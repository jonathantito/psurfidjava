import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ReaderPanel extends JPanel{
	static final long serialVersionUID = 1L;
	
	private JLabel readerNameLabel = new JLabel("");
	private JLabel readerIPLabel = new JLabel("");
	private JLabel readerPicLabel = new JLabel("");
	
	GridBagLayout gbl = new GridBagLayout();
	String name;
	String ip;
	
	public ReaderPanel(String name, String ip) {
		this.name = name;
		this.ip = ip;
		
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initComponents();			
		}
		catch(Exception e) {
			System.out.println("Panel not created");
		}
	}
	
	private void initComponents(){
		this.setLayout(gbl);
		this.setVisible(true);
		this.setFocusable(true);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; 		
		c.gridy = 0;				
		c.gridwidth = 1;	
		c.gridheight = 2;			
		c.insets = new Insets(0,20,0,0);
		ImageIcon head = new ImageIcon("Head.jpg");
		readerPicLabel.setIcon(head);
		this.add(readerPicLabel, c);
		
		c.gridx = 1; 		
		c.gridy = 0;				
		c.gridwidth = 1;	
		c.gridheight = 1;		
		readerNameLabel.setText(name);
		this.add(readerNameLabel, c);
		
		c.gridx = 1; 		
		c.gridy = 1;				
		c.gridwidth = 1;	
		c.gridheight = 1;	
		readerIPLabel.setText(ip);
		this.add(readerIPLabel, c);
	}
}
