
import java.awt.AWTEvent;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class QueryClient extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;

	JPanel buttonPanel = new JPanel();
	JPanel asnPanel = new JPanel();
	JPanel queryPanel = new JPanel();
	
	JButton importASNButton = new JButton("Import ASN");
	JButton queryButton = new JButton("Query");
	JButton exitButton = new JButton("Exit");
	
	JList asnList = new JList();
	JList tagList = new JList();
		
	JTextArea asnInfoTextArea = new JTextArea();
	JTextArea tagInfoTextArea = new JTextArea();
	
	JScrollPane asnInfoScrollPane = new JScrollPane(asnInfoTextArea);
	JScrollPane tagInfoScrollPane = new JScrollPane(tagInfoTextArea);
	JScrollPane asnListScrollPane = new JScrollPane(asnList);
	JScrollPane tagListScrollPane = new JScrollPane(tagList);

	GridBagLayout gbl = new GridBagLayout();
	
	Connection wmsConnection;
	
	public QueryClient(Connection wmsConnection){
		this.wmsConnection = wmsConnection;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initFrame();
		}
		catch(Exception e) {
			System.out.println("Form not created");
		}
	}

	//Initialize the frame
	private void initFrame() throws Exception  {
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		
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
	
	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();
		
		if(eSource == importASNButton){
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(this);
            String file = chooser.getSelectedFile().getPath();
            System.out.println(file);
            
            try {
    			BufferedReader in = new BufferedReader(new FileReader(new File(file)));
    			String[] str;	        
    			String[] p;

    			str = in.readLine().split("\t");
    			System.out.println(str[0]);
    			/*if(numPallets != 0)    
    				for(int i = 0; i < numPallets; i++){
    					str = in.readLine().split("\t");
    					p = str[2].split(">");
    					String path = "";
    					for(int j = 0; j < p.length; j++)
    						path += p[j] + ">";

    					palletVec.addElement(new Pallet(str[0],str[1], path));
    				}*/

    			in.close();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    			
    			System.out.println("Error Reading from asn file!");
    		}

		}
		
		if(eSource == queryButton){
			JOptionPane.showMessageDialog(this, "Coming soon!");
		}
		
		if(eSource == exitButton){
			System.exit(0);
		}
	}
}
