package Mafia;

/**
 * the IntObject class is for creating an object of integer which is common
 * among players
 * @author ilia
 * @version 1.0
 */
public class IntObject {
    //field
    private int value;

    //contructor
    public IntObject(int value){
        this.value = value;
    }

    /**
     * plus one
     */
    public void add(){
        value = value + 1;
    }

    /**
     * minese one
     */
    public void minese(){
        value = value - 1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
