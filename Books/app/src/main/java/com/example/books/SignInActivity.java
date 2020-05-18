package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private EditText mEmail_editText,mPassword_editText;
    private Button mBack_btn,mSignIn_btn,mRegister_btn;
    private ProgressBar mProgress_bar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth=FirebaseAuth.getInstance();
        mEmail_editText=(EditText)findViewById(R.id.email_editText);
        mPassword_editText=(EditText)findViewById(R.id.password_editText);
        mSignIn_btn=(Button)findViewById(R.id.signin_btn);
        mBack_btn=(Button)findViewById(R.id.back_btn);
        mRegister_btn=(Button)findViewById(R.id.register_btn);
        mProgress_bar=(ProgressBar)findViewById((R.id.lading_progressBar));

        mSignIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) return;
                inProgress(true);
                mAuth.signInWithEmailAndPassword(mEmail_editText.getText().toString(),mPassword_editText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this,"User signed in!"
                                        ,Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SignInActivity.this,BookDetailsActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();  return;

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignInActivity.this,"Sign in failed!"+e.getMessage()
                                ,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) return;
                inProgress(true);
                mAuth.createUserWithEmailAndPassword(mEmail_editText.getText().toString(),mPassword_editText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this,"User registered successfully!"
                                        ,Toast.LENGTH_LONG).show();
                                inProgress(false);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignInActivity.this,"Registration failed!"+e.getMessage()
                                ,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });

    }
    private void inProgress(boolean x)
    {
        if(x)
        {
            mProgress_bar.setVisibility(View.VISIBLE);
            mBack_btn.setEnabled(false);
            mSignIn_btn.setEnabled(false);
            mRegister_btn.setEnabled(false);

        }
        else
        {
            mProgress_bar.setVisibility(View.GONE);
            mBack_btn.setEnabled(true);
            mSignIn_btn.setEnabled(true);
            mRegister_btn.setEnabled(true);
        }
    }
    private boolean isEmpty()
    {
        if(TextUtils.isEmpty(mEmail_editText.getText().toString()))
        {
            mEmail_editText.setError("REQUIRED");
            return true;
        }
        if(TextUtils.isEmpty(mPassword_editText.getText().toString()))
        {
            mPassword_editText.setError("REQUIRED");
            return true;
        }
        return false;
    }
}
