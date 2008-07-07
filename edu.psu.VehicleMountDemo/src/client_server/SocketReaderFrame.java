package client_server;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SocketReaderFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;

	JPanel myPanel = new JPanel();
	
	JLabel jlAddr = new JLabel("IP Address: ");
	JLabel jlPort = new JLabel("Port: ");
	
	JButton exitButton = new JButton("Exit");
	JTextArea MessageTextArea = new JTextArea();
	JScrollPane MessageScrollPane = new JScrollPane(MessageTextArea);

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
		myPanel.setSize(500,500);
		myPanel.setLayout(gbl);
		myPanel.setVisible(true);
		myPanel.setFocusable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(800,600);
		//this.setResizable(false);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		exitButton.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (jlAddr, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (jlPort, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (exitButton, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(myPanel);
		
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
