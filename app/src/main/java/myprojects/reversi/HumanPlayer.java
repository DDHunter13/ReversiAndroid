package myprojects.reversi;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Scanner;

/**
 * Created by Asus on 03.05.2016.
 */
public class HumanPlayer implements Player{

    private int color;
    Thread gameThread;
    GridView gameGrid;

    HumanPlayer(int col, Thread thread, GridView grid){
        this.color = col;
        this.gameGrid = grid;
        this.gameThread = thread;
    }

    @Override
    public int getColor(){
        return this.color;
    }

    @Override
    public boolean makeMove(Field field){
        int[] res = new int[2];
        res = this.moveAsk(field);
        while(FieldAnalyzer.analyze(field, res[0], res[1], color) == 0){
            res = this.moveAsk(field);
        }
        field.setCellState(res[0], res[1], color);
        return true;
    }

    @Override
    public int[] moveAsk(Field field){
        final int[] res = new int[2];

        gameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                res[1] = (int) (position / 8);
                res[0] = position % 8;
                synchronized (gameThread) {
                    gameThread.notify();
                }
            }
        });
        synchronized (gameThread) {
            try {
                gameThread.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        gameGrid.setOnItemClickListener(null);

        return res;
    }

}
