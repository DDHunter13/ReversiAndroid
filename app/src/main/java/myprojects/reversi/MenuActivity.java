package myprojects.reversi;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MenuActivity extends Activity implements OnClickListener {

    RadioGroup firstGroup, secondGroup;
    RadioButton radBut11, radBut12, radBut21, radBut22;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startButton = (Button) findViewById(R.id.startButton);
        radBut11 = (RadioButton) findViewById(R.id.radioBut1Human);
        radBut12 = (RadioButton) findViewById(R.id.radioBut1AI);
        radBut21 = (RadioButton) findViewById(R.id.radioBut2Human);
        radBut22 = (RadioButton) findViewById(R.id.radioBut2AI);
        firstGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        secondGroup = (RadioGroup) findViewById(R.id.radioGroup2);

        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("firstPlayer", firstGroup.getCheckedRadioButtonId());
        intent.putExtra("secondPlayer", secondGroup.getCheckedRadioButtonId());
        startActivity(intent);
    }

}
