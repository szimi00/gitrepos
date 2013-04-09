package itself.probafeladat14;

import java.util.HashSet;
import java.util.Set;




public class ResHandler implements SharedValues {
	
	static Locks locks = new Locks();
	
	
	public static  long getLock(Set<String> res_requests, boolean iswrite) {
	
		synchronized(locks) {
		
			boolean grantable = true;
			long key = Thread.currentThread().getId();
			
			
			/* 
			 * nem jó: ha vmilyen lockot kérek, de nem kapom meg mert foglalt az erõforrás
			 * bennragadok a while-ban, és közben nem engedem el a listet, igy nem lehet 
			 * releaselock más száltól, tehát a végtelenségig a while-ban maradtam.
			 * */
			while(true) {

				if(iswrite) { //Wlockot kérünk
					if(!Locks.list.isEmpty() ){
						for(String n: res_requests) {

							if(Locks.locktypeon(n) != 0) {
								//ha az erõforráson lévõ lock nem 0, akkor foglalt írásra
								grantable = false;
							}
						}
					}
					if(grantable) {
						for(String n: res_requests) {	// kért erõforrások wlock-ra(2) állítódnak
							Locks.list.add(new Lock(n, 2, key));
						}
						break;
					}
				}
				
				else {	//Rlockot kérünk
					if(!Locks.list.isEmpty() ){
						for(String n: res_requests) {
							
							if(Locks.locktypeon(n) == 2) {
								//ha az erõforráson lévõ lock 2(wlock), akkor foglalt olvasásra
								grantable = false;
							}
						}
					}	
					if(grantable) {
						for(String n: res_requests) {	// kért erõforrások rlock-ra(1) állítódnak
							Locks.list.add(new Lock(n, 1, key));
						}
						break;
					}
				}
			}
			
			Locks.printall();
			
			return key;
		
		}
	}

	
	public static  void releaseLock(long key) {
		System.out.println("Releaselock kérés (key: "+key+")");
		
		synchronized(locks) {

			if(!Locks.list.isEmpty()) {
			
				HashSet<Lock> toRemove = new HashSet<Lock>();
				for(Lock l : Locks.list) {
					
					if(l.key == key) {
						
						toRemove.add(l);
					}
			
				}
				
				Locks.list.removeAll(toRemove);
				
				
			}
			else {
				System.out.println("__---!!! RELEASELOCK ERROR !!!---___");
			}

		}
		Locks.printall();
	}
	
	
	
}
