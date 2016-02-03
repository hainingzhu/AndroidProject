package bases;

public class MTUnit {
	
	public String name;
	
	public String label;
	
	public int icon;//resource id
	
	public int someInt;
	
	public String someStr;
	
	public String docid;
	
	public String timestart;
	public String timeend;
	
	
	
	
	public boolean equalsName(String $name){
		if(name==null || name==null) return false;
		
		return ($name.equals(name));
	}
	
	@Override
	public String toString(){
		return label;
	}
	
	
}
