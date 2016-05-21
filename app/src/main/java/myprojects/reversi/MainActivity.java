package myprojects.reversi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private GridAdapter revAdapter;
    private GridView fieldGrid;
    private Field field;
    private Player player1;
    private Player player2;
    int gameFlag;
    Thread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag", "create");
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
        //player1 = new AIPlayer(1);
        player2 = new AIPlayer(2);
        gameFlag = 1;


    }

    private void gameProcess() {
        boolean fff = true;
        while (fff) {
            if (gameFlag % 2 == 1) {
                if (FieldAnalyzer.playerCheck(field, player1.getColor())) {
                    player1.makeMove(field);
                }
            } else {
                if (FieldAnalyzer.playerCheck(field, player2.getColor())) {
                    player2.makeMove(field);
                }
            }
            gameFlag++;
            refreshFieldGrid();
            if (FieldAnalyzer.gameOver(field)) {
                fieldGrid.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getResultString(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            }
        }
    }

    private String getWinnerName(int[] score){
        if (score[0] == score[1]){return "DRAW";}
        return score[0] > score[1] ? "WHITE Player won" : "BLACK Player won";
    }

    private String getFinalScore(int[] score){
        StringBuilder strb = new StringBuilder();
        strb.append(score[0] > score[1] ? Integer.toString(score[0]) : Integer.toString(score[1]));
        strb.append(" - ");
        strb.append(score[0] < score[1] ? Integer.toString(score[0]) : Integer.toString(score[1]));
        return strb.toString();
    }

    private String getResultString(){
        StringBuilder strb = new StringBuilder();
        int[] score = new int[2];
        score = FieldAnalyzer.getScore(field);
        strb.append(getWinnerName(score));
        strb.append(" with the score: ");
        strb.append(getFinalScore(score));
        return strb.toString();
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

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("tag", "resume");
        refreshFieldGrid();
        gameThread.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("tag", "stop");
    }

    @Override
    protected void onPause(){

        super.onPause();
        Log.d("tag", "pause");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("tag", "start");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("tag", "restart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d("tag", "saveState");
        ArrayList<Integer> arList = new ArrayList<>(64);
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                arList.add(field.getCellState(i, j));
            }
        }
        outState.putIntegerArrayList("FIELD", arList);
        outState.putInt("GAME_FLAG", gameFlag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("tag", "loadState");
        ArrayList<Integer> arList = new ArrayList<>(64);
        arList = savedInstanceState.getIntegerArrayList("FIELD");
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                field.setOneCell(i, j, arList.get((i * 8) + j));
            }
        }
        gameFlag = savedInstanceState.getInt("GAME_FLAG");
    }
}
