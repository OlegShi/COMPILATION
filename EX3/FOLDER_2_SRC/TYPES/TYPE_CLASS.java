package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
 
 
 public boolean isClass(){ return true;}

	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}
 
 public boolean isExtendBy(TYPE son) {
   if(son instanceof TYPE_NIL)
     return true;
   if(!(son instanceof TYPE_CLASS))
     return false;
        
		TYPE_CLASS ancestorPointer = (TYPE_CLASS)son;
   
		while (ancestorPointer != null){
			if (ancestorPointer == this)
				return true;
			ancestorPointer = ancestorPointer.father;
		}
		return false;
		
	}
}
