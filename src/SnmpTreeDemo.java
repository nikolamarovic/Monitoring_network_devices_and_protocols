package rm2;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.*;
import com.ireasoning.protocol.snmp.SnmpTableModel;

public class SnmpTreeDemo extends JPanel implements Runnable{
	private static final long serialVersionUID = 3989714180970847868L;
	private JTree tree;
	private DefaultTreeModel defTree;
	private SnmpTableModel table1,table2,table3;
	private ArrayList<DataInfo> descriptionList = new ArrayList<SnmpTreeDemo.DataInfo>();
	private DefaultMutableTreeNode top,router,interfaces;
	private Snmp snmp;
	private class DataInfo extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 1L;
		public String info;
		public DataInfo(String info) {this.info = info;}
		
		public void setString(String s) {this.info = s;}
		
		public String toString() {return info;}
	}
	
	SnmpTreeDemo(SnmpTableModel t1, SnmpTableModel t2, SnmpTableModel t3){
		super(new GridLayout(1,0));
		
		top = new DefaultMutableTreeNode("<html><b color='Dark Gray'>" + "Snmp Tree" + "</b></html>");
		defTree = new DefaultTreeModel(top);
		tree = new JTree(defTree);
		setTables(t1,t2,t3);
		createNodes();
		
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setSize(500, 1000);
		add(treeView);
	}
	
	public SnmpTableModel getTable(int ind) {
		if(ind == 1) return table1;
		if(ind == 2) return table2;
		if(ind == 3) return table3;
		return null;
	}
	
	public void setSnmp(Snmp snmp) {
		this.snmp = snmp;
	}
	public static void printTree(Snmp snmp) {
		JFrame frame = new JFrame("TreeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnmpTreeDemo snmpTree = new SnmpTreeDemo(snmp.getTable(1),snmp.getTable(2), snmp.getTable(3));
		snmpTree.setSnmp(snmp);
		
		frame.add(snmpTree);
		
		frame.setBounds(150,150, 400, 500);
		frame.setSize(450,500);
		frame.setVisible(true);
		
		Thread t = new Thread(snmpTree);
		t.start();
	}

	public void setTables(SnmpTableModel t1, SnmpTableModel t2, SnmpTableModel t3) {
		this.table1 = t1;
		this.table2 = t2;
		this.table3 = t3;
	}
	
	public void createNodes() {
		router = null;
		interfaces = null;
		
		for(int cnt=1;cnt<=3;cnt++) {
			if(cnt == 1) router = new DefaultMutableTreeNode("<html><b color='Dark Gray'>" + "Router R1" + "</b></html>");
			if(cnt == 2) router = new DefaultMutableTreeNode("<html><b color='Dark Gray'>" + "Router R2" + "</b></html>");
			if(cnt == 3) router = new DefaultMutableTreeNode("<html><b color='Dark Gray'>" + "Router R3" + "</b></html>");
			top.add(router);
			for(int i=0;i< getTable(cnt).getColumnCount();i++) {
				
				interfaces = new DefaultMutableTreeNode(new DataInfo("<html><b color='Dark Gray'>"+"Interface: " + getTable(cnt).getColumn(i)[0].getValue().toString()+ "</b></html>"));
				router.add(interfaces);
				for(int j=1;j<getTable(cnt).getColumn(i).length;j++) {
					DataInfo tmp = null;
					//description
					if(j==1) {
						tmp = new DataInfo("<html><b color='Blue'>"+"Description: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>");
						descriptionList.add(tmp);
					}
					//type
					if(j==2) {
						tmp = new DataInfo("<html><b color='Blue'>"+"Type: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>");
						descriptionList.add(tmp);
					}
					//mtu
					if(j==3) {
						tmp = new DataInfo("<html><b color='Blue'>"+"Mtu: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>");
						descriptionList.add(tmp);
					}
					//speed
					if(j==4) {
						tmp = new DataInfo("<html><b color='Blue'>"+"Speed: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>");
						descriptionList.add(tmp);
					}
					//physadd
					if(j==5) {
						tmp = new DataInfo("<html><b color='Blue'>"+"Physical address: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>");
						descriptionList.add(tmp);
					}
					//adminstatus
					if(j==6) {
						String s = null;
						if(getTable(cnt).getColumn(i)[j].getValue().toString().equals("1")) s = "<html><b color='Green'>" + "Administrative status: UP" + "</b></html>";
						else s =  "<html><b color='Red'>" + "Administrative status: DOWN" + "</b></html>";
						tmp = new DataInfo(s);
						descriptionList.add(tmp);
					}
					//operstatus
					if(j==7) {
						String s = null;
						if(getTable(cnt).getColumn(i)[j].getValue().toString().equals("1")) s = "<html><b color='Green'>" + "Operational status: UP" + "</b></html>";
						else s =  "<html><b color='Red'>" + "Operational status: DOWN" + "</b></html>";
						tmp = new DataInfo(s);
						descriptionList.add(tmp);
					}
					interfaces.add(tmp);
				}
			}				
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				snmp.createTables();
				int index = 0;
				for(int cnt = 1;cnt<=3;cnt++) {//po ruterima ide
					for(int i=0;i<snmp.getTable(cnt).getColumnCount();i++) { //po interfejsima
						for(int j=0;j<snmp.getTable(cnt).getColumn(i).length;j++){//po podacima u interfejsima, cvorovima						
							if(j==1) {
								String newData = "<html><b color='Blue'>"+"Description: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>";
								descriptionList.get(index).setString(newData);
								index++;
							}
							if(j==2) {
								String newData = "<html><b color='Blue'>"+"Type: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>";
								descriptionList.get(index).setString(newData);							
								index++;							
							}
							if(j==3) {
								String newData = "<html><b color='Blue'>"+"Mtu: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>";
								descriptionList.get(index).setString(newData);							
								index++;							
							}
							if(j==4) {
								String newData ="<html><b color='Blue'>"+"Speed: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>";						
								descriptionList.get(index).setString(newData);								
								index++;							
							}
							if(j==5) {
								String newData = "<html><b color='Blue'>"+"Physical address: " + getTable(cnt).getColumn(i)[j].getValue().toString()+ "</b></html>";
								descriptionList.get(index).setString(newData);
								index++;								
							}
							if(j==6) {
								String newData = null;
								if(snmp.getTable(cnt).getColumn(i)[j].getValue().toString().equals("1")) newData = "<html><b color='Green'>" + "Administrative status: UP" + "</b></html>";
								else newData =  "<html><b color='Red'>" + "Administrative status: DOWN" + "</b></html>";
								descriptionList.get(index).setString(newData);						
								index++;								
							}
							if(j==7) {
								String newData = null;
								if(snmp.getTable(cnt).getColumn(i)[j].getValue().toString().equals("1")) newData = "<html><b color='Green'>" + "Operational status: UP" + "</b></html>";
								else newData =  "<html><b color='Red'>" + "Operational status: DOWN" + "</b></html>";								
								descriptionList.get(index).setString(newData);						
								index++;
							}
						}
					}
				}
				
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						for(int i=0;i<descriptionList.size();i++) {
							defTree.nodeChanged(descriptionList.get(i));						
						}
					}
			    });
			} catch (IOException e) { }
		}
	}

}
