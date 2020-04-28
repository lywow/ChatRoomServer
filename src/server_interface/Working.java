package server_interface;

import java.io.File;

public class Working {
	static Server_Network sn=new Server_Network();
	
	public Server_Network getsn()
	{
		return sn;
	}
	public static void main(String args[])
	{
		File file=new File("群组列表");
		if(!file.exists()) {file.mkdirs();}
		file=new File("用户列表");
		if(!file.exists()) {file.mkdirs();}
		System.out.println("服务器开启");
		new Thread(sn).start();
	}
}
