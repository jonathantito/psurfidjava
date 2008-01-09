import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class ProgrammingPanel extends JPanel{
	static final long serialVersionUID = 1L;
	
	private JLabel typeLabel = new JLabel("Select Tag Type:");
	private JLabel tagLabel = new JLabel("Tag ID: ");
	protected JLabel idLabel = new JLabel("No Tag");
	
	protected JComboBox typeCombo = new JComboBox();
	
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	
	public ProgrammingPanel() {
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
		c.gridwidth = 2;	
		c.gridheight = 1;
		c.weightx = 1;
		c.insets = new Insets(0, -20, 0, 0);
		this.add(typeLabel, c);
		
		c.gridx = 0; 		
		c.gridy = 1;				
		c.gridwidth = 2;	
		c.gridheight = 1;

		typeCombo.addItem("SGTIN");
		typeCombo.addItem("SSCC");
		typeCombo.addItem("GID");
		this.add(typeCombo, c);
		
		c.gridx = 2; 		
		c.gridy = 0;
		c.gridwidth = 2;	
		c.gridheight = 1;	
		//c.weightx = 20;
		c.insets.set(10, 100, 0, 0);
		this.add(tagLabel, c);
		
		c.gridx = 2; 		
		c.gridy = 1;				
		c.gridwidth = 2;	
		c.gridheight = 1;			
		this.add(idLabel, c);
	}
}
