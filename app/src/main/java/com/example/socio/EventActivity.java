package com.example.socio;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class EventActivity extends AppCompatActivity {
    EditText a,b,c,d,e,f,g;
    Button btn;
    DatabaseReference reff;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_addevent);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_add);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext()
                                , com.example.socio.HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_add:
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        a = (EditText) findViewById(R.id.editTextTextPersonName3);   //event name
        b = (EditText) findViewById(R.id.editTextTextMultiLine2);    //details
        c = (EditText) findViewById(R.id.editTextTextPersonName4);   //organizer
        d = (EditText) findViewById(R.id.editTextDate);              //date
        e = (EditText) findViewById(R.id.editTextTime);              //time
        f = (EditText) findViewById(R.id.editTextTextPersonName);    //location
        g = (EditText) findViewById(R.id.phonenumber);    //phonenum
        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference dbproducts = db.collection("events");
                events ev=new events(a.getText().toString(),b.getText().toString(),c.getText().toString(),d.getText().toString(),e.getText().toString(),f.getText().toString(),g.getText().toString());
                dbproducts.add(ev).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(com.example.socio.EventActivity.this,"Event Registration successful",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.socio.EventActivity.this,"Event Registration failed",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}
