import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.accada.tdt.TDTEngine;
import org.accada.tdt.TDTException;
import org.accada.tdt.types.LevelTypeList;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

public class DisplayPanel extends JPanel{
	static final long serialVersionUID = 1L;
	
	protected JButton previewButton = new JButton("Preview");
	protected JLabel hexLabel = new JLabel("Hex:     ");
	protected JLabel binLabel = new JLabel("Binary: ");
	protected JLabel binLabel2= new JLabel("        ");
	protected JLabel uriLabel = new JLabel("URI: ");

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	
	public DisplayPanel() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initComponents();
		}
		catch(Exception e) {
			System.out.println("ProgrammingPanel not created");
		}
	}

	private void initComponents(){
		this.setSize(800,400);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; 		
		c.gridy = 0;		
		c.gridwidth = 1;	
		c.gridheight = 1;	
		c.weightx = 1;
		c.insets = new Insets(10, -40, 0, 0);
		this.add(previewButton, c);
		
		c.gridx = 0; 		
		c.gridy = 1;				
		c.gridwidth = 4;	
		c.gridheight = 1;			
		this.add(uriLabel, c);
		
		c.gridx = 0; 		
		c.gridy = 2;				
		c.gridwidth = 2;	
		c.gridheight = 1;		
		this.add(hexLabel, c);
		
		c.gridx = 0; 		
		c.gridy = 3;				
		c.gridwidth = 4;	
		c.gridheight = 1;			
		this.add(binLabel, c);
		
		c.gridx = 0; 		
		c.gridy = 4;				
		c.gridwidth = 4;	
		c.gridheight = 1;			
		this.add(binLabel2, c);
	}
	
	protected void preview(String inbound){
		try {
			TDTEngine engine = new TDTEngine("."); // path to directory containing the subdirectories 'schemes' and 'auxiliary'
			HashMap<String,String> extraparams = new HashMap<String, String>(); // a HashMap providing extra parameters needed in addition to the input value
			LevelTypeList outboundformat = LevelTypeList.BINARY; 
			String outbound = engine.convert(inbound, extraparams, outboundformat);
			
			String hex = TagEncoderState.conv.toHexString
				(TagEncoderState.conv.fromBinaryString(outbound), " ", true);
			
			String bin = TagEncoderState.conv.toBinaryString
			(TagEncoderState.conv.fromBinaryString(outbound), 96);
			
			
			hexLabel.setText("Hex:    " + hex);
			binLabel.setText("Binary: " + bin.substring(0, 47));
			binLabel2.setText("              " + bin.substring(48, 95));
			uriLabel.setText("URI:    " + inbound);
			
		} catch (MarshalException e1) {
			JOptionPane.showMessageDialog(this, 
					"Incorrect Format/nTry the help button.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (ValidationException e1) {
			JOptionPane.showMessageDialog(this, 
					"Incorrect Format/nTry the help button.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(this, 
					"Incorrect Format/nTry the help button.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (TDTException e1) {
			JOptionPane.showMessageDialog(this, 
					"Incorrect Format/nTry the help button.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} 
	}
}
