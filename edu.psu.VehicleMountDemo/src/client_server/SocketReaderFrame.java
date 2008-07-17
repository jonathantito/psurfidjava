package client_server;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SocketReaderFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;
	
	JPanel mainPanel = new JPanel();
	JButton exitButton = new JButton("Exit");
	JTextArea logTextArea = new JTextArea();
	JScrollPane logScrollPane = new JScrollPane(logTextArea);
	GridBagLayout gbl = new GridBagLayout();
	Connection wmsConnection;
	
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
		mainPanel.setSize(400,400);
		mainPanel.setLayout(gbl);
		mainPanel.setVisible(true);
		mainPanel.setFocusable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(420,420);
		this.setLayout(gbl);
		this.setVisible(false);
		this.setFocusable(true);
		this.setTitle("Tag Collection Server");

		exitButton.addActionListener(this);	
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
			
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 380;
		c.ipady = 300;
		c.gridwidth = 1;
		c.gridheight = 1;
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add (logScrollPane, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		mainPanel.add (exitButton, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(mainPanel, c);
		this.validate();		
	}
	
	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();
		
		if(eSource == exitButton){
			System.exit(0);
		}
	}
}
