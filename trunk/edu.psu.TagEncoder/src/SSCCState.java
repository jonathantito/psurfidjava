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

public class SSCCState extends TagEncoderState implements ActionListener{
	static final long serialVersionUID = 1L;

	private JLabel headerLabel = new JLabel("Header:");
	private JLabel filterLabel = new JLabel("Filter:");
	private JLabel partitionLabel = new JLabel("Partition:");
	private JLabel compPrefixLabel = new JLabel("Company Prefix:");
	private JLabel serialRefLabel = new JLabel("Item Reference:");
	private JLabel unallocatedLabel = new JLabel("Unallocated:");

	private JLabel headerLabel2 = new JLabel("Header:");
	private JLabel filterLabel2 = new JLabel("Filter:");
	private JLabel partitionLabel2 = new JLabel("Partition:");
	private JLabel compPrefixLabel2 = new JLabel("Company Prefix:");
	private JLabel serialRefLabel2 = new JLabel("Item Reference:");
	private JLabel unallocatedLabel2 = new JLabel("Unallocated:");

	private JLabel headerLabel3 = new JLabel("");
	private JLabel filterLabel3 = new JLabel("");
	private JLabel partitionLabel3 = new JLabel("");
	private JLabel compPrefixLabel3 = new JLabel("");
	private JLabel serialRefLabel3 = new JLabel("");
	private JLabel unallocatedLabel3 = new JLabel("");

	private JTextField headerText = new JTextField("31");
	private JTextField filterText = new JTextField();
	private JTextField partitionText = new JTextField();
	private JTextField compPrefixText = new JTextField();
	private JTextField serialRefText = new JTextField();
	private JTextField unallocatedText = new JTextField("000000");

	private JButton fillButton = new JButton("Fill in");
	private JButton programButton = new JButton("Program Tag");
	private JButton helpButton = new JButton("Help");
	private JButton quitButton = new JButton("Quit");

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();

	public SSCCState() {
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
		pp.typeCombo.setSelectedItem("SSCC");
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
		c.insets = new Insets(10, 20, 0, 0);
		pp.typeCombo.setSelectedItem("SSCC");
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
		this.add(filterLabel, c);

		c.gridx = 0; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(partitionLabel, c);

		c.gridx = 0; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(compPrefixLabel, c);

		c.gridx = 0; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialRefLabel, c);

		c.gridx = 0; 		
		c.gridy = 7;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(unallocatedLabel, c);

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
		this.add(filterText, c);

		c.gridx = 1; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(partitionText, c);

		c.gridx = 1; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		compPrefixText.setToolTipText("The sum of the company prefix and the\n" +
									  "item reference number must equal 17");
		this.add(compPrefixText, c);

		c.gridx = 1; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialRefText, c);

		c.gridx = 1; 		
		c.gridy = 7;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		unallocatedText.setEnabled(false);
		this.add(unallocatedText, c);

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
		this.add(filterLabel2, c);

		c.gridx = 2; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(partitionLabel2, c);

		c.gridx = 2; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(compPrefixLabel2, c);

		c.gridx = 2; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialRefLabel2, c);

		c.gridx = 2; 		
		c.gridy = 7;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(unallocatedLabel2, c);

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
		this.add(filterLabel3, c);

		c.gridx = 3; 		
		c.gridy = 4;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(partitionLabel3, c);

		c.gridx = 3; 		
		c.gridy = 5;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(compPrefixLabel3, c);

		c.gridx = 3; 		
		c.gridy = 6;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(serialRefLabel3, c);

		c.gridx = 3; 		
		c.gridy = 7;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(unallocatedLabel3, c);

		c.gridx = 0; 		
		c.gridy = 8;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(fillButton, c);

		c.gridx = 1; 		
		c.gridy = 8;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(programButton, c);

		c.gridx = 2; 		
		c.gridy = 8;				
		c.gridwidth = 1;	
		c.gridheight = 1;			
		this.add(helpButton, c);

		c.gridx = 3; 		
		c.gridy = 8;				
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

					String inbound = "urn:epc:tag:sscc-96:" + filterText.getText() + "." + compPrefixText.getText()
					+ "." +	serialRefText.getText();

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
			filterText.setText("0");
			partitionText.setText("6");
			compPrefixText.setText("614141");
			serialRefText.setText("10000000001");
		}

		if(eSource == helpButton){
			JOptionPane.showMessageDialog(this, 
					"Help is coming!",
					"Help",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if(eSource == pp.typeCombo){
			eSource = pp.typeCombo.getSelectedItem();
			if(eSource == "GID"){
				exit();
				nextState(gid);
			}
			if(eSource == "SGTIN"){
				exit();
				nextState(sgtin);
			}
		}		
		
		if(eSource == dp.previewButton){
			String inbound = "urn:epc:tag:sscc-96:" + filterText.getText() + "." + compPrefixText.getText()
			+ "." +	serialRefText.getText();
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
				int partition = 0;

				if(uri[3].equalsIgnoreCase("sscc-96")){ 
					uri[3] = "31";

					partition = 12 - uri[5].length();			

					headerLabel3.setText(uri[3]);
					filterLabel3.setText(uri[4]);
					partitionLabel3.setText(partition + "");
					compPrefixLabel3.setText(uri[5]);
					serialRefLabel3.setText(uri[6]);
				}
				else{
					String header = uri[3];

					headerLabel3.setText(header);
					filterLabel3.setText("");
					partitionLabel3.setText("");
					compPrefixLabel3.setText("");
					serialRefLabel3.setText("");
					unallocatedLabel3.setText("");
				}

				tag.setRenewCount(0);
			} catch (Exception e) { 
				headerLabel3.setText("");
				filterLabel3.setText("");
				partitionLabel3.setText("");
				compPrefixLabel3.setText("");
				serialRefLabel3.setText("");
				unallocatedLabel3.setText("");
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

/*
------------------------------------------------------------------------------------------------ 
Test data = urn.epc.tag.sgtin-96.0.0867360217.005.889520128
		  = 001100000000100000110011101100101101110111011001000000010100000000110101000001010000000000000000
------------------------------------------------------------------------------------------------		  
Pallet Tag Demo:
		  
001100000001101001010111101111110100010010110101101000011100000000000000100110001001011010000001
301A 57BF 44B5 A1C0 0098 9681
		  
0
6
614141
1234567
100000001		  
------------------------------------------------------------------------------------------------
 */