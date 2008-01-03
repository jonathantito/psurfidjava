import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


public class TagEncodingFrame extends JFrame{
	static final long serialVersionUID = 1L;
	
	TagEncoder te;
	
	public TagEncodingFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initComponents();
		}
		catch(Exception e) {
			System.out.println("Frame not created");
		}
		
		te = new TagEncoder(this);		
	}
	
	private void initComponents(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(500, 600);
		this.setTitle("Tag Encoder");		
		this.validate();
	}
	
}
