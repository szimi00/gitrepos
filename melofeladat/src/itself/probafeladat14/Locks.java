package itself.probafeladat14;

import java.util.ArrayList;


public class Locks implements SharedValues {

	// zárak dinamikus tömbje
	static ArrayList<Lock> list = new ArrayList<Lock>();
	
	// visszaadja az erõforráson lévõ zár típusát
	public static  int locktypeon(String res) {
		//synchronized(list) {
			int toreturn = 0;
			
			if(!list.isEmpty()) {
				for(Lock l : list) {
					if (l.resource.equals(res)) {
						if(toreturn < l.locktype) {
							toreturn = l.locktype;
						}
					}
				}
			}
			return toreturn;
		//}
	}
	
	
	public static void printall() {
		
		if(!list.isEmpty()) {
			System.out.println("              JELENLEGI LOCKOK: ");
			for(Lock l : list) {
				System.out.println(l.resource +"\t"+ l.locktype +"\t"+ l.key);
			}
		}
		else {
			System.out.println("  -nincs lock!");
		}
	}
	
	
	// init
	/*{
		for(String s : RESOURCE_LIST) {
			list.add(new Lock(s));
			
		}
		
	}*/
	// init vége
	
	
}
