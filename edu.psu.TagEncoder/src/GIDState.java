import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.accada.tdt.TDTEngine;
import org.accada.tdt.TDTException;
import org.accada.tdt.types.LevelTypeList;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.tags.Tag;

public class GIDState extends TagEncoderState implements ActionListener{
	static final long serialVersionUID = 1L;

	private JLabel headerLabel = new JLabel("Header:");
	private JLabel genManagerLabel = new JLabel("General Manager #:");
	private JLabel objClassLabel = new JLabel("Object Class:");
	private JLabel serialLabel = new JLabel("Serial Number:");

	private JLabel headerLabel2 = new JLabel("Header:");
	private JLabel genManagerLabel2 = new JLabel("General Manager #:");
	private JLabel objClassLabel2 = new JLabel("Object Class:");
	private JLabel serialLabel2 = new JLabel("Serial Number:");

	private JLabel headerLabel3 = new JLabel();
	private JLabel genManagerLabel3 = new JLabel();
	private JLabel objClassLabel3 = new JLabel();
	private JLabel serialLabel3 = new JLabel();

	private JTextField headerText = new JTextField("35");
	private JTextField genManagerText = new JTextField();
	private JTextField objClassText = new JTextField();
	private JTextField serialText = new JTextField();

	private JButton fillButton = new JButton("Fill in");
	private JButton programButton = new JButton("Program Tag");
	private JButton helpButton = new JButton("Help");
	private JButton quitButton = new JButton("Quit");

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();

	public GIDState() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initComponents();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void enter(){
		jf.add(this);
		pp.typeCombo.setSelectedItem("GID");
		jf.repaint();
		jf.validate();
		this.requestFocus();
		
		ls.mls.setMessageListener(this);
	}

	protected void exit(){
		jf.remove(this);
	}

	public TagEncoderState processEvent(TagEncoderState t){
		return t;
	}

	private void initComponents(){
		this.setSize(500,400);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		fillButton.addActionListener(this);
		programButton.addActionListener(this);
		helpButton.addActionListener(this);
		quitButton.addActionListener(this);
		pp.typeCombo.addActionListener(this);
		dp.previewButton.addActionListener(this);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; 		
		c.gridy = 0;				
		c.gridwidth = 4;	
		c.gridheight = 2;		
		c.insets = new Insets(10 ,20, 0, 0);
		pp.typeCombo.setSelectedItem("GID");
		this.add(pp, c);

		c.gridx = 0; 		
		c.gridy = 2;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(40, 20, 0, 0);
		this.add(headerLabel, c);

		c.gridx = 0; 		
		c.gridy = 3;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(10, 20, 0, 0);
		this.add(genManagerLabel, c);

		c.gridx = 0; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(objClassLabel, c);


		c.gridx = 0; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialLabel, c);

		c.gridx = 1; 		
		c.gridy = 2;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(40, 20, 0, 0);
		headerText.setEnabled(false);
		this.add(headerText, c);

		c.gridx = 1; 		
		c.gridy = 3;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(10, 20, 0, 0);
		this.add(genManagerText, c);

		c.gridx = 1; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(objClassText, c);

