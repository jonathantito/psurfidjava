import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SocketReaderFrame extends JFrame implements ActionListener{

	static final long serialVersionUID = 1L;

	JPanel myPanel = new JPanel();
	JLabel ipLabel = new JLabel("IP Address: ");
	JLabel portLabel = new JLabel("Port Number: ");

	JTextField ipTextField = new JTextField("127.0.0.5");
	JTextField portTextField = new JTextField("20004");

	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");
	JButton exitButton = new JButton("Exit");

	JTextArea MessageTextArea = new JTextArea();
	JScrollPane MessageScrollPane = new JScrollPane(MessageTextArea);

	GridBagLayout gbl = new GridBagLayout();
	
	SocketReader sr;
	
	public SocketReaderFrame(SocketReader sr){
		this.sr = sr;
		
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
		myPanel.setSize(800,500);
		myPanel.setLayout(gbl);
		myPanel.setVisible(true);
		myPanel.setFocusable(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(800,990);
		this.setResizable(false);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(3,3,0,0);
		myPanel.add (ipLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 150;
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		myPanel.add (ipTextField, c);
		
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
		startButton.setEnabled(false);
		myPanel.add (startButton, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		myPanel.add (stopButton, c);
		
		c.gridx = 0;
		c.gridy = 4;
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
		c.ipady = 800;
		c.gridwidth = 1;
		c.gridheight = 1;
		MessageTextArea.setLineWrap(true);
		MessageTextArea.setWrapStyleWord(true);
		MessageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add (MessageScrollPane, c);

		this.validate();
		ipTextField.requestFocus();
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		Object eSource = e.getSource();
		
		if(eSource == exitButton){
			System.exit(0);
		}
		
		if(eSource == startButton){
			try {
				sr.sendReports = true;
				sr.getReport();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(eSource == stopButton){
			sr.sendReports = false;
		}
	}

}
