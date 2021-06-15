package Mafia;

/**
 * this class is for checking voting was created or not
 * @author ilia
 * @version 1.0
 */
public class votingWasCreated {
    //field
    private boolean value;

    //costructor
    public votingWasCreated(boolean value){
        this.value = value;
    }

    /**
     * set value to yes
     */
    public void yes(){
        value = true;
    }

    /**
     * set value to false
     */
    public void no(){
        value = false;
    }
    public boolean getvalue(){
        return value;
    }
}
