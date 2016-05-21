package myprojects.reversi;

import java.io.*;

/**
 * Created by DD on 03.05.2016.
 */
public class Field {

    private Cell[][] fieldMatrix;
    private DataOutputStream outstr;

    //Creates the empty field and places 1st 4 chips.
    Field(OutputStream ops){
        this.fieldMatrix = new Cell[8][8];
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                fieldMatrix[i][j] = new Cell();
            }
        }
        this.fieldMatrix[3][3].setState(1);
        this.fieldMatrix[4][4].setState(1);
        this.fieldMatrix[3][4].setState(2);
        this.fieldMatrix[4][3].setState(2);
        this.outstr = new DataOutputStream(ops);
    }

    public void outputField(){
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                try{
                    outstr.writeInt(this.getCellState(i, j));
                    outstr.writeBytes(" ");
                } catch(IOException ex){}
            }
            try{
                outstr.writeBytes("/n");
            } catch(IOException ex){}
        }
        try{
            outstr.writeBytes("/n");
        } catch(IOException ex){}
    }

    public void directionSet (int i, int j, int dx, int dy, int pl){
        int sc = FieldAnalyzer.directionCheck(this, i, j, dx, dy, pl);
        int x = i + dx;
        int y = j + dy;
        for (int k = 0; k < sc; ++k){
            this.fieldMatrix[x][y].setState(pl);
            x += dx;
            y += dy;
        }
    }

    public void setCellState(int i, int j, int st){
        fieldMatrix[i][j].setState(st);
        this.directionSet(i, j, -1, -1, st);
        this.directionSet(i, j, -1, 0, st);
        this.directionSet(i, j, -1, 1, st);
        this.directionSet(i, j, 0, -1, st);
        this.directionSet(i, j, 0, 1, st);
        this.directionSet(i, j, 1, -1, st);
        this.directionSet(i, j, 1, 0, st);
        this.directionSet(i, j, 1, 1, st);
    }

    public void setOneCell(int i, int j, int st){
        fieldMatrix[i][j].setState(st);
    }

    public int getCellState(int i, int j){
        return fieldMatrix[i][j].getState();
    }

}
