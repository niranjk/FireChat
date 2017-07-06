package com.khatri.niranjank.firechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signin;
    private EditText email;
    private EditText passwd;
    private TextView signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        signin = (Button) findViewById(R.id.login_signIn);
        email = (EditText) findViewById(R.id.login_email);
        passwd = (EditText) findViewById(R.id.login_pass);
        signup = (TextView) findViewById(R.id.login_singnUp);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == signin){
            userLogin();
        }

        if(v == signup){
            // will open the signup activity here
        }

    }

    private void userLogin() {
        String useremail = email.getText().toString().trim();
        String userpasswd = passwd.getText().toString().trim();

        if(TextUtils.isEmpty(useremail)){
            // email is empty
            Toast.makeText(this,"Enter Email Address",Toast.LENGTH_LONG).show();
            // stopping the function
            return;
        }

        if(TextUtils.isEmpty(userpasswd)){
            // password is empty
            Toast.makeText(this,"Enter Email Address",Toast.LENGTH_LONG).show();
            // stopping the function
            return;
        }

        // if validation are ok we will first show the progressbar
        progressDialog.setMessage("Logging Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(useremail,userpasswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            // here we are inside the listener so we cannot pass .this
                            // we have to use getApplicationContext
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });

    }
}
