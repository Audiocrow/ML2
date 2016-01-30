package com.ml2.server;

import java.io.InputStream;
import java.util.Arrays;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;

public class Client implements Disposable {
	public final String TAG = Client.class.getName();
	
	private final Socket socket;
	public final String address;
	
	Client(Socket s) {
		socket = s;
		address = s.getRemoteAddress();
	}
	
	public void processMessage(byte[] msg)
	{
		int serverCall = -1;
		byte[] msgCode = new byte[4];
		
		//copy first 4 bytes of incoming message to an array of byte;
		msgCode = Arrays.copyOf(msg, 4);
	    
		//convert 4 bytes into an int
		serverCall = java.nio.ByteBuffer.wrap(msgCode).getInt();
	
		//evoke serverProcessor class
		//serverProcessor( message );
	}
	
	public InputStream getInputStream() { return socket.getInputStream(); }
	@Override
	public int hashCode() {
		/*RECALL: Whenever it is invoked on the same object more than once during an execution of a Java application,
		 * the hashCode method must consistently return the same integer, provided that 
		 * no information used in equals comparisons on the object is modified.
		 * This integer need not remain consistent from one execution of an application to another execution of the same application.
		 * 
		 * TLDR; ONLY USE VALUES THAT WON'T CHANGE TO COMPUTE THE HASHCODE
		 */
		int hash = 73;
		hash = 73*hash + address.hashCode();
		return hash;
	}
	

	@Override
	public boolean equals(Object obj) {
		/*Note: Client will equal another client even if the sockets are different
		 * which shouldn't matter since the IP Addresses are the only important difference
		 */
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Client)) return false;
		Client other = (Client)obj;
		if(!address.equals(other.address)) return false;
		return true;
	}
	@Override
	public void dispose() { socket.dispose(); }
}
