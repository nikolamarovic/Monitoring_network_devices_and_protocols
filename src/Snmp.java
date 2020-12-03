package rm2;

import java.io.IOException;
import com.ireasoning.protocol.snmp.*;

public class Snmp {
	private static final String ids[] = {"192.168.10.1","192.168.20.1","192.168.30.1"};
	private static final String communityString = "si2019";
	private static final int port = 161;
	private static final String getIndex = ".1.3.6.1.2.1.2.2.1.1";
	private static final String getDescription = ".1.3.6.1.2.1.2.2.1.2";
	private static final String getType = ".1.3.6.1.2.1.2.2.1.3";
	private static final String getMtu = ".1.3.6.1.2.1.2.2.1.4";
	private static final String getSpeed= ".1.3.6.1.2.1.2.2.1.5";
	private static final String getPhysAddress = ".1.3.6.1.2.1.2.2.1.6";
	private static final String getAdminStatus = ".1.3.6.1.2.1.2.2.1.7";
	private static final String getOperStatus = ".1.3.6.1.2.1.2.2.1.8";
	private SnmpTableModel r1Table,r2Table,r3Table;
	private SnmpSession sessions[];
	public void createSessions() {
		SnmpSession.loadMib2();
		sessions = new SnmpSession[3];
		for(int i=0;i<3;i++)
			try {
				sessions[i] = new SnmpSession(ids[i],port,communityString,communityString,1);
			} catch (IOException e) { }
	}
	public void createTables() throws IOException{
		r1Table = new SnmpTableModel("Router 1");
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getIndex)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getDescription)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getType)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getMtu)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getSpeed)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getPhysAddress)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getAdminStatus)));
		r1Table.addRow(sessions[0].snmpGetTableColumn(new SnmpOID(getOperStatus)));	

		r2Table = new SnmpTableModel("Router 2");
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getIndex)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getDescription)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getType)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getMtu)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getSpeed)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getPhysAddress)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getAdminStatus)));
		r2Table.addRow(sessions[1].snmpGetTableColumn(new SnmpOID(getOperStatus)));

		r3Table = new SnmpTableModel("Router 3");
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getIndex)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getDescription)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getType)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getMtu)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getSpeed)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getPhysAddress)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getAdminStatus)));
		r3Table.addRow(sessions[2].snmpGetTableColumn(new SnmpOID(getOperStatus)));
	}
	public void deleteTables() {
		r1Table.removeAll();
		r2Table.removeAll();
		r3Table.removeAll();
	}
	public void printTablesConsole() {
		System.out.println("R1");
		for(int i=0;i< r1Table.getColumnCount();i++) { 
			for(int j=0;j<r1Table.getColumn(i).length;j++) 
				System.out.print(r1Table.getColumn(i)[j].getValue().toString()+" ");
			System.out.println("");
		}
		
		System.out.println("R2");
		for(int i=0;i< r2Table.getColumnCount();i++) { 
			for(int j=0;j<r2Table.getColumn(i).length;j++) 
				System.out.print(r2Table.getColumn(i)[j].getValue().toString()+" ");
			System.out.println("");
		}
		
		System.out.println("R3");
		for(int i=0;i< r3Table.getColumnCount();i++) { 
			for(int j=0;j<r3Table.getColumn(i).length;j++) 
				System.out.print(r3Table.getColumn(i)[j].getValue().toString()+" ");
			System.out.println("");
		}
	}
	public SnmpTableModel getTable(int index) {
		if(index==1) return r1Table;
		else if(index==2) return r2Table;
		else if(index==3)return r3Table;
		return null;
	}
	public static void callInvokeRoutine(Snmp snmp) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SnmpTreeDemo.printTree(snmp);
			}
	    });
	}
	
	public static void main(String[] args) throws IOException {	
		Snmp snmp = new Snmp();
		snmp.createSessions();
		snmp.createTables();
		callInvokeRoutine(snmp);
	}
}
