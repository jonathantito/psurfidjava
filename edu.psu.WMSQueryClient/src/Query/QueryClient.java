package Query;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;


public class QueryClient extends JFrame implements ActionListener, ListSelectionListener{

	static final long serialVersionUID = 1L;

	JPanel buttonPanel = new JPanel();
	JPanel asnPanel = new JPanel();
	JPanel queryPanel = new JPanel();

	JButton importASNButton = new JButton("Import ASN");
	JButton queryButton = new JButton("Query");
	JButton exitButton = new JButton("Exit");
	//JButton clearButton = new JButton("Clear Database");

	DefaultListModel asnModel = new DefaultListModel();
	DefaultListModel tagModel = new DefaultListModel();
	JList asnList = new JList(asnModel);
	JList tagList = new JList(tagModel);

	JTextArea asnInfoTextArea = new JTextArea();
	JTextArea tagInfoTextArea = new JTextArea();

	JScrollPane asnInfoScrollPane = new JScrollPane(asnInfoTextArea);
	JScrollPane tagInfoScrollPane = new JScrollPane(tagInfoTextArea);
	JScrollPane asnListScrollPane = new JScrollPane(asnList);
	JScrollPane tagListScrollPane = new JScrollPane(tagList);

	GridBagLayout gbl = new GridBagLayout();

	Connection wmsConnection;
	int quickfix = 0;

