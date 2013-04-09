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
			 * nem j�: ha vmilyen lockot k�rek, de nem kapom meg mert foglalt az er�forr�s
			 * bennragadok a while-ban, �s k�zben nem engedem el a listet, igy nem lehet 
			 * releaselock m�s sz�lt�l, teh�t a v�gtelens�gig a while-ban maradtam.
			 * */
			while(true) {

				if(iswrite) { //Wlockot k�r�nk
					if(!Locks.list.isEmpty() ){
						for(String n: res_requests) {

							if(Locks.locktypeon(n) != 0) {
								//ha az er�forr�son l�v� lock nem 0, akkor foglalt �r�sra
								grantable = false;
							}
						}
					}
					if(grantable) {
						for(String n: res_requests) {	// k�rt er�forr�sok wlock-ra(2) �ll�t�dnak
							Locks.list.add(new Lock(n, 2, key));
						}
						break;
					}
				}
				
				else {	//Rlockot k�r�nk
					if(!Locks.list.isEmpty() ){
						for(String n: res_requests) {
							
							if(Locks.locktypeon(n) == 2) {
								//ha az er�forr�son l�v� lock 2(wlock), akkor foglalt olvas�sra
								grantable = false;
							}
						}
					}	
					if(grantable) {
						for(String n: res_requests) {	// k�rt er�forr�sok rlock-ra(1) �ll�t�dnak
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
		System.out.println("Releaselock k�r�s (key: "+key+")");
		
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
