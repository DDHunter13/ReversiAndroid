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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldGrid = (GridView) findViewById(R.id.field);
        fieldGrid.setNumColumns(8);
        fieldGrid.setEnabled(true);

        field = new Field(System.out);

        revAdapter = new GridAdapter(this, 8, 8, this.field);
        fieldGrid.setAdapter(revAdapter);
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameProcess();
            }
        });

        player1 = new HumanPlayer(1, gameThread, fieldGrid);
        player2 = new AIPlayer(2);

        gameThread.start();
    }

    private void gameProcess() {
        boolean fff = true;
        while (fff) {
            if (FieldAnalyzer.playerCheck(field, player1.getColor())) {
                player1.makeMove(field);
                refreshFieldGrid();
            }


            if (FieldAnalyzer.playerCheck(field, player2.getColor())) {
                player2.makeMove(field);
                refreshFieldGrid();
            }

            if (FieldAnalyzer.gameOver(field)) {
                break;
            }
        }
    }

    private void refreshFieldGrid(){
        fieldGrid.post(new Runnable() {
            @Override
            public void run() {
                revAdapter.makeFieldVect(field);
                revAdapter.notifyDataSetChanged();
            }
        });
    }
}
