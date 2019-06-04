package car.com.cartique.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.Map;

import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
     /*   btnSignup = findViewById(R.id.btn_signup);*/
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);
/*
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });*/

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (email.isEmpty()) {
                    inputEmail.setError("Enter email address");
                } else if (password.isEmpty()) {
                    inputPassword.setError("Enter password!");
                } else {

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            inputPassword.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        databaseReference = FirebaseDatabase.getInstance().getReference();

                                        databaseReference.child("Clients").orderByChild("userID").equalTo(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                GenericTypeIndicator<Map<String, Client>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Client>>() {
                                                };
                                                final Map<String, Client> user = dataSnapshot.getValue(genericTypeIndicator);
                                                Client logInUser = null;
                                                for (Client client:user.values()) {
                                                    logInUser = client;
                                                }
                                                String uniqueKey = null;
                                                for (String key : user.keySet()){
                                                    uniqueKey = key;
                                                }
                                                logInUser.setUniqueID(uniqueKey);
                                                Gson gson = new Gson();
                                                String userString = gson.toJson(logInUser);
                                                storeUserObjInPref(userString);
                                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                                        String newToken = instanceIdResult.getToken();
                                                        token = newToken;
                                                    }
                                                });
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                if (token == null || token.equalsIgnoreCase(" "))
                                                    token =  preferences.getString(Config.NOTIFICATION_TOKEN," ");

                                                final String topic = logInUser.getTopics().get(0);
                                                databaseReference.child(dataSnapshot.getKey()).child(uniqueKey).child("NotificationID").setValue(token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        progressBar.setVisibility(View.GONE);
                                                        if (topic.equalsIgnoreCase("Paint")) {
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Paint")
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (!task.isSuccessful()) {
                                                                                String msg = "Unable to subscribe to paint topic";
                                                                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }
                                                                    });
                                                        }
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });

                                    }
                                }
                            });
                }
            }
        });

    }
    private void storeUserObjInPref(String user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Config.USER_OBJECT,user);
        editor.apply();
        System.out.println(".........User SAved "+user);
    }
}
