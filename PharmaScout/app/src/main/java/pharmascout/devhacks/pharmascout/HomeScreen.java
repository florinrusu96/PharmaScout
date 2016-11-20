package pharmascout.devhacks.pharmascout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nea Florin on 11/19/2016.
 */

public class HomeScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_creen);

        String search = getString(R.string.search_eng);
        String options = getString(R.string.option_eng);
        String exit = getString(R.string.exit_eng);

        TextView goToSearchText = (TextView) findViewById(R.id.goToSearchText);
        TextView goToOptionsText = (TextView) findViewById(R.id.goToOptionText);
        TextView quitText = (TextView) findViewById(R.id.exitText);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Prime Regular.otf");

        //Search button

        goToSearchText.setTypeface(face);
        goToSearchText.setTextSize(30);
        goToSearchText.setText(search);

        //Option button

        goToOptionsText.setTypeface(face);
        goToOptionsText.setTextSize(30);
        goToOptionsText.setText(options);


        //Quit button

        quitText.setTypeface(face);
        quitText.setTextSize(30);
        quitText.setText(exit);

        //Buttons

        LinearLayout goToSearchButton = (LinearLayout) findViewById(R.id.goToSearch) ;
        LinearLayout goToOptionsButton = (LinearLayout) findViewById(R.id.goToOptions) ;
        LinearLayout goToExitButton = (LinearLayout) findViewById(R.id.exit) ;


    }

    public void searchAction(View view) {
        Intent i=new Intent(HomeScreen.this, SearchActivity.class);
        startActivity(i);
    }

    public void optionsAction(View view) {
        Intent i=new Intent(HomeScreen.this, OptionsScreen.class);
        startActivity(i);
    }

    public void exitAction(View view) {
        finish();
    }
}

