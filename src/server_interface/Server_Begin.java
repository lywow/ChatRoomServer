/*package server_interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server_Begin {
	JFrame sbi;
	JButton sbb1,sbb2;
	JTextField sbt;
	String pass="00000";//服务器密码
	JLabel lll=new JLabel(">>>>>>>>>输入的密码错误<<<<<<<<<");
	int i=0;
	
	Server_Begin()
	{
		sbi=new JFrame("服务器开启窗口");
		sbb1=new JButton("开启服务器");
		sbb2=new JButton("关闭本程序");
		sbt=new JTextField(20);
		sbi.setLayout(new FlowLayout());
		sbi.add(new JLabel("请输入开启服务器钥匙"));
		
		lll.setVisible(false);
		sbi.add(sbt);
		sbi.add(lll);
		sbi.add(sbb1);
		sbi.add(sbb2);
		sbi.setSize(250, 180);
		sbi.setResizable(false);
		sbi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void server_begin_display()
	{
		sbi.setVisible(true);
		sbb1.addActionListener(new ActionListener()//开启服务器按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				if(pass.equals(sbt.getText()))
				{
					i=1;
				}
				else {lll.setVisible(true);}
			}
		});
		sbb2.addActionListener(new ActionListener()//退出服务器按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				sbi.setVisible(false);
			}
		});
	}
	
	public int get_i()
	{return i;}
	
	public void beginclose()
	{
		sbi.setVisible(false);
	}
}*/
