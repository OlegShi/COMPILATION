package TYPES;

public class TYPE_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
 
 public TYPE_LIST(TYPE_LIST list){
   if(list!=null){
     this.head = this.head = list.head;
   }
   if(list.tail != null){
     this.tail = new TYPE_LIST(list.tail);
   }
 }    
	
	public void printList(){
		System.out.print(head.name+",");
		//System.out.print("-"+head.getClass().getName());
		//System.out.print("    |");
		if(tail!=null){
			tail.printList();
		}
	}
 
  public void concat(TYPE_LIST list){
    
     TYPE_LIST pointer = this;

    //System.out.print(pointer.head.name+"\n");
      
 
     while(pointer.tail!=null){
        pointer = pointer.tail;
        //System.out.print(pointer.head.name+"\n");
     }
     
     pointer.tail = list;  
  }
    
    //returns first
    public TYPE search_by_name(String name){
    
      
     TYPE_LIST pointer = this;
     do {
       if(pointer.head!=null&&pointer.head.name.equals(name)){
         return pointer.head;
       }
       pointer = pointer.tail;
     }
     while(pointer!=null);
     
     return null;
  }
  
  public boolean compare_list(TYPE_LIST list){
     
     if( list==null){
      return false;
     }
     
     TYPE_LIST pointer1 = this;
     TYPE_LIST pointer2 = list;
          
     
     do {       
        //only check equality if its  not nil and array arg
        if (!((pointer1.head instanceof TYPE_ARRAY) &&
               (pointer2.head instanceof TYPE_NIL ||
                 (pointer2.head instanceof TYPE_NEW_ARRAY &&  ((TYPE_ARRAY)pointer1.head).arrayType.name.equals(((TYPE_NEW_ARRAY)pointer2.head).arrayType.name))
               )
          )){
          if(!(
              (pointer1.head instanceof TYPE_CLASS) && 
              (
                (
                  (pointer2.head instanceof TYPE_CLASS) &&                
                  ((TYPE_CLASS)(pointer1.head)).isExtendBy(pointer2.head)
                )||    
                pointer2.head instanceof  TYPE_NIL  
              )
              
                 
          )){
            if((pointer1.head!=null) && (pointer2.head!=null) && !(pointer1.head.name.equals(pointer2.head.name))){
              System.out.format("%s!=%s\n",pointer1.head.name,pointer2.head.name);
              return false;
            }
          }
       }
       pointer1 = pointer1.tail;
       pointer2 = pointer2.tail;
      
     }
     while(pointer2!=null&&pointer1!=null);
     
     if(pointer1!=null ||pointer2!=null){
       return false;
     }
     
     return true;
  }
  
}
