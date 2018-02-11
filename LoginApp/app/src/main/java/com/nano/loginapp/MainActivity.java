package com.nano.loginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText emailId,passwordId;
    private Button login;

   private FirebaseAuth mauth;

   private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Firebase.setAndroidContext ( this );

        mauth =  FirebaseAuth.getInstance ();

        emailId = ( EditText ) findViewById ( R.id.EmailId );
        passwordId = ( EditText ) findViewById ( R.id.PasswordId);
        login = ( Button ) findViewById ( R.id.LoginButtonId );


        authStateListener = new FirebaseAuth.AuthStateListener () {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               // Toast.makeText ( MainActivity.this,"Nahid",Toast.LENGTH_LONG ).show ();
                Log.d ( "name","This is onAuthstatechaged method is called " );

                if(firebaseAuth.getCurrentUser () != null)
                {

                    Log.d ( "name","This is if method is called " );
                    startActivity ( new Intent ( MainActivity.this,AccountActivity.class ) );

                }
            }
        };


        login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Startsingnin();

            }
        } );


    }

    @Override
    protected void onStart() {
        super.onStart ();
        mauth.addAuthStateListener ( authStateListener );
        

    }

    public void Startsingnin()
    {
        String Email = emailId.getText ().toString ();
        String Password = passwordId.getText ().toString ();

        if(TextUtils.isEmpty ( Email ) || TextUtils.isEmpty ( Password ))
        {

            Toast.makeText ( MainActivity.this,"Please write email or password",Toast.LENGTH_LONG ).show ();
        }

        else {

            mauth.signInWithEmailAndPassword ( Email,Password ).addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful ())
                    {

                        Toast.makeText ( MainActivity.this,"Email or password is wrong",Toast.LENGTH_LONG ).show ();
                    }

                }
            } );
        }


    }
}
