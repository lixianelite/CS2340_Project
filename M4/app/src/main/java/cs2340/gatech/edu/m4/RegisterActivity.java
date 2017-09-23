package cs2340.gatech.edu.m4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button signup_cancelButton = (Button) findViewById(R.id.signup_cancel_button);
        signup_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_cancelIntent = new Intent (RegisterActivity.this, WelcomeActivity.class);
                RegisterActivity.this.startActivity(signup_cancelIntent);
                finish();
            }
        });
    }
}
