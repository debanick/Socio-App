package com.example.socio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG;

    static {
        TAG = "TAG";
    }

    EditText name,email,password;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name,Email,Password;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TAG", "onCreate: initialized registration");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activityregister);
        name = (EditText)findViewById(R.id.editName1);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);
        mRegisterbtn = (Button)findViewById(R.id.buttonRegister);
        mLoginPageBack = (TextView)findViewById(R.id.buttonLogin);
        // for authentication using FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public void onClick(View v) {
        if (v==mRegisterbtn){
            UserRegister();
        }else if (v== mLoginPageBack){
            startActivity(new Intent(com.example.socio.Register.this, com.example.socio.LoginActivity.class));
        }
    }

    private void UserRegister() {
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference nRef = database.getReference("name");
        nRef.setValue(Name);

        if (TextUtils.isEmpty(Name)){
            Toast.makeText(com.example.socio.Register.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Email)){
            Toast.makeText(com.example.socio.Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Password)){
            Toast.makeText(com.example.socio.Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }else if (Password.length()<6){
            Toast.makeText(com.example.socio.Register.this,"Password must be greater then 6 digit", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseFirestore fstore;
                fstore=FirebaseFirestore.getInstance();
                if (task.isSuccessful()){

                    Log.d("TAG", "onComplete: user details initialized ");
                    OnAuth(task.getResult().getUser());

                    Name = name.getText().toString().trim();
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", Name);
                    user.put("UID",mAuth.getCurrentUser().getUid());
                    fstore.collection("user")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(com.example.socio.Register.this,"ERegistration Initialized "+Name, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                    sendEmailVerification();
                    mDialog.dismiss();
                    OnAuth(task.getResult().getUser());

                    mAuth.signOut();
                }else{
                    Toast.makeText(com.example.socio.Register.this,"error on creating user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Email verification code using FirebaseUser object and using isSucccessful()function.
    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(com.example.socio.Register.this,"Check your Email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        com.example.socio.User user = BuildNewuser();
        mdatabase.child(uid).setValue(user);
    }


    public com.example.socio.User BuildNewuser(){
        return new com.example.socio.User(
                getDisplayName(),
                getUserEmail(),
                new Date().getTime()
        );
    }

    public String getDisplayName() {
        return Name;
    }

    public String getUserEmail() {
        return Email;
    }

}