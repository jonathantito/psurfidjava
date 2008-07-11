package client_server;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SocketReaderFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;

	JPanel buttonPanel = new JPanel();
	JPanel asnPanel = new JPanel();
	JPanel queryPanel = new JPanel();
	
	JButton importASNButton = new JButton("Import ASN");
	JButton QueryButton = new JButton("Query");
	JButton exitButton = new JButton("Exit");
	
	JList asnList = new JList();
	JList tagList = new JList();
		
	JTextArea asnInfoTextArea = new JTextArea();
	JTextArea tagInfoTextArea = new JTextArea();
	
	JScrollPane asnInfoScrollPane = new JScrollPane(asnInfoTextArea);
	JScrollPane tagInfoScrollPane = new JScrollPane(tagInfoTextArea);

	GridBagLayout gbl = new GridBagLayout();
	
	public SocketReaderFrame(){
		
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
		buttonPanel.setSize(100,500);
		buttonPanel.setLayout(gbl);
		buttonPanel.setVisible(true);
		buttonPanel.setFocusable(true);
		
		asnPanel.setSize(700,300);
		asnPanel.setLayout(gbl);
		asnPanel.setVisible(true);
		asnPanel.setFocusable(true);
		
		queryPanel.setSize(700,300);
		queryPanel.setLayout(gbl);
		queryPanel.setVisible(true);
		queryPanel.setFocusable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(800,600);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);
		this.setTitle("Query Client");

		exitButton.addActionListener(this);
		importASNButton.addActionListener(this);
		QueryButton.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (importASNButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (QueryButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		buttonPanel.add (exitButton, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(buttonPanel);
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 700;
		c.ipady = (int)(this.getHeight() * .5);
		c.gridwidth = 1;
		c.gridheight = 1;
		MessageTextArea.setLineWrap(true);
		MessageTextArea.setWrapStyleWord(true);
		MessageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add (MessageScrollPane, c);

		this.validate();		
	}
	
	
	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();
		
		if(eSource == exitButton){
			System.exit(0);
		}
	}
}
