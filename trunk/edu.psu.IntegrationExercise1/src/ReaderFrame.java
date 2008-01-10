import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReaderFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;
	static ReaderApplication ra;

	static MyOwnFocusTraversalPolicy newPolicy; //sets the tab order in the frame

	//// Declaration of ReaderFrame Components ////
	JLabel IPLabel = new JLabel("IP Address: ");
	JLabel PortLabel = new JLabel("Port Number: ");
	JLabel CmdLabel = new JLabel("Cmd: ");

	JTextField IPTextField = new JTextField("");
	JTextField PortTextField = new JTextField("");
	JTextField CmdTextField = new JTextField("");

	JButton ConnectButton = new JButton("Connect");
	JButton DisconnectButton = new JButton("Disconnect");
	JButton ClearButton = new JButton("Clear");
	JButton ExitButton = new JButton("Exit");
	JButton SendButton = new JButton("Send");

	JTextArea MessageTextArea = new JTextArea();
	JScrollPane MessageScrollPane = new JScrollPane(MessageTextArea);

	//// The ReaderFrame is Organized in a Gridbag Layout ////
	GridBagLayout gbl = new GridBagLayout();
	
	//// CONSTRUCTER ////
	public ReaderFrame(String s) {
		//// Names the frame the value of 's'
		super(s);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			newPolicy = new MyOwnFocusTraversalPolicy(this); //creates new tab policy
			this.setFocusTraversalPolicy(newPolicy); //sets the tab order
		}
		catch(Exception e) {
			MessageTextArea.append("Form not created");
		}
	}
	
	public static void main(String[] args) {
		ra = new ReaderApplication();
	}

	//// Initializes the Frame ////
	private void jbInit() throws Exception  {
		//// Frame Settings ////
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(1100,250);
		this.setResizable(false);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		//// Define the constraints of the layout ////
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		//// Setting the grid coordinates for each object and adding them to the form ////
		//// 'this' refers to the frame ////
		c.gridx = 0; 		//x-coordinate
		c.gridy = 0;		//y-coordinate
		c.ipadx = 0;		//changes pixel length in x direction of object
		c.ipady = 0;		//changes pixel length in y direction of object
		c.gridwidth = 2;	//how many grid blocks the component takes up in the x direction
		c.gridheight = 1;	//how many grid blocks the component takes up in the y direction
		c.weightx = 0;		//thickness of object in x
		c.weighty = 0;		//thickness of object in y
		//// puts a 3 pixel gap on the tops and sides of each object ////
		c.insets = new Insets(3,3,0,0);
		this.add (IPLabel, c);

		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 150;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		//IPTextField.setText("128.118.158.139");
		this.add (IPTextField, c);

		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		this.add (PortLabel, c);

		c.gridx = 2;
		c.gridy = 1;
		c.ipadx = 150;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		PortTextField.addActionListener(this);
		this.add (PortTextField, c);

		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		ConnectButton.addActionListener(this);
		this.add (ConnectButton, c);

		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		DisconnectButton.addActionListener(this);
		this.add (DisconnectButton, c);

		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		ClearButton.addActionListener(this);
		this.add (ClearButton, c);

		c.gridx = 0;
		c.gridy = 5;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		ExitButton.addActionListener(this);
		this.add (ExitButton, c);

		c.gridx = 0;
		c.gridy = 6;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		this.add (CmdLabel, c);

		c.gridx = 1;
		c.gridy = 6;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		CmdTextField.addActionListener(this);
		this.add (CmdTextField, c);

		c.gridx = 6;
		c.gridy = 6;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		SendButton.addActionListener(this);
		this.add (SendButton, c);

		c.gridx = 4;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 3;
		c.gridheight = 6;
		c.weightx = 0;
		c.weighty = 0;
		//// Enables word wrap and vertical scrollbar in the message window ////
		MessageTextArea.setLineWrap(true);
		MessageTextArea.setWrapStyleWord(true);
		MessageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		MessageScrollPane.setPreferredSize(new Dimension(825, 150));
		this.add (MessageScrollPane, c);

		//// This line is necessary to use the objects on the form ////
		this.validate();
		IPTextField.requestFocus();
	}

	public void actionPerformed(ActionEvent e) {
		//// determines which object performed an action (which button was pressed) ////
		Object eSource = e.getSource();

		if(eSource == ConnectButton){
			 ra.ExecuteCommand("Connect");
		}

		if(eSource == DisconnectButton){
			ra.ExecuteCommand("Disconnect");
		}

		if(eSource == ClearButton){
			ra.ExecuteCommand("Clear");
		}

		if(eSource == ExitButton){
			ra.ExecuteCommand("Exit");
		}

		if(eSource == SendButton){
			ra.ExecuteCommand(CmdTextField.getText());
		}

		if(eSource == CmdTextField){
			ra.ExecuteCommand(CmdTextField.getText());
		}

		if(eSource == PortTextField){
			ra.ExecuteCommand("Connect");
			CmdTextField.requestFocus();
		}
	}
}
