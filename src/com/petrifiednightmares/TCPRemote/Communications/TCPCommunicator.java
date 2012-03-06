package com.petrifiednightmares.TCPRemote.Communications;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class TCPCommunicator implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 505498261491712104L;

	private static Socket mainSocket;

	public static final int PORT_NUMBER = 6129;

	private static SocketAddress sockaddr;

	private static PrintWriter output;

	public TCPCommunicator()
	{

	}

	public static void setupSocket(String ip) throws UnknownHostException, IOException
	{
		sockaddr = new InetSocketAddress(ip, PORT_NUMBER);
		connectSocket();
	}

	private static void connectSocket() throws IOException
	{
		destroySocket();
		output = null;
		mainSocket = new Socket();
		mainSocket.connect(sockaddr, 5000);
		output = new PrintWriter(mainSocket.getOutputStream());
	}

	public static void destroySocket()
	{
		if (mainSocket != null)
		{
			if (!mainSocket.isClosed())
			{
				try
				{
					mainSocket.close();
				}
				catch (IOException e)
				{
					// Doesn't matter, since we are destroying anyway lululululul
				}
			}
		}
		mainSocket = null;
	}

	public static void sendMessage(String msg) throws IOException
	{
		if (mainSocket == null)
		{
			return;
		}

		if (!mainSocket.isConnected() || mainSocket.isClosed())
		{
			connectSocket();
			System.out.println("Connecting Socket");
		}

		output.println(msg);
		output.flush();

		System.out.println(msg);
	}
}
