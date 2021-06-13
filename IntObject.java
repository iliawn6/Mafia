package Mafia;

public class IntObject {
    private int value;

    public IntObject(int value){
        this.value = value;
    }

    public void add(){
        value = value + 1;
    }

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
