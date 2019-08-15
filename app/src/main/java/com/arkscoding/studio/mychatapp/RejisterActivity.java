package com.arkscoding.studio.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RejisterActivity extends AppCompatActivity {
    public static final String CHAT_PREF = "Chat preft";
    public static final String CHAT_NAME = "chatname";

    //ref field
    private AutoCompleteTextView myUsernameView;
    private EditText myEmailView;
    private EditText myPasswordView;
    private EditText myConformPasswordView;


    //Firebase reference
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rej);
        myEmailView = (EditText) findViewById(R.id.email);
        myPasswordView = (EditText) findViewById(R.id.password);
        myConformPasswordView = (EditText) findViewById(R.id.conformpassword);
        myUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

        //gett hold on Firebase instance
        myAuth = FirebaseAuth.getInstance();

    }
    public void signUp(View view){

        rejistrationMethod();

    }

    private void rejistrationMethod(){
        myEmailView.setError(null);
        myPasswordView.setError(null);
        //grab values
        String email=myEmailView.getText().toString();
        String password=myPasswordView.getText().toString();
        String conformpass=myConformPasswordView.getText().toString();
        //kill switch
        boolean cansel=false;
        View focous=null;

        if(!TextUtils.isEmpty(password) && !passwordVerification(password)){
            boolean passworddd=!TextUtils.isEmpty(password);
            boolean eee=!passwordVerification(password);
            Log.i("Button","im innnnnnnnnnnnnnnnnnnnnnnnnnnnnn if password");

            myPasswordView.setError(getString(R.string.invalid_password));
            focous=myPasswordView;
            cansel=true;
        }
        if(TextUtils.isEmpty(email) && !emailVerification(email))
        {
            Log.i("Button","im innnnnnnnnnnnnnnnnnnnnnnnnnnnnn if email");
            myEmailView.setError(getString(R.string.invalid_email));
            focous=myEmailView;
            cansel=true;

        }
        if(cansel){
            focous.requestFocus();
        }else {
            Log.i("email passwod perfect","***********************");
            createUser();;
        }
    }

    private void getUsername() {
        String user_Name = myUsernameView.getText().toString();
        SharedPreferences pref = getSharedPreferences(CHAT_PREF, 0);
        pref.edit().putString(CHAT_NAME, user_Name).apply();
    }

    //Aleart dialog
    private void errorMsg(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("errorrrrrrrrrrr")
                .setMessage(msg)
                .setPositiveButton("ok", null)
                .setIcon(android.R.drawable.ic_lock_lock)
                .show();
    }


    private void createUser() {
        Log.i("inside button","im bbbbbbbbbbbbbbbbbbbbbbbbbbb ");
        String email = myEmailView.getText().toString();
        String password = myPasswordView.getText().toString();

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //************************************delete*************************************
                Log.i("IM HERE........", String.valueOf(task.isSuccessful()));
                //********************************************************************************
               if(task.isSuccessful()) {
                    Log.i("Button task done","im innnnnnnnnnnnnnnnnnnnnnnnnnnnnn ");
                   // createUser();
                    Toast.makeText(RejisterActivity.this, "welcome to chitchat...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RejisterActivity.this, mainChatActivity.class);
                    finish();
                    startActivity(intent);
                }
                else  {
                    Log.i("message","it is : "+task.getException());
                    errorMsg("OOPs plz try again");
                }
            }
        });
    }

    private boolean emailVerification(String email) {

        if (email.contains("@")) {
            return true;
        }
        else {
            //  myEmailView.setError(getString(R.string.invalid_email));
            return false;
        }}

    private boolean passwordVerification(String password)
    {

        String con_pass=myConformPasswordView.getText().toString();
        int l=password.length();
//        if (password.equals(con_pass)){
//            Log.i(password+","+con_pass,"im in passsssssssssssssssss");
//        }
        if (l>6 && password.equals(con_pass)){

            Log.i(password+","+con_pass+l,"im innnnnnnnnnnnnnnnnnnnnnnnnnnnnn true");
            return true;

        }

        else {
            Log.i(password+","+con_pass+l,"im innnnnnnnnnnnnnnnnnnnnnnnnnnnnn false");
            return false;
        }
    }
}
