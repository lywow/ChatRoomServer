package server_interface;

import java.awt.*;
import server_interface_panel.*;
import java.awt.event.*;
import javax.swing.*;

public class Server_Interface {
	static JFrame si;
	static User_Panel up=new User_Panel();
	static Group_Panel gp=new Group_Panel();
	static JPanel si_card=new JPanel();
	static CardLayout card=new CardLayout();
	static JButton sibu,sibg;
	static JLabel sil;
	
	Server_Interface()
	{
		si=new JFrame("聊天系统服务器管理界面");
		si.setSize(600, 738);
		si.setResizable(false);
		si.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		si.setLayout(null);
		si.setLocationRelativeTo(null);
		
		sibu=new JButton("用户列表");sibu.setBounds(0, 0, 150, 40);si.add(sibu);
		sibg=new JButton("群组列表");sibg.setBounds(150, 0, 150, 40);si.add(sibg);
		sil=new JLabel("当前用户列表");sil.setBounds(300, 0, 300, 40);si.add(sil);
		
		si_card.setLayout(card);
		si_card.setBounds(0, 40, 600, 660);
		si_card.add(up.get_panel(),"user");si_card.add(gp.get_panel(),"group");si.add(si_card);
		sibu.addActionListener(new ActionListener()//跳转个人信息界面
		{public void actionPerformed(ActionEvent e){sil.setText("当前用户列表");card.show(si_card,"user");}});
		sibg.addActionListener(new ActionListener()//跳转好友列表界面
		{public void actionPerformed(ActionEvent e){sil.setText("当前群组列表");card.show(si_card,"group");}});
		
		/*up.gettree().addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e) {
				// 设置选择可以包含任何数量的项，这些项不必是连续的。
				up.get_user_tree().getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
				// 获取选中节点
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)up.get_user_tree().getLastSelectedPathComponent();
				if (treeNode != null) 
				{// 获取选中节点的父节点
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode.getParent();
					if (!pding.equals(parent)) 
					{
						System.out.println(".......");
						selection=selection+parent+"\\"+treeNode;
						try {
							gp.setTextArea(selection);
						} catch (Exception e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
						selection="C:\\Users\\Administrator\\Desktop\\新建文件夹\\用户资料\\";
					}
				}
			}
		}); */
	}
	
	void server_interface_display()
	{si.setVisible(true);}
}
