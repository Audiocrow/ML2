package com.ml2.server;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectSet;
import com.ml2.shared.resources.Constants;

public class ServerLoop extends ApplicationAdapter {
	
	SpriteBatch batch;
	private ObjectSet<Client> clients;
	private Database databaseConnect;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		clients = new ObjectSet<Client>();
		
		//This is the thread to accept and store new clients:
		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// no timeout, fix later
				serverSocketHint.acceptTimeout = 0;
				ServerSocket server = Gdx.net.newServerSocket(Protocol.TCP, Constants.NET_PORT, serverSocketHint); 
				while(true) {
					//Block until a client is trying to connect
					try {
						Socket socket = server.accept(null);
						Client newClient = new Client(socket);
						//If this client isn't already in our list, re-add it
						if(!clients.contains(newClient)) clients.add(newClient);
						else newClient.dispose(); //Note: Client.dispose also disposes the socket
					}
					catch(GdxRuntimeException e) {
						Gdx.app.error("ServerLoop", e.getMessage());
					}
				}
			}
		}).start();
	}

	@Override
	public void render () {
		//Process any outstanding client messages
		
		for(Client c : clients) {
			InputStream in = c.getInputStream();
			try {
				if(in.available() > 0) {
					//read in message from the input stream and process
					byte[] msg = new byte[in.available()];
					in.read(msg, 0, msg.length);
					c.processMessage(msg);
				}
			}
			catch(IOException e) {
				Gdx.app.error("ServerLoop:Client " + c.address, e.getMessage());
				//Kill this client, or ignore the message, or do nothing?
			}
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
