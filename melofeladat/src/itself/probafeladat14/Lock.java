package itself.probafeladat14;



public class Lock {
	
	String resource;
	int locktype;
	long key;

	
	
	
	Lock(String l) {
		resource = l;
		locktype = 0;
		key = -1;
	}
	
	Lock(String l, int t, long k) {
		resource = l;
		locktype = t;
		key = k;
	}
	
	

	// locktype: er�forr�s neve �s a lock t�pusa (0-szabad, 1-olvas�s, vagy 2-�r�s)
	//static public Hashtable<String, Integer> locktype = new Hashtable<String, Integer>();
	
	// lockclient: er�forr�s neve �s a rajta z�rat tart� kliens ID-ja
	//static public Hashtable<String, Long> lockclient = new Hashtable<String, Long>();
	
	
	// felt�lt�s: minden er�forr�s szabad (lock=0, clientID=-1)
	/*{
		//System.out.println("Lock static {}KONSTRUKTOR ;)");
		for(String s : RESOURCE_LIST) {
			locktype.put(s, 0);
			lockclient.put(s, (long)-1);
			
		}
	}*/
	
	
	
}

