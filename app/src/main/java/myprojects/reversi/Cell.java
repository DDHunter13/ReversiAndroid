package myprojects.reversi;

/**
 * Created by DD on 03.05.2016.
 */
public class Cell {
    private int state;

    Cell(){
        this.state = 0;
    }

    public int getState(){
        return this.state;
    }

    public void setState(int st){
        this.state = st;
    }
}
