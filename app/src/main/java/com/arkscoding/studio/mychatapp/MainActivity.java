package com.arkscoding.studio.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
//useer app || email app@gmail.com ||Password appappapp123
       EditText myEmail;
       EditText myPasswprd ;
    FirebaseAuth myauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEmail=(EditText)findViewById(R.id.email);
        myPasswprd=(EditText)findViewById(R.id.password);
        myauth=FirebaseAuth.getInstance();

    }

    private  void userEmailverification(){
        String email = myEmail.getText().toString();
        String password = myPasswprd.getText().toString();
        if (email.isEmpty()|| password.isEmpty())
             {
            Toast.makeText(this,"Enter Email and password",Toast.LENGTH_LONG).show();
            return;

        }
        else{
Log.i("msg:  ","inside addOnCompleteListener.............");
myauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Log.i("Success","was user loged in "+task.isSuccessful());

        if(task.isSuccessful()) {
            Intent intent=new Intent(MainActivity.this,mainChatActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            String s=task.getException().toString(); Log.i(s,"msg");
            s=s.replace("com.google.firebase.auth.FirebaseAuthUserCollisionException:","Sorry,");

            errorMsg(s);
        }
    }
});

}
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

        String con_pass=myPasswprd.getText().toString();
        if ((password.length()>6)&&(password.equals(con_pass))){
            return true;
        }
        else {

            return false;
        }
    }
    public void loginApp(View view){
        Log.i("message","inside signin");
 userEmailverification();
    }
    public void newUser(View view){
        Intent intent=new Intent(MainActivity.this,RejisterActivity.class);
        finish();
        startActivity(intent);
    }
    //Aleart dialog
    private void errorMsg(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("OOPS.........")
                .setMessage(msg)
                .setPositiveButton("ok", null)
                .setIcon(android.R.drawable.ic_lock_lock)
                .show();
    }}

