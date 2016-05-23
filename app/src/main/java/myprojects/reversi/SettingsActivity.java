package myprojects.reversi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.view.View;

public class SettingsActivity extends Activity implements OnClickListener{

    RadioButton classicButton, woodButton;
    RadioGroup radioGroup;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        classicButton = (RadioButton) findViewById(R.id.radioButClassic);
        woodButton = (RadioButton) findViewById(R.id.radioButWood);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        okButton = (Button) findViewById(R.id.settingsOkButton);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent();
        intent.putExtra("STYLE", radioGroup.getCheckedRadioButtonId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