		c.gridx = 1; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialText, c);

		c.gridx = 2; 		
		c.gridy = 2;				
		c.gridwidth = 1;	
		c.gridheight = 1;
		c.insets.set(40, 20, 0, 0);
		this.add(headerLabel2, c);

		c.gridx = 2; 		
		c.gridy = 3;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(10, 20, 0, 0);
		this.add(genManagerLabel2, c);

		c.gridx = 2; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(objClassLabel2, c);

		c.gridx = 2; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialLabel2, c);

		c.gridx = 3; 		
		c.gridy = 2;				
		c.gridwidth = 1;	
		c.gridheight = 1;
		c.insets.set(40, 20, 0, 0);
		this.add(headerLabel3, c);

		c.gridx = 3; 		
		c.gridy = 3;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		c.insets.set(10, 20, 0, 0);
		this.add(genManagerLabel3, c);

		c.gridx = 3; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(objClassLabel3, c);

		c.gridx = 3; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialLabel3, c);

		c.gridx = 0; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(fillButton, c);

		c.gridx = 1; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(programButton, c);

		c.gridx = 2; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(helpButton, c);

		c.gridx = 3; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;		
		c.ipadx = 20;
		this.add(quitButton, c);
		
		c.gridx = 0; 		
		c.gridy = 9;				
		c.gridwidth = 5;	
		c.gridheight = 3;
		c.ipadx = 0;
		c.insets.set(40, 20, 0, 0);
		this.add(dp, c);
	}			

	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();

		if(eSource == quitButton){
			ls.reader.close();
			ls.mls.stopService();
			System.exit(0);
		}
		
		if(eSource == programButton){
			String errors = null;

			/*if(filterText.getText() == "" || partitionText.getText() == null ||
					compPrefixText.getText() == null || itemRefText.getText() == null ||
					serialText.getText() == null){
				
				errors = errors + "One of the fields are null\n";
			}
			else{
				if(Integer.parseInt(filterText.getText()) < 0 || Integer.parseInt(filterText.getText()) > 3)
					errors = errors + "Incorrect filter\n";						

				if(Integer.parseInt(partitionText.getText()) != 
					(Integer.parseInt(compPrefixText.getText()) + Integer.parseInt(itemRefText.getText())))
					errors = errors + "Incorrect Company Prefix or Item Reference\n";

				if(Integer.parseInt(partitionText.getText()) < 0 || Integer.parseInt(partitionText.getText()) > 6)
					errors = errors + "Partition out of Range\n";
			}*/
			if(errors != null) printErrors(errors);
			else{
				try {
					TDTEngine engine = new TDTEngine("."); // path to directory containing the subdirectories 'schemes' and 'auxiliary'
					HashMap<String,String> extraparams = new HashMap<String, String>(); // a HashMap providing extra parameters needed in addition to the input value

					String inbound = "urn:epc:tag:gid-96:" + genManagerText.getText() + "." + objClassText.getText()
					+ "." +	serialText.getText();

					LevelTypeList outboundformat = LevelTypeList.BINARY; 
					String outbound = engine.convert(inbound, extraparams, outboundformat);

					
					System.out.println(outbound);
					outbound = ls.conv.toHexString(ls.conv.fromBinaryString(outbound), " ", true);
					System.out.println(outbound);
					
					ls.reader.setAutoMode(ls.reader.OFF);
					ls.reader.setNotifyMode(ls.reader.OFF);
					ls.reader.programTag(outbound);
					ls.reader.setAutoMode(ls.reader.ON);
					ls.reader.setNotifyMode(ls.reader.ON);
					
				} catch (MarshalException e1) {
					e1.printStackTrace();
				} catch (ValidationException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (TDTException e1) {
					e1.printStackTrace();
				} catch (AlienReaderException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(eSource == fillButton){
			genManagerText.setText("123456789");
			objClassText.setText("12345678");				
			serialText.setText("123456789");
		}

		if(eSource == helpButton){
			JOptionPane.showMessageDialog(this, 
					"Help is coming!",
					"Help",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if(eSource == pp.typeCombo){
			eSource = pp.typeCombo.getSelectedItem();
			if(eSource == "SGTIN"){
				exit();
				nextState(sgtin);
			}
			if(eSource == "SSCC"){
				exit();
				nextState(sscc);
			}
		}	
		
		if(eSource == dp.previewButton){
			String inbound = "urn:epc:tag:gid-96:" + genManagerText.getText() + "." + objClassText.getText()
			+ "." +	serialText.getText();
			dp.preview(inbound);
		}
	}	

	public void messageReceived(Message message){
		ls.tagList = message.getTagList();

		if (message.getTagCount() == 0) {
			pp.idLabel.setText("NULL");
		} 
		else{
			Tag tag = ls.tagList[0];
			pp.idLabel.setText(tag.getTagID());
			String inbound = ls.conv.toBinaryString(ls.conv.fromHexString(tag.getTagID()), 96);

			try {
				TDTEngine engine = new TDTEngine("."); // path to directory containing the subdirectories 'schemes' and 'auxiliary'
				HashMap<String,String> extraparams = new HashMap<String, String>(); // a HashMap providing extra parameters needed in addition to the input value

				LevelTypeList outboundformat = LevelTypeList.BINARY; // permitted values are 'BINARY', 'TAG_ENCODING', 'PURE_IDENTITY', 'LEGACY' and 'ONS_HOSTNAME' 
				String outbound = engine.convert(inbound, extraparams, outboundformat);			
				String o2 = engine.convert(outbound, extraparams, LevelTypeList.TAG_ENCODING);
				
				o2 = o2.replaceAll(":", ".");	
				String[] uri = o2.split("\\.");

				if(uri[3].equalsIgnoreCase("gid-96")){ 
					uri[3] = "35";		

					headerLabel3.setText(uri[3]);
					genManagerLabel3.setText(uri[4]);
					objClassLabel3.setText(uri[5]);
					serialLabel3.setText(uri[6]);
				}
				else{
					String header = uri[3];

					headerLabel3.setText(header);
					genManagerLabel3.setText("");
					objClassLabel3.setText("");
					serialLabel3.setText("");
				}

				tag.setRenewCount(0);
			} catch (Exception e) { 
				headerLabel3.setText("");
				genManagerLabel3.setText("");
				objClassLabel3.setText("");
				serialLabel3.setText("");
				//e.printStackTrace(System.out);
			}

		}
	}

	public void printErrors(String errors){
		JOptionPane.showMessageDialog(this, 
				errors,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
