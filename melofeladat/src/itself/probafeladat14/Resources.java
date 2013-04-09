package itself.probafeladat14;

import java.util.ArrayList;



public class Resources {
	
	ArrayList<Resource> list = new ArrayList<Resource>();
	
	
	public void load() {
		for(String s : itself.probafeladat14.SharedValues.RESOURCE_LIST) {
			list.add(new Resource(s));
		}
	}
	
	
	void add(String _name) {
		list.add(new Resource(_name));
	}
	
	
	public Resource get(String _name) {
		Resource toreturn = null;
		for(Resource r : list) {
			if(r.name.equals(_name)) {
				toreturn = r;
			}
		}
		return toreturn;
	}
	
	
	void print() {
		System.out.println("ERÕFORRÁSOK (összes): ");
		for(Resource r : list) {
			System.out.println(r.name);
		}
	}
}
