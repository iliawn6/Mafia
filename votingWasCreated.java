package Mafia;

public class votingWasCreated {
    private boolean value;

    public votingWasCreated(boolean value){
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
