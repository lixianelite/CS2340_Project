package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DatabaseHelper;
import cs2340.gatech.edu.m4.model.User;

public class RegisterActivity extends AppCompatActivity {

    private Spinner rUserType;
    private EditText rUserView;
    private EditText rPasswordView;
    private EditText rEmail;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rUserView = (EditText) findViewById(R.id.signup_username);
        rPasswordView = (EditText) findViewById(R.id.signup_password);
        rUserType = (Spinner) findViewById(R.id.signup_usertype);
        rEmail = (EditText) findViewById(R.id.signup_email);

        Button signup_cancelButton = (Button) findViewById(R.id.signup_cancel_button);
        Spinner user_typeSpinner = (Spinner) findViewById(R.id.signup_usertype);
        signup_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_cancelIntent = new Intent (RegisterActivity.this, WelcomeActivity.class);
                RegisterActivity.this.startActivity(signup_cancelIntent);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.UserType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_typeSpinner.setAdapter(adapter);

        Button signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String rusername = rUserView.getText().toString();
                String rpassword = rPasswordView.getText().toString();
                String rusertype = (String) rUserType.getSelectedItem();
                String remail = rEmail.getText().toString();

                User u = new User();
                u.setUsername(rusername);
                u.setEmail(remail);
                u.setPassword(rpassword);
                u.setUsertype(rusertype);
                boolean check = helper.CheckIfDataExists(rusername);
                if (check){
                    AlertDialog.Builder debuilder = new AlertDialog.Builder(RegisterActivity.this);
                    debuilder.setMessage("This username already exists")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else{
                    long success = helper.insertUser(u);
                    if (success != -1){
                        Intent rsintent = new Intent (RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(rsintent);
                        finish();
                    }
                    else{
                        AlertDialog.Builder rfbuilder = new AlertDialog.Builder(RegisterActivity.this);
                        rfbuilder.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                }


            }
        });
    }
}
