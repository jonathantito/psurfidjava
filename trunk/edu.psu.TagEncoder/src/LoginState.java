import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

import com.alien.enterpriseRFID.discovery.AlienDiscoverySocketException;
import com.alien.enterpriseRFID.discovery.DiscoveryItem;
import com.alien.enterpriseRFID.discovery.DiscoveryListener;
import com.alien.enterpriseRFID.discovery.NetworkDiscoveryListenerService;
import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.notify.MessageListenerService;
import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderConnectionException;
import com.alien.enterpriseRFID.reader.AlienReaderConnectionRefusedException;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.reader.AlienReaderNotValidException;
import com.alien.enterpriseRFID.reader.AlienReaderTimeoutException;
import com.alien.enterpriseRFID.tags.Tag;

public class LoginState extends TagEncoderState implements ActionListener, DiscoveryListener {
	static final long serialVersionUID = 1L;

	private JPanel readerListPanel = new JPanel();
		
	private JButton loginButton = new JButton("Login");
	private JButton scanButton = new JButton("Start Scan");
	private JButton quitButton = new JButton("Quit");

	private JLabel  userNameLabel = new JLabel("User Name:");
	private JLabel  passwordLabel = new JLabel("Password:");

	private JTextField userNameTextField = new JTextField("alien");
	private JTextField passwordTextField = new JTextField("password");

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private boolean scanning;
	private NetworkDiscoveryListenerService ndls;
	private String loginIP;
	private Vector<ReaderPanel> vrp = new Vector<ReaderPanel>();
	protected AlienClass1Reader reader;
	private int mlsPort = 4000;
	protected MessageListenerService mls = new MessageListenerService(mlsPort);
	private ServerSocket srvr;
	private Socket skt;	
	protected Tag tagList[];

	int readerPosition = 0;

	public LoginState() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initComponents();
			scanning = false;
		}
		catch(Exception e) {
			System.out.println("LoginPanel not created");
		}
	}
	
	public LoginState(InetSocketAddress addr) throws IOException {
		srvr = new ServerSocket();
		srvr.bind(addr);
	}
	
	protected void enter(){
        jf.add(this);
        jf.repaint();
        jf.validate();
        this.requestFocus();
    }
    
    protected void exit(){
        jf.remove(this);
    }
    
    public TagEncoderState processEvent(TagEncoderState t){
    	return ls;
    }

	private void initComponents(){
		this.setSize(500,600);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		loginButton.addActionListener(this);
		scanButton.addActionListener(this);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; 		
		c.gridy = 0;				
		c.gridwidth = 3;	
		c.gridheight = 10;			
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		readerListPanel.setBorder(loweredbevel);
		Dimension d = new Dimension(225, 500);
		readerListPanel.setPreferredSize(d);
		this.add(readerListPanel, c);

		c.gridx = 3; 		
		c.gridy = 0;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		c.insets = new Insets(10,30,0,0);
		this.add(userNameLabel, c);

		c.gridx = 3; 		
		c.gridy = 1;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		this.add(userNameTextField, c);

		c.gridx = 3; 		
		c.gridy = 2;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		this.add(passwordLabel, c);

		c.gridx = 3; 		
		c.gridy = 3;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		this.add(passwordTextField, c);

		c.gridx = 3; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		this.add(scanButton, c);

		c.gridx = 3; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		loginButton.setEnabled(false);
		this.add(loginButton, c);
	}

	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();

		if(eSource == quitButton){
			ndls.stopService();
			scanning = false;
			reader.close();
			System.exit(0);
		}

		if(eSource == scanButton){
			//loginButton.setEnabled(true);
			if(!scanning){
				scanButton.setText("Stop Scan");
				scanning = true;

				try {
					ndls = new NetworkDiscoveryListenerService();
					ndls.setDiscoveryListener(this);
					ndls.startService();
				} catch (AlienDiscoverySocketException e1) {
					JOptionPane.showMessageDialog(this, 
							"You have another reader discover application open!\nClose it please.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					
					ndls.stopService();
					scanButton.setText("Start Scan");
					scanning = false;							
				}
			}
			else if(scanning){
				ndls.stopService();
				scanButton.setText("Start Scan");
				scanning = false;
				if(vrp.size() == 0) loginButton.setEnabled(false);
			}
		}

		if(eSource == loginButton){
			try {
				InetAddress my_addr = InetAddress.getByName("127.0.0.5");
				LoginState lp = new LoginState(new InetSocketAddress(my_addr, 20005));

				reader = new AlienClass1Reader();
				reader.setConnection(loginIP, 23);
				reader.setUsername("alien");
				reader.setPassword("password");
				reader.open();
				
				// CONFIG SETTINGS			
				reader.autoModeReset();
				reader.setNotifyMode(AlienClass1Reader.OFF);
				reader.setAutoMode(AlienClass1Reader.OFF);
				reader.setAntennaSequence("0,1");
				reader.setRFAttenuation(70);
				reader.setTagType(16);
				reader.setPersistTime(5);
				reader.setAcquireMode("Global Scroll");
				reader.setReaderFunction(AlienClass1Reader.FUNCTION_PROGRAMMER);
				reader.setAutoAction("Acquire");

				reader.setNotifyAddress(InetAddress.getLocalHost().getHostAddress(), mlsPort); 
				reader.setNotifyFormat(AlienClass1Reader.XML_FORMAT);
				reader.setNotifyTrigger("TrueFalse"); 
				
				if(!mls.isRunning()){
					mls.setMessageListener(this);
					mls.startService();
				}			

				reader.setNotifyMode(AlienClass1Reader.ON);
				reader.setAutoMode(AlienClass1Reader.ON);

				Thread.sleep(1000);

				System.out.println("Message Listener has Started\n");
				
				//System.out.println(reader.getTagID());
				//skt = srvr.accept();
			} catch (AlienReaderConnectionRefusedException e1) {
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (AlienReaderNotValidException e1) {
				e1.printStackTrace();
			} catch (AlienReaderTimeoutException e1) {
				e1.printStackTrace();
			} catch (AlienReaderConnectionException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (AlienReaderException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
			ndls.stopService();
			scanning = false;
			exit();
			nextState(sgtin);
		}
	}

	public void readerAdded(DiscoveryItem discoveryItem){
		loginButton.setEnabled(true);
		final String name = discoveryItem.getReaderName();
		final String ip = discoveryItem.getReaderAddress();

		vrp.addElement(new ReaderPanel
				(discoveryItem.getReaderName(), discoveryItem.getReaderAddress()));

		c.gridx = 0; 		
		c.gridy = readerPosition;				
		c.gridwidth = 2;	
		c.gridheight = 2;	
		readerListPanel.add(vrp.lastElement(), c);

		readerPosition += 2;

		vrp.lastElement().addMouseListener(new MouseAdapter(){
			boolean highlighted = false;
			Color notHighlighted = new Color(238, 238, 238);

			public void mouseClicked(MouseEvent me) { 
				loginIP = ip;
				
				for(int i= 0; i < vrp.size(); i++){
					if(vrp.elementAt(i).ip == ip){
						vrp.elementAt(i).setBackground(Color.YELLOW.brighter());
						highlighted = true;
					}
					else{
						vrp.elementAt(i).setBackground(notHighlighted);
						highlighted = false;
					}
				} 
			}
		});
		this.validate();
	}

	public void readerRenewed(DiscoveryItem discoveryItem){
	}

	public void readerRemoved(DiscoveryItem discoveryItem){
	}
	
	public void messageReceived(Message message){
		
	}
}
