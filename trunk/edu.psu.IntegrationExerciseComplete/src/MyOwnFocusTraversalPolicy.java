import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

//// this class sets the tab order in the frame ////
public class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
	
	ReaderFrame f;
	
	public MyOwnFocusTraversalPolicy(ReaderFrame f){
		this.f = f;
	}
	
		//// tab order forward (tab) ////
        public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
            if (aComponent.equals(f.IPTextField)) {
                return f.PortTextField;
            } else if (aComponent.equals(f.PortTextField)) {
                return f.ConnectButton;
            } else if (aComponent.equals(f.ConnectButton)) {
                return f.DisconnectButton;
            } else if (aComponent.equals(f.DisconnectButton)) {
                return f.ClearButton;
            } else if (aComponent.equals(f.ClearButton)) {
                return f.ExitButton;
            } else if (aComponent.equals(f.ExitButton)) {
                return f.CmdTextField;
            } else if (aComponent.equals(f.CmdTextField)) {
                return f.SendButton;
            } else if (aComponent.equals(f.SendButton)) {
                return f.IPTextField;
            }
            return f.IPTextField;
        }

        //// tab order backward (Shift + tab) ////
        public Component getComponentBefore(Container focusCycleRoot,Component aComponent) {
        	if (aComponent.equals(f.IPTextField)) {
                return f.SendButton;
            } else if (aComponent.equals(f.SendButton)) {
                return f.CmdTextField;
            } else if (aComponent.equals(f.CmdTextField)) {
                return f.ExitButton;
            } else if (aComponent.equals(f.ExitButton)) {
                return f.ClearButton;
            } else if (aComponent.equals(f.ClearButton)) {
                return f.DisconnectButton;
            } else if (aComponent.equals(f.DisconnectButton)) {
                return f.ConnectButton;
            } else if (aComponent.equals(f.ConnectButton)) {
                return f.PortTextField;
            } else if (aComponent.equals(f.PortTextField)) {
                return f.IPTextField;
            } 
        	return f.CmdTextField;
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return f.CmdTextField;
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return f.CmdTextField;
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return f.IPTextField;
        }
    }
