package Mafia;

/**
 * the boleanObject class is for creating an object of boolean which is common
 * among players
 * @author ilia
 * @version 1.0
 */

public class boleanObject {
    //field
    private boolean value;

    //constructor
    public boleanObject(boolean value){
        this.value = value;
    }

    /**
     * set value to true
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
