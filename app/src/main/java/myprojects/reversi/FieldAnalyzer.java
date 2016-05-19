package myprojects.reversi;

/**
 * Created by DD on 03.05.2016.
 */
public class FieldAnalyzer {

    public static boolean playerCheck(Field fl, int pl){
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                int flag = analyze(fl, i, j, pl);
                if (flag != 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean gameOver(Field fl){
        int pl1 = 1;
        int pl2 = 2;
        if(!playerCheck(fl, pl1)){
            if(!playerCheck(fl, pl2)){
                return true;
            }
        }
        return false;
    }

    public static int directionCheck (Field field, int x, int y, int dx, int dy, int pl){
        int flag = 0;
        x += dx;
        y += dy;
        while((x > -1) && (x < 8) && (y < 8) && (y > -1) && (field.getCellState(x, y) != pl)){
            if(field.getCellState(x, y) == 0){
                return 0;
            }
            ++flag;
            x += dx;
            y += dy;
            if((x < 0) || (x > 7) || (y < 0) || (y > 7)){
                return 0;
            }
        }
        return flag;
    }

    public static int analyze(Field fl, int i, int j, int pl){
        if ((i < 0) || (i > 7) || (j < 0) || (j > 7)){
            return 0;
        }
        int flag = 0;
        if (fl.getCellState(i, j) != 0) return 0;

        flag += FieldAnalyzer.directionCheck(fl, i, j, -1, -1, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, -1, 0, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, -1, 1, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, 0, -1, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, 0, 1, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, 1, -1, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, 1, 0, pl);
        flag += FieldAnalyzer.directionCheck(fl, i, j, 1, 1, pl);

        return flag;
    }

}
