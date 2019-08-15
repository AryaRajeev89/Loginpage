package com.arkscoding.studio.mychatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

public class mainChatActivity extends AppCompatActivity {
Switch a;private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        myAuth = FirebaseAuth.getInstance();

        Button b=(Button)findViewById(R.id.button2);
b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Log.i("msg", "onClick: ");
        myAuth.signOut();
        Intent intent=new Intent(mainChatActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
});
    }
}
