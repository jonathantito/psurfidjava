package client_server;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ServerFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;

	JPanel myPanel = new JPanel();
	
	JLabel addrLabel = new JLabel("IP Address: ");
	JLabel portLabel = new JLabel("Port Number: ");
	
	JTextField addrTextField = new JTextField("127.0.0.1");
	JTextField portTextField = new JTextField("20005");

	JButton okButton = new JButton("OK");
	JButton exitButton = new JButton("Exit");

	GridBagLayout gbl = new GridBagLayout();
	
	Driver d;
	
	public ServerFrame(Driver d){
		this.d = d;
		
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
		myPanel.setSize(300,200);
		myPanel.setLayout(gbl);
		myPanel.setVisible(true);
		myPanel.setFocusable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(300,200);
		//this.setResizable(false);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		okButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(3,3,0,0);
		myPanel.add (addrLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 150;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		addrTextField.setEnabled(false);
		myPanel.add (addrTextField, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		myPanel.add (portLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 150;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		myPanel.add (portTextField, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (okButton, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (exitButton, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(myPanel);

		this.validate();
		portTextField.requestFocus();
	}
	
	
	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();
		
		if(eSource == exitButton){
			System.exit(0);
		}
		if(eSource == okButton){
			this.setVisible(false);
			d.connect(addrTextField.getText(),Integer.parseInt(portTextField.getText()));
		}
	}
}
