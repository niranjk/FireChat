package com.khatri.niranjank.firechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {


    /*
    private Button signup;
    private EditText email;
    private EditText passwd;
    private TextView login;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

*/

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main;
    FloatingActionButton fab;





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_signout){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main," You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Snackbar.make(activity_main,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }else {
                Snackbar.make(activity_main,"Not signed in please try later!", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.main_input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });

        // check if not sign-in then navigate signin page
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else {
            Snackbar.make(activity_main,"Welcome" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
        }

        // load content
        displayChatMessage();

        /*
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        signup = (Button) findViewById(R.id.main_register);
        email = (EditText) findViewById(R.id.main_email);
        passwd = (EditText) findViewById(R.id.main_pass);
        login = (TextView) findViewById(R.id.main_loginnow);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);

        */

    }

    private void displayChatMessage() {

        ListView listOfMessage = (ListView) findViewById(R.id.main_listview);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) findViewById(R.id.listitem_messagetext);
                messageUser = (TextView) findViewById(R.id.listitem_messageuser);
                messageTime = (TextView) findViewById(R.id.listitem_messagetime);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyy (HH:mm:ss)", model.getMessageTime()));

            }
        };

        listOfMessage.setAdapter(adapter);
    }



    /*
    @Override
    public void onClick(View v) {

        if(v == signup){
            registerUser();
        }

        if(v == login){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

    }

    private void registerUser() {
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
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(useremail,userpasswd).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // user is successfully registered and logged in
                    // we will start the profile activity here
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {

                    Toast.makeText(MainActivity.this, " Couldn't Register ", Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            }
        });

    }
    */
}
