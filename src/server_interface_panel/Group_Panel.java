package server_interface_panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
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

public class Group_Panel {
	static JPanel gp;
	static JButton[] gpb=new JButton[4];
	static JTextArea gpta;
	static Data_Reading group=new Data_Reading("群组列表");
	static File file;
	static String selection="群组列表\\";
	static JScrollPane sp;
	
	public Group_Panel()
	{
		gp=new JPanel();
		gp.setLayout(null);
		gp.setBounds(0, 0, 600, 660);
		sp=new JScrollPane(group.tree);
		sp.setBounds(0, 0, 300, 640);
		gp.add(sp);
		
		gpta=new JTextArea();gpta.setBounds(300, 0, 300, 580);gp.add(gpta);gpta.setEditable(false);
		gpta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		gpb[0]=new JButton("进行编辑");gpb[0].setBounds(300, 580, 150, 40);gp.add(gpb[0]);
		gpb[1]=new JButton("编辑保存");gpb[1].setBounds(450, 580, 150, 40);gp.add(gpb[1]);
		gpb[2]=new JButton("新建群组");gpb[2].setBounds(300, 620, 150, 40);gp.add(gpb[2]);
		gpb[3]=new JButton("删除群组");gpb[3].setBounds(450, 620, 150, 40);gp.add(gpb[3]);
		
		group.tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e) 
			{
				// 设置选择可以包含任何数量的项，这些项不必是连续的。
				group.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				// 获取选中节点
				selection="群组列表\\";
				gpta.setEditable(false);
				gpta.setText("");
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)group.tree.getLastSelectedPathComponent();
				if (treeNode != null) 
				{
					selection+=treeNode;
					System.out.println(selection);
					try {
						setTextArea();
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});
		
		gpb[0].addActionListener(new ActionListener()//进行编辑按钮监听
		{public void actionPerformed(ActionEvent e){gpta.setEditable(true);}});
		gpb[1].addActionListener(new ActionListener()//编辑保存按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				gpta.setEditable(false);
				try {
					saveTextArea();
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		gpb[2].addActionListener(new ActionListener()//新建群组按钮监听
		{
			public void actionPerformed(ActionEvent e){gpta.setEditable(false);
			try {
			    create_group();
		    } catch (Exception e1) {
		    	// TODO 自动生成的 catch 块
		    	e1.printStackTrace();
		    }
		}});
		gpb[3].addActionListener(new ActionListener()//删除群组按钮监听
		{
			public void actionPerformed(ActionEvent e){gpta.setEditable(false);
			    TreePath treepath=group.tree.getSelectionPath();
			    if (treepath!=null)
			    {
			        //下面两行取得选取节点的父节点.
			        DefaultMutableTreeNode selectionNode=(DefaultMutableTreeNode)treepath.getLastPathComponent();
			        TreeNode parent=(TreeNode)selectionNode.getParent();
			        System.out.println(selectionNode);
			        if (parent!=null) 
			        {
			            //由DefaultTreeModel的removeNodeFromParent()方法删除节点。
			            group.newModel.removeNodeFromParent(selectionNode);
			        }
			        try {
				        delete_group(selection+selectionNode);
			        } catch (Exception e1) {
			        	// TODO 自动生成的 catch 块
			        	e1.printStackTrace();
		        	}
			    }
	    	}
		});
	}
	
	public JPanel get_panel() {return gp;}
	void delete_group(String str)throws Exception
	{
		JFrame dg=new JFrame("确定删除群组");
		dg.setLayout(new FlowLayout());
		JButton dgb=new JButton("确定删除");
		JLabel dgl=new JLabel(">>>>>>>>>>该群组已经删除<<<<<<<<<<");dgl.setVisible(false);
		dg.setSize(250, 150);
		dg.add(dgl);dg.add(dgb);
		dg.setVisible(true);dg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		dgb.addActionListener(new ActionListener()//确定创建按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				file=new File(str);
				if(file.exists())
				{
					file.delete();
					dg.setVisible(false);
				}
				else {dgl.setVisible(true);dg.setVisible(false);}
		        for(ConcurrentHashMap.Entry<String, Client_Thread> entry: Server_Network.clientlist.entrySet()) 
				{try {
					entry.getValue().send("delgroup|"+selection.substring(0,selection.length()-4));
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}}
			}
		});
	}
	void create_group()throws Exception
	{
		JFrame cg=new JFrame("请输入群组名称");
		cg.setLayout(new FlowLayout());
		JTextField cgt=new JTextField(20);
		JButton cgb=new JButton("确定创建");
		JLabel cgl=new JLabel(">>>未命名或该群组已存在<<<");cgl.setVisible(false);
		cg.setSize(250, 150);
		cg.add(cgt);cg.add(cgl);cg.add(cgb);
		cg.setVisible(true);cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cgb.addActionListener(new ActionListener()//确定创建按钮监听
		{
			public void actionPerformed(ActionEvent e)
			{
				file=new File("群组列表\\"+cgt.getText()+".txt");
				if(cgt.getText()!=null&&!file.exists())
				{
					try {
						file.createNewFile();
						addgroup(cgt.getText());
						TreePath treepath=group.tree.getSelectionPath();
					    if (treepath!=null)
					    {
					        //下面两行取得选取节点的父节点.
					        DefaultMutableTreeNode selectionNode=(DefaultMutableTreeNode)treepath.getLastPathComponent();
					        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)selectionNode.getParent();
					        
					        DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(cgt.getText()+".txt");
					        newNode.setAllowsChildren(true);
							//由DefaultTreeModel的insertNodeInto（）方法增加新节点
							group.newModel.insertNodeInto(newNode,parent,parent.getChildCount());
							for(ConcurrentHashMap.Entry<String, Client_Thread> entry: Server_Network.clientlist.entrySet()) 
							{try {
								entry.getValue().send("addgroup|"+cgt.getText());
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}}
					    }
						cg.setVisible(false);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
				else {cgl.setVisible(true);}
			}
		});
	}
	void saveTextArea ()throws Exception
	{
		file=new File(selection);
		if (file != null) 
		{
			FileWriter fr = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fr);
			try 
			{
				String[] sss=gpta.getText().split("\\n");
				for(int i=0;sss[i]!=null;i++) 
				{bw.write(sss[i]+"\r\n");}
			}
			finally 
			{
				bw.close();
				fr.close();
			}
		}
	}
	void setTextArea() throws Exception 
	{
		file=new File(selection);
		if (file != null) 
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			try 
			{
				while (br.ready()) 
				{gpta.append(br.readLine()+"\r\n");}
			}
			finally 
			{
				br.close();
				fr.close();
			}
		}
	}
	void addgroup(String str) throws IOException
	{
		file=new File("群组列表.txt");
		if (!file.exists()) 
		{file.createNewFile();}
		FileWriter fr = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fr);
		try 
		{
			{bw.write(str+"\r\n");}
		}
		finally 
		{
			bw.close();
			fr.close();
		}
		Client_Thread integ;
		Iterator<String> iter = Server_Network.clientlist.keySet().iterator();
		while (iter.hasNext()) 
		{
			integ = Server_Network.clientlist.get(iter.next());
			try {
				integ.send("addgroup|||"+str);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
