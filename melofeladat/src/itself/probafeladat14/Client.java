/* ************************************************************
 * $Id: $
 *
 * Copyright (C) 2012 ITself Europe Kft.
 * All rights reserved.
 * ************************************************************/
package itself.probafeladat14;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;

// only a comment for trying git
//a kliensek implementacioja es inditasa
public class Client extends Thread implements SharedValues
{
	//kliensek letrehozasahoz hasznalt random
	private static Random mRandom = new Random();

	//flag leallitashoz
	private boolean mRunning = true;

	//kliensben hasznalt metodus
	private boolean mMethod = true;

	//ezeket az eroforrasokat akarja a kliens lefoglalni
	private Set<String> mNeededRes = new HashSet<String>();

	//kliens eroforras feloldasahoz kapott azonosito
	private long mLockID = -1;

	//kliens naplozasi neve
	private String mLogName = null;

	//kliens konstruktor
	public Client( String[] xResList, boolean xMethod, int xClientID )
	{
		super( "Client_" + xClientID );
		mLogName = "Client_" + xClientID;
		mMethod = xMethod;

		for ( int i = 0; i < xResList.length; i++ )
		{
			mNeededRes.add( xResList[ i ] );
		}
	}

	//naplozashoz interfesz
	private void log( String xMessage )
	{
		System.out.println( new Date() + " " + mLogName + ": " + xMessage );
	}

	//eroforrasok tartasa, vagy alvas
	private synchronized void holdResources()
	{
		if ( !mRunning )
		{
			return;
		}

		//alszik egy intervallumba eso erteket
		try
		{
			wait( mRandom.nextInt( CLIENT_HOLD_DIFF ) & CLIENT_HOLD_MINIMUM );
		}
		catch ( InterruptedException e )
		{
			log( "Hiba: Eroforras foglalas megszakitva!" );
		}
	}

	//interfesz leallitashoz
	public synchronized void stopRunning() throws Exception
	{
		//atallitja a futasi flag-et es megszakitja az esetleges alvast
		if ( mRunning )
		{
			mRunning = false;
			wait();
		}
		else
		{
			log( "Hiba: Az ugyfel mar leallt!" );
		}
	}

	//Thread metodusanak tulterhelese
	public void run()
	{
		log( "Elindult." );

		while ( mRunning )
		{
			log( ( ( mMethod == WRITE_METHOD ) ? "Irasra" : "Olvasasra" ) + " kert eroforrasok: "
				+ toSortedSet( mNeededRes ) );

			final long startTime = System.currentTimeMillis();
			mLockID = ResHandler.getLock( mNeededRes, mMethod );
			final long elapsed = System.currentTimeMillis() - startTime;

			log( ( ( mMethod == WRITE_METHOD ) ? "Irasra" : "Olvasasra" ) + " kapott eroforrasok (" + elapsed
				+ " ms): " + toSortedSet( mNeededRes ) + ". Lock: " + mLockID );

			holdResources();

			ResHandler.releaseLock( mLockID );

			holdResources();
		}

		log( "Leallt." );
	}

	//kliensek letrehozasa
	private static Client createClient( int xClientID )
	{
		final int resNum = mRandom.nextInt( RESOURCE_LIST.length ) + 1;

		//fogjuk az osszes eroforrast es random kiveszunk belole
		final ArrayList<String> selectedRes = new ArrayList<String>( Arrays.asList( RESOURCE_LIST ) );

		for ( int i = 0; i < ( RESOURCE_LIST.length - resNum ); i++ )
		{
			final int chosenRes = mRandom.nextInt( selectedRes.size() );

			selectedRes.remove( chosenRes );
		}

		final boolean method = mRandom.nextInt( 5 ) <= 2;

		return new Client( ( String[] ) selectedRes.toArray( new String[]{} ), method, xClientID );
	}

	//seged interfesz, hogy rendezve tudjuk logolni a halmaz elemeit
	private String toSortedSet( Set<String> xSet )
	{
		final StringBuffer tmpSB = new StringBuffer( "{ " );

		final String[] sortedRes = ( String[] ) xSet.toArray( new String[]{} );
		Arrays.sort( sortedRes );

		for ( int i = 0; i < sortedRes.length; i++ )
		{
			tmpSB.append( sortedRes[ i ] ).append( ", " );
		}
		tmpSB.setLength( tmpSB.length() - 2 );
		tmpSB.append( " }" );

		return tmpSB.toString();
	}

	public static void main( String[] args ) throws Exception
	{
		//megtartjuk a klienseket, a leallitashoz
		final Client[] clientArr = new Client[ CLIENTNUM ];

		for ( int i = 0; i < clientArr.length; i++ )
		{
			clientArr[ i ] = createClient( i );
			clientArr[ i ].start();

			//a klienseket nem egyszerre inditjuk
			try
			{
				Thread.sleep( mRandom.nextInt( CLIENT_HOLD_MINIMUM ) );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}

		//kliensek futasi idejet vegig sleep-eli
		try
		{
			Thread.sleep( RUNTIME );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}

		//kliensek leallitasa
		for ( int i = 0; i < clientArr.length; i++ )
		{
			clientArr[ i ].stopRunning();

			try
			{
				clientArr[ i ].join();
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}
}
