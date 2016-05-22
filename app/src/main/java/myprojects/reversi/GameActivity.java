package myprojects.reversi;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends Activity implements OnClickListener{

    private GridAdapter revAdapter;
    private GridView fieldGrid;
    private Field field;
    private Player player1;
    private Player player2;
    TextView colorPlayer, turnsCount;
    int gameFlag;
    Thread gameThread;
    Button butNewGame;
    Button butSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fieldGrid = (GridView) findViewById(R.id.field);
        fieldGrid.setNumColumns(8);
        fieldGrid.setEnabled(true);

        field = new Field(System.out);

        butNewGame = (Button) findViewById(R.id.newButton);
        butNewGame.setOnClickListener(this);
        butSettings = (Button) findViewById(R.id.setButton);
        butSettings.setOnClickListener(this);
        colorPlayer = (TextView) findViewById(R.id.playerColorText);
        turnsCount = (TextView) findViewById(R.id.turnsCountText);

        revAdapter = new GridAdapter(this, 8, 8, this.field);
        fieldGrid.setAdapter(revAdapter);

        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameProcess();
            }
        });

        Intent intent = getIntent();
        int id1 = intent.getIntExtra("firstPlayer", R.id.radioBut1Human);
        int id2 = intent.getIntExtra("secondPlayer", R.id.radioBut2AI);
        player1 = id1 == R.id.radioBut1Human ? new HumanPlayer(1, gameThread, fieldGrid) : new AIPlayer(1);
        player2 = id2 == R.id.radioBut2Human ? new HumanPlayer(2, gameThread, fieldGrid) : new AIPlayer(2);

        gameFlag = 1;

        gameThread.start();
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
            setTextLabels();
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

    private void setTextLabels(){
        colorPlayer.post(new Runnable() {
            @Override
            public void run() {
                if(gameFlag % 2 == 1){
                    colorPlayer.setText("WHITE Player's turn");
                } else{
                    colorPlayer.setText("BLACK Player's turn");
                }
            }
        });
        turnsCount.post(new Runnable() {
            @Override
            public void run() {
                turnsCount.setText("Turns Count: " + gameFlag);
            }
        });
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.newButton:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.setButton:
                //call settings Activity
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshFieldGrid();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
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
