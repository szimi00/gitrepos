/* ************************************************************
 * $Id: $
 *
 * Copyright (C) 2012 ITself Europe Kft.
 * All rights reserved.
 * ************************************************************/
package itself.probafeladat14;

//ebben az osztalyban vannak definialva a kliensben es az utemezoben hasznalt konstansok
public interface SharedValues
{
	//eroforrasok listaja
	public final static String[] RESOURCE_LIST = new String[]{ "a", "b", "c", "d", "e", "f", "g", "h" };

	//konstans a kliens lefoglalasi metodus-tipusanak ertelmezesehez
	public final static boolean WRITE_METHOD = true;

	//konstans a kliens lefoglalasi metodus-tipusanak ertelmezesehez
	public final static boolean READ_METHOD = false;

	//konstans a kliensek szamahoz
	public final static int CLIENTNUM = 5; //default: 5

	//a kliens varakozasi idejenek minimuma
	public final static int CLIENT_HOLD_MINIMUM = 1000;

	//a kliens varakozasi idejenek maximum elterese
	public final static int CLIENT_HOLD_DIFF = 1000;

	//a kliensek futasi idokorlatja
	public final static int RUNTIME = 20000;
}
