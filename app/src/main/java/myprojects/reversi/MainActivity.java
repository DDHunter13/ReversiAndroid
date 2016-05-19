package myprojects.reversi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity {

    private GridAdapter revAdapter;
    private GridView fieldGrid;
    private Field field;
    private Player player1;
    private Player player2;
    Thread gameThread;
    int xMovePos, yMovePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xMovePos = -1;
        yMovePos = -1;

        fieldGrid = (GridView) findViewById(R.id.field);
        fieldGrid.setNumColumns(8);
        fieldGrid.setEnabled(true);

        field = new Field(System.out);
        player2 = new HumanPlayer(1);
        player1 = new AIPlayer(2);

        revAdapter = new GridAdapter(this, 8, 8, this.field);
        fieldGrid.setAdapter(revAdapter);
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameProcess();
            }
        });
        gameThread.start();
    }

    private void setMovePos(int i, int j) {
        xMovePos = i;
        yMovePos = j;
    }

    private int[] getMovePos() {
        int[] result = new int[2];
        result[0] = xMovePos;
        result[1] = yMovePos;
        return result;
    }

    private void gameProcess() {
        boolean fff = true;
        while (fff) {
            this.setMovePos(-1, -1);
            if (FieldAnalyzer.playerCheck(field, player1.getColor())) {
                if (player1.getClass().equals(HumanPlayer.class)) {
                    while (FieldAnalyzer.analyze(field, xMovePos, yMovePos, player1.getColor()) == 0) {
                        fieldGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int i = (int) (position / 8);
                                int j = position % 8;
                                setMovePos(i, j);
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
                        //Убрать onItemClickListener
                        fieldGrid.setOnItemClickListener(null);
                    }
                }
                player1.makeMove(field);
            }
            revAdapter.notifyDataSetChanged();


//            try{
//                Thread.sleep(1000);
//            } catch(InterruptedException ex){
//                Thread.currentThread().interrupt();
//            }

            this.setMovePos(-1, -1);
            if (FieldAnalyzer.playerCheck(field, player2.getColor())) {
                if (player2.getClass().equals(HumanPlayer.class)) {
                    while (FieldAnalyzer.analyze(field, xMovePos, yMovePos, player2.getColor()) == 0) {
                        fieldGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int i = (int) (position / 8);
                                int j = position % 8;
                                setMovePos(i, j);
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
                        //Убрать onItemClickListener
                        fieldGrid.setOnItemClickListener(null);
                    }
                }
                player2.makeMove(field);
            }

            if (FieldAnalyzer.gameOver(field)) {
                break;
            }
        }
    }
}
