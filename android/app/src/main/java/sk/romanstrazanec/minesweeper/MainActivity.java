package sk.romanstrazanec.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newGame(View view) {
        startActivity(new Intent(MainActivity.this, GameActivity.class));
    }

    public void settings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }
}
