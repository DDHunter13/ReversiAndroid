package myprojects.reversi;

/**
 * Created by Asus on 03.05.2016.
 */
public class AIPlayer implements Player {

    private int color;

    AIPlayer(int col){
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
        field.setCellState(res[0], res[1], color);
        return true;
    }

    public int[] chooseMove(Field field){
        int[] result = new int[2];
        int totalScore = 0;
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                int currentScore = FieldAnalyzer.analyze(field, i, j, color);
                if (currentScore != 0){
                    if (((i == 0) && (j == 0)) || ((i == 0) && (j == 7)) || ((i == 7) && (j == 0)) || ((i == 7) && (j == 7))){
                        currentScore += 100;
                    } else{
                        if ((i == 0) || (i == 7) || (j == 0) || (j == 7)){
                            currentScore += 10;
                        }
                    }
                }
                if (currentScore > totalScore){
                    totalScore = currentScore;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }

    @Override
    public int[] moveAsk(Field field){
        return this.chooseMove(field);
    }

}