	public QueryClient(Connection wmsConnection){
		this.wmsConnection = wmsConnection;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initFrame();
			fillASNList();
		}
		catch(Exception e) {
			System.out.println("Form not created");
		}
	}

	//Initialize the frame
	private void initFrame() throws Exception  {
		//Border border = BorderFactory.createLineBorder(Color.black, 1);

		buttonPanel.setSize(700,100);
		buttonPanel.setLayout(gbl);
		buttonPanel.setVisible(true);
		buttonPanel.setFocusable(true);

		asnPanel.setSize(350,650);
		asnPanel.setLayout(gbl);
		asnPanel.setVisible(true);
		asnPanel.setFocusable(true);

		queryPanel.setSize(350,650);
		queryPanel.setLayout(gbl);
		queryPanel.setVisible(true);
		queryPanel.setFocusable(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(800,800);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);
		this.setTitle("Query Client");

		exitButton.addActionListener(this);
		importASNButton.addActionListener(this);
		queryButton.addActionListener(this);
		asnList.addListSelectionListener(this);
		tagList.addListSelectionListener(this);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,0,0);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (importASNButton, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (queryButton, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (exitButton, c);

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 300;
		c.ipady = 300;
		c.gridwidth = 1;
		c.gridheight = 1;
		asnInfoTextArea.setLineWrap(true);
		asnInfoTextArea.setWrapStyleWord(true);
		asnInfoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		asnPanel.add (asnInfoScrollPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 300;
		c.ipady = 300;
		c.gridwidth = 1;
		c.gridheight = 1;
		asnListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		asnPanel.add(asnListScrollPane, c);

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 300;
		c.ipady = 300;
		c.gridwidth = 1;
		c.gridheight = 1;
		tagInfoTextArea.setLineWrap(true);
		tagInfoTextArea.setWrapStyleWord(true);
		tagInfoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		queryPanel.add (tagInfoScrollPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 300;
		c.ipady = 300;
		c.gridwidth = 1;
		c.gridheight = 1;
		tagListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		queryPanel.add(tagListScrollPane, c);

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(buttonPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(asnPanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(queryPanel, c);

		this.validate();		
	}

	public void valueChanged(ListSelectionEvent e){
		Object eSource = e.getSource();

		if(quickfix == 0){ //for some reason this method was executed twice every time.  
			//					quickfix forces it to execute once.
			if(eSource == asnList){
				String selection = asnList.getSelectedValue().toString();

				int asnID = Integer.parseInt(selection.substring(0, selection.indexOf(" ")));
				tagInfoTextArea.setText("");

				try {
					Statement st = wmsConnection.createStatement();
					String cmd = "SELECT * FROM ASN WHERE ASNID = " + asnID; 				
					ResultSet rs = st.executeQuery(cmd);
					while(rs.next()){
						String result = "";
						result += "ASN ID:\n\t" + rs.getString(1) + "\n";
						result += "Company Name:\n\t" + rs.getString(2) + "\n";
						result += "Date:\n\t" + rs.getString(3) + "\n";
						result += "Time:\n\t" + rs.getString(4) + "\n";
						result += "Desc:\n\t" + rs.getString(5) + "\n";
						asnInfoTextArea.setText(result);
					}

					cmd = "SELECT * FROM TAGS WHERE TAGASNID = " + asnID; 				
					rs = st.executeQuery(cmd);
					tagModel.removeAllElements();
					while(rs.next()){
						String result = "";
						result += rs.getString(1) + "   " + rs.getString(3);
						tagModel.insertElementAt(result, 0);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				quickfix = 1;
			}
			if(eSource == tagList && !tagList.isSelectionEmpty()){
				String selection = tagList.getSelectedValue().toString();

				String tagHexID = selection.substring(0, selection.indexOf(" "));
				try {
					Statement st = wmsConnection.createStatement();
					String cmd = "SELECT * FROM TAGS WHERE TAGHEXID = \"" + tagHexID + "\""; 				
					ResultSet rs = st.executeQuery(cmd);
					while(rs.next()){
						String result = "";
						result += "Hex ID:\n\t" + rs.getString(1) + "\n";
						result += "Desc:\n\t" + rs.getString(3) + "\n";
						
						Statement st1 = wmsConnection.createStatement();
						ResultSet rs1;
						cmd = "SELECT * FROM LOCATIONS WHERE LOCHEXID = \"" + rs.getString(4) + "\"";
						rs1 = st1.executeQuery(cmd);
						result += "Location:\n";
						while(rs1.next()){
							result += "\t" + rs1.getString(2);
						}
						
						cmd = "SELECT * FROM ASN WHERE ASNID = \"" + rs.getString(2) + "\""; 				
						rs1 = st1.executeQuery(cmd);
						while(rs1.next()){
							result += "\nShipped Date:\n\t" + rs1.getString(3) 
									+ " " + rs1.getString(4) + "\n";
						}
					
						cmd = "SELECT * FROM TAGLOCATIONS WHERE TAGHEXID = \"" + tagHexID + "\" LIMIT 1";
						rs1 = st1.executeQuery(cmd);
						result += "Arrival Date:\n";
						while(rs1.next()){
							result += "\t" + rs1.getString(4) 
							+ " " + rs1.getString(5);
						}
						
						cmd = "SELECT * FROM TAGLOCATIONS WHERE TAGHEXID = \"" + tagHexID + "\"";
						rs1 = st1.executeQuery(cmd);
						result += "\nPath:\n";
						while(rs1.next()){
							Statement st2 = wmsConnection.createStatement();
							ResultSet rs2;
							cmd = "SELECT * FROM LOCATIONS WHERE LOCHEXID = \"" + rs1.getString(2) + "\"";
							rs2 = st2.executeQuery(cmd);
							while(rs2.next()){
								result += "\t" + rs2.getString(2);
							}							
							result += "   <" + rs1.getString(4) + " " + rs1.getString(5) + ">\n";
						}						
						
						tagInfoTextArea.setText(result);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				quickfix = 1;
			}
		}
		else
			quickfix = 0;
	}

	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();

		if(eSource == importASNButton){
			JFileChooser chooser = new JFileChooser();
			int selection = chooser.showOpenDialog(this);

			if(selection == JFileChooser.APPROVE_OPTION){
				String file = chooser.getSelectedFile().getPath();

				try {
					BufferedReader in = new BufferedReader(new FileReader(new File(file)));
					String[] str;	        
					str = in.readLine().split("\t");


					// Add asn to database and to asn list
					Statement st = wmsConnection.createStatement();
					String cmd = "INSERT into ASN (asncompanyname,asndate,asntime,asndesc)" + 
					"VALUES(\"" + str[0] + "\",CURDATE(), CURTIME(),\"" + str[1] +"\")";
					st.executeUpdate(cmd);

					cmd = "SELECT LAST_INSERT_ID()";
					ResultSet rs = st.executeQuery(cmd);

					while(rs.next()){
						Statement st1 = wmsConnection.createStatement();
						cmd = "SELECT * FROM ASN WHERE ASNID = " + rs.getString(1);
						ResultSet rs1 = st1.executeQuery(cmd);

						while(rs1.next()){
							String result = "";
							result += rs1.getString(1) + "   " + rs1.getString(2) + "   " + rs1.getString(3);
							System.out.println(result);
							asnModel.insertElementAt(result, 0);


							// add Tags from asn to database
							in.readLine();
							String[] tags;
							System.out.println(str[2]);
							
							for(int i = 0; i < Integer.parseInt(str[2]); i++){
								Statement st2 = wmsConnection.createStatement();

								tags = in.readLine().split("\t");
								cmd = "INSERT into TAGS " +
								"VALUES(\"" + tags[0] + "\",\"" + rs.getString(1) + "\",\"" 
								+ tags[1] + "\",'')";
								System.out.println(cmd);
								st2.executeUpdate(cmd);
							}
						}
					}
					in.close();
				} catch (MySQLIntegrityConstraintViolationException e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "ASN is already in the Table!!");
				} catch (SQLException e2) {
					e2.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Error Reading from asn file!");
				}
			}
		}

		if(eSource == queryButton){
			//JOptionPane.showMessageDialog(this, "Coming soon!");
			JTable queryTable;
			String[] header = {"Name", "Age"};
			String[][] values = {{"Brian", "21"},{"John", "22"}};
			queryTable = new JTable(values, header);
			JOptionPane jop = new JOptionPane();
			jop.add(queryTable.getTableHeader());
			jop.add(queryTable);
			JOptionPane.showMessageDialog(this, "");
		}

		if(eSource == exitButton){
			System.exit(0);
		}
	}

	public void fillASNList(){
		try {
			Statement st = wmsConnection.createStatement();
			String cmd = "SELECT * FROM ASN";
			ResultSet rs = st.executeQuery(cmd);

			while(rs.next()){
				String result = "";
				result += rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3);
				asnModel.insertElementAt(result, 0);
			}			
		} catch (MySQLIntegrityConstraintViolationException e1){
			JOptionPane.showMessageDialog(this, "ASN is already in the Table!!");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
