package server_interface;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server_Network implements Runnable{
	static ServerSocket server;
	public static ConcurrentHashMap<String, Client_Thread> clientlist;
	static Server_Interface si;
	
	Server_Network()
	{
		try {
			si=new Server_Interface();
			server=new ServerSocket(5000);
			clientlist=new ConcurrentHashMap<String, Client_Thread>();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void run() 
	{
		si.server_interface_display();
		while(true)
		{
			try {
				Socket ss=server.accept();
				if(ss.isConnected()) {System.out.println("一个新链接");}
				Client_Thread ct=new Client_Thread(ss);
				clientlist.put(ct.key, ct);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
