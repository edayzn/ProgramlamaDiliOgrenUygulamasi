package com.example.programlamadiliogren;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.app.Dialog;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    EditText userEmail, userPassword;
    Button btnSignin, btnSignup, btnForgotPassword;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.userPassword);

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

    }
    public void current(){
        if(user !=null){
            System.out.println(user.getEmail());
            System.out.println("sdsdsd");}
        else
            System.out.println("kullanıcı yok");
    }
    public void Signin() {
        String inputEmail = userEmail.getText().toString().trim();
        String inputPassword = userPassword.getText().toString().trim();
        if (TextUtils.isEmpty(inputEmail)) {
            Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(inputPassword)) {
            Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz!", Toast.LENGTH_LONG).show();
        } else if (inputPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Parola en az 6 haneli olmalıdır!", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (userEmail.getText().toString().equals("adminmail") & userPassword.getText().toString().equals("sifre")) {
                        startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
                    } else if (task.isSuccessful()) {
                        current();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public void Signup() {
        String inputEmail = userEmail.getText().toString().trim();
        String inputPassword = userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(inputEmail)) {
            Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(inputPassword)) {
            Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz!", Toast.LENGTH_SHORT).show();
        } else if (inputPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Parola en az 6 haneli olmalıdır", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            //Create user
            auth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void forgotPassword() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_email_dialog);
        dialog.setTitle("Reset Password");
        dialog.setCancelable(true);

        final EditText userEmail = (EditText) dialog.findViewById(R.id.newEmail);

        Button btnReset = (Button) dialog.findViewById(R.id.btnReset);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = userEmail.getText().toString().trim();

                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter your Email", Toast.LENGTH_LONG).show();
                } else {
                    auth.sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

