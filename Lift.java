package Lift;

final class Button{
    int floor;

    //define the Up ad Down buttons
    static final Button Up = new Button(), Down = new Button();

    //declare the 25 Stop buttons
    static final int NB_OF_FLOORS = 25;
    static final Button[] Stop = new Button[NB_OF_FLOORS];

    static {
        //initialize the 25 Stop buttons
        for (int i=0; i< Button.Stop.length;i++) Button.Stop[i] = new Button(i);
    }

    // the constructors must be private, in order
    // to create no other buttons
    private Button(){}

    private Button(int floor){
        this.floor = floor;
    }

    // test if the button is one of the STOP buttons
    boolean isStop() {
        return this!=Up && this!=Down;
    }

    public int getFloor() {
        return floor;
    }
}


class State {
    protected String action;
    protected State previousState;

    public State getPreviousState() {
        return previousState;
    }
    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void pressButton(Button b, Lift lift){}
    public String getAction(){return action;}
    @Override
    public String toString() {return action;}
}

class Idle extends State{
    {action = "Idle";}
    public void pressButton(Button b, Lift lift){
        if(b == Button.Stop[b.getFloor()]){
            lift.setState(new Halt(b.getFloor()));
        }
        if (b == Button.Down)
        {
            lift.setState(new MovingDown());
        }
        if (b == Button.Up)
        {
            lift.setState(new MovingUp());
        }
        System.out.println(lift);
        super.pressButton(b, lift);
    }

}

class AtTop extends Idle {
    {action= "At top";}

    public void pressButton(Button b, Lift lift){
        if(b == Button.Stop[b.getFloor()]){
            lift.setState(new Halt(b.getFloor()));
        }
        super.pressButton(b, lift);
    }
}

class AtBottom extends Idle {
    {action= "At bottom";}

    public void pressButton(Button b, Lift lift){
        if(b == Button.Stop[b.getFloor()]){
            lift.setState(new Halt(b.getFloor()));
            lift.setFloor(b.getFloor());
        }
        super.pressButton(b, lift);
    }
}

class MovingUp extends Idle{
    {action = "Moving up";}
    public void pressButton(Button b, Lift lift){
        if (b == Button.Up) {

            if (b.isStop()) {
                for (int i = lift.getFloor(); i < b.getFloor(); i++) {
                    lift.setState(new Halt(i + 1));
                }
                lift.setFloor(b.getFloor());
            }else{
                lift.setState(new AtTop());
                lift.setFloor(b.getFloor());
            }
        }
        super.pressButton(b, lift);
    }
}

class MovingDown extends Idle{
    {action = "Moving Down";}
    public void pressButton(Button b, Lift lift){
        System.out.println("asd");
        if (b == Button.Down){

             System.out.println("da");
             for (int i = lift.getFloor() - 1; i >= 0; i--) {
                 lift.setState(new Halt(i + 1));
             }
             lift.setFloor(0);

        }
        super.pressButton(b, lift);
    }
}

class Halt extends State {
    public Halt(int i) {
            action = "Halt" + i;
        }
    public void pressButton(Button b, Lift lift){
        if(b == Button.Stop[b.getFloor()]){
            lift.setState(new Idle());
        }
        if (b == Button.Down)
        {
            lift.setState(new Idle());
        }
        if (b == Button.Up)
        {
            lift.setState(new Idle());
        }
        System.out.println(lift);
        super.pressButton(b, lift);
    }
    }




public class Lift {
    private State state = new AtBottom();
    private int floor;

    void pressButton(Button b) {
        state.pressButton(b, this);
    }

    public void setFloor(int floor){
        this.floor = floor;
    }

    public int getFloor(){
        return this.floor;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        state.setPreviousState(this.state);
        this.state = state;
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public static void main(String[] args) {
        Lift player = new Lift();

        player.pressButton(Button.Stop[4]);
        player.pressButton(Button.Stop[20]);
        player.pressButton(Button.Down);
    }
}
