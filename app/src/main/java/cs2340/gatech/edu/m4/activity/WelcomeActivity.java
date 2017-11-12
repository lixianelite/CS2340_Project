package cs2340.gatech.edu.m4.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs2340.gatech.edu.m4.R;



public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button loginButton2 = findViewById(R.id.login_button2);

        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent (WelcomeActivity.this, LoginActivity.class);
                WelcomeActivity.this.startActivity(loginIntent);
            }
        });

        Button registerButton2 = findViewById(R.id.register_button2);
        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent (WelcomeActivity.this, RegisterActivity.class);
                WelcomeActivity.this.startActivity(registerIntent);
            }
        });

        Button ExitButton = findViewById((R.id.exit_button));
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(WelcomeActivity.this);
                alertbuilder.setMessage("Do you want to exit?");
                alertbuilder.setCancelable(true);
                alertbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alertbuilder.create();
                alert.show();
            }
        });
    }

}
