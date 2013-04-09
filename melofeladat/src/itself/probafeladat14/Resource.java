package itself.probafeladat14;

public class Resource {
	
	public String name;
	public int lock;	// 0: none,  1: read,  2: write
	Resource (String _name) {
		name =  _name;
		lock = 0;
	}
}
