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
	
	

	// locktype: erõforrás neve és a lock típusa (0-szabad, 1-olvasás, vagy 2-írás)
	//static public Hashtable<String, Integer> locktype = new Hashtable<String, Integer>();
	
	// lockclient: erõforrás neve és a rajta zárat tartó kliens ID-ja
	//static public Hashtable<String, Long> lockclient = new Hashtable<String, Long>();
	
	
	// feltöltés: minden erõforrás szabad (lock=0, clientID=-1)
	/*{
		//System.out.println("Lock static {}KONSTRUKTOR ;)");
		for(String s : RESOURCE_LIST) {
			locktype.put(s, 0);
			lockclient.put(s, (long)-1);
			
		}
	}*/
	
	
	
}

