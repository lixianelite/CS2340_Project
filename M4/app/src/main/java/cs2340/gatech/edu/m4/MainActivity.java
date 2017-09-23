package cs2340.gatech.edu.m4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                Intent logoutIntent = new Intent (MainActivity.this, WelcomeActivity.class);
=======
                Intent logoutIntent = new Intent (MainActivity.this, LoginActivity.class);
>>>>>>> master
                MainActivity.this.startActivity(logoutIntent);
                finish();
            }
        });
    }
}