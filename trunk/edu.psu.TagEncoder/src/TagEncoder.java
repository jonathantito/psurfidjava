
import javax.swing.JFrame;


public class TagEncoder {
	TagEncoderState tes;
	
	public TagEncoder(JFrame f) {
        tes = TagEncoderState.start(this,f);
    }
    
    public void processEvent(TagEncoderState t){
    	tes = tes.nextState(t);
    }
    
    public TagEncoderState getGameState(){
        return tes;
    }
    
    public void setGameState(TagEncoderState tes){
        this.tes = tes;
    }
}
