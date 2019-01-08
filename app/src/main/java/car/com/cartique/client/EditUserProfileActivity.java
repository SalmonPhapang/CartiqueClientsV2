package car.com.cartique.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;

public class EditUserProfileActivity extends AppCompatActivity {

    private AppCompatButton btnSave;
    private AppCompatImageView profilePic;
    private EditText txtEditBio;
    private EditText txtEditCellNumber;
    private EditText txtEditWebsite;
    private EditText txtEditAddress;
    private EditText txtEditGuarantee;
    private EditText txtEditCity;
    private EditText txtEditSuburb;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressDialog progressDialog = new ProgressDialog(EditUserProfileActivity.this);
        progressDialog.setTitle("Profile Update");
        progressDialog.setMessage("Saving your Profile");
        profilePic = findViewById(R.id.userProfileImage);
        txtEditBio = findViewById(R.id.txtEditBio);
        txtEditCellNumber = findViewById(R.id.txtEditCellNumer);
        txtEditAddress = findViewById(R.id.txtEditAddress);
        txtEditCity = findViewById(R.id.txtEditCity);
        txtEditSuburb = findViewById(R.id.txtEditSuburb);
        txtEditWebsite = findViewById(R.id.txtEditwebsite);
        txtEditGuarantee = findViewById(R.id.txtEditGuarantee);

        final Intent intent = getIntent();
        final Client client =(Client) intent.getSerializableExtra("Client");
        Picasso.with(getApplicationContext())
                .load(client.getImageUrl())
                .fit()
                .into(profilePic);
        txtEditBio.setText(client.getBio());
        txtEditCellNumber.setText(client.getCellNumber());
        txtEditWebsite.setText(client.getWebsite());
        txtEditAddress.setText(client.getAddress());
        txtEditCity.setText(client.getCity());
        txtEditSuburb.setText(client.getSuburb());
        txtEditGuarantee.setText(client.getGuarantee());

        btnSave = findViewById(R.id.btn_edit_profile_sav);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                client.setBio(txtEditBio.getText().toString());
                client.setCellNumber(txtEditCellNumber.getText().toString());
                client.setWebsite(txtEditWebsite.getText().toString());
                client.setAddress(txtEditAddress.getText().toString());
                client.setCity(txtEditCity.getText().toString());
                client.setSuburb(txtEditSuburb.getText().toString());
                client.setGuarantee(txtEditGuarantee.getText().toString());
                Gson gson = new Gson();
                String userString = gson.toJson(client);
                storeUserObjInPref(userString);
                databaseReference.child("Clients").child(client.getUniqueID()).setValue(client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Intent resultIntent = new Intent(getApplicationContext(),ResultsActivity.class);
                        resultIntent.putExtra("message",Config.PROFILE_UPDATE_RESULT_MESSAGE);
                        startActivity(resultIntent);
                        finish();
                    }
                });
            }
        });
    }
    private void storeUserObjInPref(String user) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Config.USER_OBJECT,user);
        editor.apply();
    }

}
