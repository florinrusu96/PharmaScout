package pharmascout.devhacks.pharmascout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //comment pus de Nelu
    }

    public void mapStartButton(View obj){
        Bundle coords = new Bundle();
        Random rand = new Random();
        coords.putDouble("latitude", 90 * rand.nextDouble());
        coords.putDouble("longitude", 90 * rand.nextDouble());

        Intent intent = new Intent(SearchActivity.this, MapsActivity.class );
        intent.putExtras(coords);
        startActivity(intent);
    }
}
