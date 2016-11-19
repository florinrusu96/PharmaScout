package pharmascout.devhacks.pharmascout;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    }
}
