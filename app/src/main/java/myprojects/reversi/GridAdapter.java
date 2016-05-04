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

public class GridAdapter extends BaseAdapter{
    private Context cont;
    private int collumns, rows;
    private Vector<Integer> fieldVect;

    public GridAdapter (Context context, int cols, int rows, Field field){
        this.cont = context;
        this.collumns = cols;
        this.rows = rows;
        this.fieldVect = new Vector<Integer>();
        this.makeFieldVect(field);
    }

    private void makeFieldVect(Field field){

        if (!fieldVect.isEmpty()) {
            fieldVect.clear();
        }
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                fieldVect.add(field.getCellState(j, i));
            }
        }
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
                view.setImageResource(R.drawable.white);
                break;
            case 2:
                view.setImageResource(R.drawable.black);
                break;
            default:
                view.setImageResource(R.drawable.empty);
        }
        return view;
    }
}