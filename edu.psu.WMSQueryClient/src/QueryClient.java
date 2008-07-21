
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
						result += rs.getString(1) + "   " + rs.getString(5);
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
						result += "Location:\n\t" + rs.getString(3) + "\n";
						result += "Path:\n\t" + rs.getString(4) + "\n";
						result += "Desc:\n\t" + rs.getString(5) + "\n";
						result += "Shipping Date:\n\t" + rs.getString(6) + "\n";
						result += "Arrival Date:\n\t" + rs.getDate(7) + "\n";
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
					String cmd = "INSERT into ASN " + 
					"VALUES(" + str[0] + ",\"" + str[1] + "\",CURDATE(), CURTIME(),\"" + str[2] +"\")";
					st.executeUpdate(cmd);

					cmd = "SELECT * FROM ASN WHERE ASNID = " + str[0];
					ResultSet rs = st.executeQuery(cmd);

					while(rs.next()){
						String result = "";
						result += rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3);
						System.out.println(result);
						asnModel.insertElementAt(result, 0);


						// add Tags from asn to database
						in.readLine();
						String[] tags;
						Date d = null;
						Time t = null;
						System.out.println(str[3]);
						for(int i = 0; i < Integer.parseInt(str[3]); i++){
							Statement st1 = wmsConnection.createStatement();
							
							tags = in.readLine().split("\t");
							cmd = "INSERT into TAGS " +
							"VALUES(\"" + tags[0] + "\",\"" + str[0] + "\", '', '',\"" 
							+ tags[1] + "\", CURDATE(), " + d + ")";
							st1.executeUpdate(cmd);

							cmd = "INSERT into tagLocations " +
							"VALUES('', \"" + tags[0] + "\", '', ''," + d + "," + t + ")";
							st1.executeUpdate(cmd);
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
			JOptionPane.showMessageDialog(this, "Coming soon!");
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
