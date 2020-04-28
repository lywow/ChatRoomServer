package server_interface_panel;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import server_interface.Client_Thread;
import server_interface.Server_Network;
import server_interface.Working;

public class User_Panel {
	static JPanel up=new JPanel();
	static JPanel up_other=new JPanel();
	static JTextField[] upt= {new JTextField(20),new JTextField(20)};
	static JButton upb1,upb2,upb3;
	static Data_Reading user=new Data_Reading("用户列表");
	static String selection=null;
	static JScrollPane sp;
	static JLabel upl=new JLabel();
	File file;
	
	public User_Panel()
	{
		up.setLayout(null);
		sp=new JScrollPane(user.tree);
		sp.setBounds(0, 0, 300, 660);
		up.setBounds(0, 0, 600, 660);
		up.add(sp);
		
		up_other.setBounds(300, 0, 300, 620);
		up_other.setLayout(new FlowLayout());
		up_other.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		up_other.add(new JLabel("账 号："));up_other.add(upt[0]);
		up_other.add(new JLabel("密 码："));up_other.add(upt[1]);
		up.add(up_other);
		for(int i=0;i<2;i++) {upt[i].setEditable(false);}

		upb1=new JButton("进行编辑");up_other.add(upb1);
		upb2=new JButton("编辑保存");up_other.add(upb2);
		up_other.add(new JLabel("-----------------------------------------------------------------------------------"));
		upb3=new JButton("删除这个用户");up_other.add(upb3);up_other.add(upl);upl.setVisible(false);
		
		upb3.addActionListener(new ActionListener()//删除yonghu按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				if(selection.length()>5)
				{
					if(Server_Network.clientlist.containsKey(selection))
					{
						delFolder("用户列表\\"+selection+".txt");
						delFolder("用户资料\\"+selection);
						for(ConcurrentHashMap.Entry<String, Client_Thread> entry: Server_Network.clientlist.entrySet()) 
						{try {
							entry.getValue().send("delfriend|"+selection);
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}}
					    TreePath treepath=user.tree.getSelectionPath();
					    if (treepath!=null)
					    {
					        //下面两行取得选取节点的父节点.
					        DefaultMutableTreeNode selectionNode=(DefaultMutableTreeNode)treepath.getLastPathComponent();
					        TreeNode parent=(TreeNode)selectionNode.getParent();
					        System.out.println(selectionNode);
					        if (parent!=null) 
					        {
					            //由DefaultTreeModel的removeNodeFromParent()方法删除节点。
					        	user.newModel.removeNodeFromParent(selectionNode);
					        }
					    }
					}
					else {upl.setText(">>>>>>>>>在线的账号不可删除<<<<<<<<<");}
				}
				else {upl.setText(">>>>>>>>>>没有选择任何用户<<<<<<<<<<");}
				upl.setVisible(false);
			}
		});
		upb1.addActionListener(new ActionListener()//进行编辑按钮监听
		{public void actionPerformed(ActionEvent e){for(int i=0;i<2;i++) {upt[i].setEditable(true);}}});
		upb2.addActionListener(new ActionListener()//确定编辑按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					user_save();
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				for(int i=0;i<2;i++) {upt[i].setEditable(false);}
			}
		});
		
		user.tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e) 
			{
				// 设置选择可以包含任何数量的项，这些项不必是连续的。
				user.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				// 获取选中节点
				for(int i=0;i<2;i++) 
				{upt[i].setText("");}
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)user.tree.getLastSelectedPathComponent();
				if (treeNode != null) 
				{
					selection=treeNode.toString();
					selection=selection.substring(0, selection.length()-4);
					try {
						user_reading();
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		}); 
	}
	void user_save()throws Exception
	{
		file=new File("用户列表\\"+selection+".txt");
		if (file != null) 
		{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			try 
			{
				for(int i=0;i<2;i++) 
				{bw.write(upt[i].getText() + "\r\n");}
			}
			finally 
			{
				bw.close();
				fw.close();
			}
		}
	}
	
	void user_reading() throws Exception 
	{
		file=new File("用户列表\\"+selection+".txt");
		if (file != null) 
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			try 
			{
				for(int i=0;i<2&&br.ready();i++) 
				{upt[i].setText(br.readLine());}
			}
			finally 
			{
				br.close();
				fr.close();
			}
		}
	}
	
	
	public void delFolder(String folderPath) //删除用户文件方法
	{
	    try {  
	        delAllFile(folderPath); //删除完里面所有内容  
	        String filePath = folderPath;  
	        filePath = filePath.toString();  
	        java.io.File myFilePath = new java.io.File(filePath);  
	        myFilePath.delete(); //删除空文件夹  
	     } catch (Exception e) {  
	       e.printStackTrace();   
	     }  
	}
	public boolean delAllFile(String path) 
	{  
		boolean flag = false;  
	    File file = new File(path);  
	    if (!file.exists()) 
	    {return flag;}  
	    if (!file.isDirectory()) 
	    {return flag;}  
	    String[] tempList = file.list();  
	    File temp = null;  
	    for (int i = 0; i < tempList.length; i++) 
	    {
		    if (path.endsWith(File.separator)) 
		    {  
		    	temp = new File(path + tempList[i]);  
		    } else {temp = new File(path + File.separator + tempList[i]);  }  
		          
		    if (temp.isFile())
		    {temp.delete();}  
		    if (temp.isDirectory()) 
		    {
		    	delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
		    	delFolder(path + "/" + tempList[i]);//再删除空文件夹  
		        flag = true;  
		    } 
	    }  
	    return flag;  
	}  
	
	public JPanel get_panel() {return up;}
}
