package pharmascout.devhacks.pharmascout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Nea Florin on 11/19/2016.
 */

public class OptionsScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_screen);
        //Option text
        TextView optionsText = (TextView) findViewById(R.id.rangeText);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Prime Regular.otf");
        String range = getString(R.string.range_eng);
        optionsText.setTypeface(face);
        optionsText.setTextSize(30);
        optionsText.setText(range);
        final SeekBar sk=(SeekBar) findViewById(R.id.seekBar);
        sk.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                TextView t1=(TextView) findViewById(R.id.sliderText);
                t1.setTextSize(20);
                t1.setText(Integer.toString(progress));
                //Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();

            }
        });
    }

}
