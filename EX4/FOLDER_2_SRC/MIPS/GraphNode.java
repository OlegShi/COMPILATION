package MIPS;

import java.util.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


/**
 * Created by hen on 11/01/2018.
 */
public class GraphNode {

    public static GraphNode head = new GraphNode();
    public static GraphNode lastNode=head;
    public static HashMap<String,GraphNode> labelList=new HashMap<>();

    public static void addNode(GraphNode newNode){
        lastNode.serialNext = newNode;
        lastNode=lastNode.serialNext;
        
        if(newNode.defing!=-1){
          addNode( new GraphNode(new int[]{newNode.defing},null));
        }                        
    }

    public static void addLabel(String label) {
        lastNode.serialNext =  new GraphNode(  null,null);
        lastNode.serialNext.isLabel=true;
        labelList.put(label,lastNode.serialNext);
        lastNode=lastNode.serialNext;
    }

    public static void addAJump(String label) {
        lastNode.serialNext =  new GraphNode(  null,label);
        lastNode.serialNext .isConnectedToNextSerial = false;
        lastNode=lastNode.serialNext;
    }



    public int[] using;
    public int defing=-1;
    public String connectToLabel;
    public boolean isLabel;
    public GraphNode serialNext;
    public GraphNode jumpNext;
    public boolean isConnectedToNextSerial =true;



    public GraphNode(){
    }

    public GraphNode(int[] using, String conectToLabel){
        this.using = using;
        this.connectToLabel = conectToLabel;
        isLabel=false;
    }

    public GraphNode(int[] using, int defing, String conectToLabel){
        this(using,conectToLabel);
        this.defing=defing;
        
        
        
    }


    public static void connectGraph() {
        GraphNode nodePointer = head.serialNext;
        while(nodePointer!=null){

            if(nodePointer.connectToLabel!=null){
                nodePointer.jumpNext=labelList.get(nodePointer.connectToLabel);
            }

            nodePointer = nodePointer.serialNext;

        }
    }

    public static boolean wasChanged=true;
    public static Set<Integer> tempList=new HashSet<>();

    public static void addToSet(Set<Integer> set,int t){
        set.add(t);
        wasChanged =true;
        tempList.add(t);
    }

    public Set<Integer> useIn=new HashSet<>();
    public Set<Integer> useOut=new HashSet<>();

    public static void makeTheLivenessGraph() {
        while(wasChanged){
            wasChanged=false;
            GraphNode nodePointer = head.serialNext;
            while(nodePointer!=null){
                if(nodePointer.using!=null){
                    for(int t:nodePointer.using){
                        if(!nodePointer.useIn.contains(t)){
                            addToSet(nodePointer.useIn,t);
                        }
                    }
                }

                for(int t:nodePointer.useOut){
                    if(t!=nodePointer.defing){
                        if(!nodePointer.useIn.contains(t)) {
                            addToSet(nodePointer.useIn,t);
                        }
                    }
                }

                if(nodePointer.isConnectedToNextSerial&&nodePointer.serialNext!=null){
                    for(int t:nodePointer.serialNext.useIn){
                        if(!nodePointer.useOut.contains(t)) {
                            addToSet(nodePointer.useOut,t);
                        }
                    }
                }

                if(nodePointer.jumpNext!=null){
                    for(int t:nodePointer.jumpNext.useIn){
                        if(!nodePointer.useOut.contains(t)) {
                            addToSet(nodePointer.useOut,t);
                        }
                    }
                }



                nodePointer = nodePointer.serialNext;
            }

        }
    }



    public static class igNodeGraph{
        ArrayList<igNode> nodes = new ArrayList<>();
    }

    public static class igNode{
        int t;
        int c=-1;
        Set<igNode> connections=new HashSet<>();
    }

    public static void createConnections(igNode node, igNodeGraph graph) {

        //get his connections
        //add to connections

        for (int tt : getConnected(node.t)) {
            if (GraphNode.tempList.contains(tt)) {
                GraphNode.tempList.remove(tt);
                igNode conectedNode = new igNode();
                conectedNode.t = tt;
                node.connections.add(conectedNode);
                conectedNode.connections.add(node);
                graph.nodes.add(conectedNode);
                createConnections(conectedNode,graph);
            }
            else{
                //get the node from the graph
                igNode conectedNode=null;
                for(igNode nodeT : graph.nodes){
                    if(nodeT.t==tt){
                        conectedNode=nodeT;
                    }
                }
                //connect that node
                node.connections.add(conectedNode);
                conectedNode.connections.add(node);
            }
        }



    }

