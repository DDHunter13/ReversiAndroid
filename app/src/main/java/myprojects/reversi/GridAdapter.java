package myprojects.reversi;

/**
 * Created by DD on 24.04.2016.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Vector;

enum Style{CLASSIC, WOOD, METAL}

public class GridAdapter extends BaseAdapter{
    private Context cont;
    private int collumns, rows;
    private Vector<Integer> fieldVect;
    Style style;

    public GridAdapter (Context context, int cols, int rows, Field field){
        this.cont = context;
        this.collumns = cols;
        this.rows = rows;
        this.style = Style.CLASSIC;
        this.fieldVect = new Vector<Integer>();
        this.makeFieldVect(field);
    }

    public void makeFieldVect(Field field){
        if (!fieldVect.isEmpty()) {
            fieldVect.clear();
        }
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                fieldVect.add(field.getCellState(j, i));
            }
        }
    }

    public void setStyle(Style st){
        this.style = st;
    }

    @Override
    public int getCount(){
        return (this.collumns * this.rows);
    }

    @Override
    public Object getItem (int position){
        return null;
    }

    @Override
    public long getItemId (int position){
        return 0;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        ImageView view;

        if (convertView == null)
            view = new ImageView(cont);
        else
            view = (ImageView) convertView;

        switch(fieldVect.get(position)){
            case 1:
                view.setImageResource(whiteSetStyle());
                break;
            case 2:
                view.setImageResource(blackSetStyle());
                break;
            default:
                view.setImageResource(emptySetStyle());
        }
        return view;
    }

    private int emptySetStyle(){
        switch(style){
            case METAL:
                return R.drawable.empty_metal;
            case WOOD:
                return R.drawable.empty_wood;
            default:
                return R.drawable.empty;
        }
    }

    private int blackSetStyle(){
        switch(style){
            case METAL:
                return R.drawable.black_metal;
            case WOOD:
                return R.drawable.black_wood;
            default:
                return R.drawable.black;
        }
    }

    private int whiteSetStyle(){
        switch(style){
            case METAL:
                return R.drawable.white_metal;
            case WOOD:
                return R.drawable.white_wood;
            default:
                return R.drawable.white;
        }
    }


}