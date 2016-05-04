package myprojects.reversi;

/**
 * Created by DD on 03.05.2016.
 */
public interface Player {

    public boolean makeMove(Field field);
    public int[] moveAsk(Field field);
    public int getColor();

}
