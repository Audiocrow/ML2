package com.ml2.server;

import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;
import com.ml2.shared.utils.Utils;

public class Client implements Disposable {
	private Socket socket;
	public final String address;
	/** Since TCP sends in chunks, we need to store everything and scan through it until we've received the entire intended message.
	 * It's possible that afterwards there will be remaining chunks of the next message, so store that too.
	 */
	private byte[] messageQueue;
	
	Client(Socket s) {
		socket = s;
		address = s.getRemoteAddress();
	}
	
	/** ANATOMY OF A MESSAGE:<p>
	 * <b>int</b>: The first 4 bytes indicate the message ID, which is how the server knows what to do with the rest of it<p>
	 * <b>short</b>: The next 2 bytes indicate how long the intended message is supposed to be (because TCP sends in chunks).
	 */
	public void processMessage(byte[] msg)
	{
		//First, add this new msg to the messageQueue
		if(messageQueue == null) messageQueue = msg;
		else {
			byte[] concatMsg = Utils.concat(messageQueue, msg);
			messageQueue = concatMsg;
		}
		
		//Next, see if the entire message has been received (first we need the msgID and the msgLen)
		if(messageQueue.length < 6) return;
		ByteBuffer bb = ByteBuffer.wrap(msg);
		int msgID = bb.getInt();
		//Grab the next 2 bytes and wrap to short:
		short msgLen = bb.getShort();
		if(messageQueue.length < msgLen + 6) return;
		
		//At this point, we have the entire intended message - send it to be processed
		try {
			byte[] message = new byte[msgLen];
			bb.get(message);
			//serverProcessor.getInstance().process(msgID, message);
		}
		catch(BufferUnderflowException e) {
			Gdx.app.error("Client " + address + " processingMessage()", e.getMessage());
			return; //Try again next time this is called? Remove this if you want to just move on.
		}
		
		//If anything is left over, it becomes the new messageQueue
		bb = bb.slice();
		byte[] newQueue = bb.array();
		messageQueue = newQueue.length > 0 ? newQueue : null;
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