    public static ArrayList<igNodeGraph> makeTheInterferenceGraph() {
        ArrayList<igNodeGraph> graphs =new ArrayList<>();
        while(GraphNode.tempList.size()!=0){
            int t = (int) GraphNode.tempList.toArray()[0];
            GraphNode.tempList.remove(t);
            
            igNodeGraph graph = new igNodeGraph();
            graphs.add(graph);

            igNode node = new igNode();
            node.t = t;
            graph.nodes.add(node);
            createConnections(node,graph);

        }
        


        return graphs;

    }

    public static Set<Integer> getConnected(int t) {
        Set<Integer> connectedTemps =  new HashSet<>();

        GraphNode nodePointer = head.serialNext;
        while(nodePointer!=null){
            if(nodePointer.useIn.contains(t)){
                for(int tt : nodePointer.useIn){
                    if(tt!=t){
                        connectedTemps.add(tt);
                    }
                }
            }
            nodePointer = nodePointer.serialNext;
        }

        return connectedTemps;
    }




    public static HashMap<String,String> colorThegraph(ArrayList<igNodeGraph> graphs) {

        class SortbyConnection implements Comparator<GraphNode.igNode>
        {
            public int compare(GraphNode.igNode a, GraphNode.igNode b)
            {
                return  b.connections.size() -a.connections.size();
            }
        }

        int [] colors = new int[]{0,1,2,3,4,5,6,7};

        for(GraphNode.igNodeGraph graph: graphs){
            Collections.sort(graph.nodes, new SortbyConnection());
            for(GraphNode.igNode node : graph.nodes){
                int colorIndex=0;
                boolean colorOk = false;
                while(colorIndex<7&&colorOk==false){
                    colorOk=true;
                    for(GraphNode.igNode nNodes : node.connections){
                        if(nNodes.c==colors[colorIndex]){
                            colorOk=false;
                        }
                    }
                    colorIndex++;
                }
                if(colorIndex==8&&colorOk==false){
                    System.out.println("too many registers");
                    System.exit(0);
                }
                node.c = colors[colorIndex-1];

            }
        }

        HashMap<String,String> map = new HashMap();

        for(GraphNode.igNodeGraph graph: graphs) {
            for (GraphNode.igNode node : graph.nodes) {
                map.put(String.valueOf(node.t),String.valueOf(node.c));
            }
        }

        return  map;
    }
    
    public static void runTranlation(String outputFilename) throws IOException{
            GraphNode.connectGraph();
            GraphNode.makeTheLivenessGraph();
            ArrayList<GraphNode.igNodeGraph> graphs = GraphNode.makeTheInterferenceGraph();
            HashMap<String,String> map = GraphNode.colorThegraph(graphs);   
              
            Path path = Paths.get(outputFilename);
            Charset charset = StandardCharsets.UTF_8;      
            String content = new String(Files.readAllBytes(path), charset);

            ArrayList<String> keys = sortKeys(map);


            System.out.println("\n==========Temp translation==========");
            for (String key : keys){
                String temp="Temp_"+key;
                String register="\\$t" + map.get(key);            
                //System.out.println("sed -i 's/"+temp + "/" + register+"/g' ${OUTPUT_DIR}/MIPS.txt");
                System.out.println(temp + " - " + register);
                content = content.replaceAll(temp, register);          
            }
			      System.out.println("====================================="); 
            Files.write(path, content.getBytes(charset));
    }
    
     private static ArrayList<String> sortKeys(HashMap<String, String> map) {
       class SortbyTemp implements Comparator<String>
        {
            public int compare(String a, String b)
            {
                return  b.length() -a.length();
            }

        }

        ArrayList<String> keys = new ArrayList<>();
        for (String key : map.keySet()){
            keys.add(key);
        }

        Collections.sort(keys, new SortbyTemp());

        return keys;
    }

}
