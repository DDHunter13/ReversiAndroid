package myprojects.reversi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity {

    private GridAdapter revAdapter;
    private GridView fieldGrid;
    private Field field;
    private Player player1;
    private Player player2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldGrid = (GridView)findViewById(R.id.field);
        fieldGrid.setNumColumns(8);
        fieldGrid.setEnabled(true);

        fieldGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //продолжение работы приложения и передача координат нажатия
            }
        });

        field = new Field(System.out);
        player1 = new HumanPlayer(1);
        player2 = new AIPlayer(2);

        revAdapter = new GridAdapter(this, 8, 8, this.field);
        fieldGrid.setAdapter(revAdapter);


    }

    private void gameProcess(){
        boolean fff = true;
        while(fff){
            if(FieldAnalyzer.playerCheck(field, player1.getColor())){
                player1.makeMove(field);
            }

            try{
                Thread.sleep(1000);
            } catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }

            if(FieldAnalyzer.playerCheck(field, player2.getColor())){
                player2.makeMove(field);
            }

            if(FieldAnalyzer.gameOver(field)){
                break;
            }
        }
    }
}
