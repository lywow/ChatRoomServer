package server_interface_panel;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Data_Reading extends JFrame {
	private static final long serialVersionUID = -1877527354792619586L;
	
	JTree tree;
	DefaultTreeModel newModel;
	DefaultMutableTreeNode Node;
	DefaultMutableTreeNode temp;
	String path;//需要遍历的目录
	public Data_Reading(String path) 
	{
		Node=traverseFolder(path);
		newModel=new DefaultTreeModel(Node);
		tree=new JTree(newModel);
		tree.setEditable(true);
		System.out.println("建立了一棵树");
	}
	public DefaultMutableTreeNode traverseFolder(String path) {
		DefaultMutableTreeNode fujiedian = new DefaultMutableTreeNode(new File(path).getName());
        File file = new File(path);
       
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                if(file.isDirectory()) {//如果是空文件夹
                	DefaultMutableTreeNode dn=new DefaultMutableTreeNode(file.getName(), false);
                	return dn;
                }
            }else{
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	//是目录的话，生成节点，并添加里面的节点
                    	fujiedian.add(traverseFolder(file2.getAbsolutePath()));
                    }else{
                    	//是文件的话直接生成节点，并把该节点加到对应父节点上
                    	temp=new DefaultMutableTreeNode(file2.getName());
                    	fujiedian.add(temp);
                    }
                }
            }
        } else {//文件不存在
            return null;
        }
		return fujiedian;
    }
}
