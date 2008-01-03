import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProgrammingPanel extends JPanel{
	static final long serialVersionUID = 1L;
	
	//private SGTINPanel sgtin = new SGTINPanel();	
	
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
		this.setSize(500,600);
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; 		
		c.gridy = 0;				
		c.gridwidth = 2;	
		c.gridheight = 1;		
		c.insets = new Insets(10,100, 0, 0);
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
		this.add(tagLabel, c);
		
		c.gridx = 2; 		
		c.gridy = 1;				
		c.gridwidth = 2;	
		c.gridheight = 1;			
		this.add(idLabel, c);
	}
}
