package pharmascout.devhacks.pharmascout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Nea Florin on 11/19/2016.
 */

public class HomeScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_creen);
        TextView goToSearchButton = (TextView) findViewById(R.id.goToSearchText);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Prime Regular.otf");
        goToSearchButton.setTypeface(face);
        goToSearchButton.setText("Let's Search");
    }
}

