package Mafia;

public class boleanObject {
    private boolean value;

    public boleanObject(boolean value){
        this.value = value;
    }
    public void yes(){
        value = true;
    }

    public void no(){
        value = false;
    }
    public boolean getvalue(){
        return value;
    }
}
