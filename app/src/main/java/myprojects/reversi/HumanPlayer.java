package myprojects.reversi;

import java.util.Scanner;

/**
 * Created by Asus on 03.05.2016.
 */
public class HumanPlayer implements Player{

    private int color;

    HumanPlayer(int col){
        this.color = col;
    }

    @Override
    public int getColor(){
        return this.color;
    }

    @Override
    public boolean makeMove(Field field){
        int[] res = new int[2];
        res = this.moveAsk(field);
        while(FieldAnalyzer.analyze(field, res[0] - 1, res[1] - 1, color) == 0){
            res = this.moveAsk(field);
        }
        field.setCellState(res[0] - 1, res[1] - 1, color);
        return true;
    }

    @Override
    public int[] moveAsk(Field field){
        Scanner in = new Scanner(System.in);
        int[] res = new int[2];
        res[0] = in.nextInt();
        res[1] = in.nextInt();
        return res;
    }

}
