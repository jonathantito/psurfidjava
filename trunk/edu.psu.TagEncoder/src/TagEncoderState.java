
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.alien.enterpriseRFID.notify.MessageListener;
import com.alien.enterpriseRFID.util.Converters;


public abstract class TagEncoderState extends JPanel implements MessageListener{
    public static TagEncoder te;
    public static JFrame jf;
    
    public static LoginState ls;
    public static SGTINState sgtin;
    public static GIDState gid;
    public static SSCCState sscc;
    
    protected static Converters conv = new Converters();
    protected DisplayPanel dp = new DisplayPanel();
    protected ProgrammingPanel pp = new ProgrammingPanel();
    
    public static TagEncoderState start(TagEncoder te, JFrame jf){
    	TagEncoderState.te = te;
    	TagEncoderState.jf = jf;
        ls = new LoginState();
        sgtin = new SGTINState();
        gid = new GIDState();
        sscc = new SSCCState();
        ls.enter();
        return ls;
    }
    
    public abstract TagEncoderState processEvent(TagEncoderState t);
    protected abstract void enter();
    protected abstract void exit();
    
    public TagEncoderState nextState(TagEncoderState newState){
        if (this == newState) return this;
        else {
            this.exit();
            newState.enter();
            repaint();
            return newState;
        }
    }
    
    public String toString(){
        if (this == ls) return "LoginState";
        return "?";
    }

}
