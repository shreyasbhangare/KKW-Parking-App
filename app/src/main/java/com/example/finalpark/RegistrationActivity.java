package com.example.finalpark;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    private EditText userName,userPassword,userEmail,userPrn;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private RadioButton Malebtn,Femalebtn;
    DatabaseReference databaseReference;
    private String Gender="";
    private TextView GenderBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        databaseReference=FirebaseDatabase.getInstance().getReference("People");

        firebaseAuth=FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    final String user_email=userEmail.getText().toString().trim();
                    final String user_password=userPassword.getText().toString().trim();
                    final String user_name=userName.getText().toString();
                    final String user_prn=userPrn.getText().toString();
                    final String user_parked="";
                    Gender="";
                    if(Malebtn.isChecked())
                    {
                        Gender="Male";
                    }
                    if(Femalebtn.isChecked())
                    {
                        Gender="Female";
                    }
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                People people=new People(user_name,user_prn,user_email,Gender,user_password,user_parked);
                                FirebaseDatabase.getInstance().getReference("People")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).
                                                getUid())
                                        .setValue(people).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

            }
        });
    }

    private void setupUIViews()
    {
        userName=(EditText)findViewById(R.id.etUserName);
        userPrn=(EditText)findViewById(R.id.etPrn);
        userPassword=(EditText)findViewById(R.id.etUserPassword);
        userEmail=(EditText)findViewById(R.id.etUserEmail);
        regButton=(Button)findViewById(R.id.btnRegister);
        userLogin=(TextView)findViewById(R.id.tvUserLogin);
        Malebtn=(RadioButton)findViewById(R.id.btnMale);
        Femalebtn=(RadioButton)findViewById(R.id.btnFemale);
        GenderBox=(TextView)findViewById(R.id.tvGender);

    }

    private Boolean validate()
    {
        boolean result=false;
        String name=userName.getText().toString();
        String password=userPassword.getText().toString();
        String email=userEmail.getText().toString();
        String crn=userPrn.getText().toString();
        if(name.isEmpty()||password.isEmpty()||email.isEmpty()||crn.isEmpty())
        {
            Toast.makeText(this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();

        }
        else if(name.isEmpty()&&password.isEmpty()&&email.isEmpty()&&(!(Malebtn.isChecked())||(
                !Femalebtn.isChecked())))
        {
            Toast.makeText(this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();

        }
        else if (password.length()<6)
        {
            userPassword.setError("Password must be atleast 6 characters long.");
        }
        else if(!(Malebtn.isChecked())&&!(Femalebtn.isChecked()))
        {
            GenderBox.setError("Select one of the following");
        }
        else
        {
            result=true;
        }
        return result;
    }
}
