package car.com.cartique.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;

public class UserProfileFullActivity extends AppCompatActivity {
    private AppCompatImageView profilePic;
    private TextView txtUsername;
    private TextView txtLocation;
    private AppCompatTextView txtProfilebio;
    private AppCompatTextView txtEmail;
    private AppCompatTextView txtCellNumber;
    private AppCompatTextView txtAddress;
    private AppCompatTextView txtWebsite;
    private AppCompatTextView txtservice;
    private AppCompatTextView txtrepair;
    private AppCompatTextView txtpaint;
    private AppCompatTextView txtguaratee;
    private AppCompatTextView txtgroup;
    private  Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_full);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //client = (Client) intent.getSerializableExtra("Client");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user = preferences.getString(Config.USER_OBJECT,"");
        Gson gson = new Gson();
        client = gson.fromJson(user,Client.class);

        profilePic = findViewById(R.id.userProfileImage);
        txtProfilebio = findViewById(R.id.txtProfilebio);
       // txtUsername = findViewById(R.id.txtUserName);
        txtLocation = findViewById(R.id.txtUserLocation);
        txtEmail = findViewById(R.id.txtProfileEmail);
        txtCellNumber = findViewById(R.id.txtProfilePhone);
        txtAddress = findViewById(R.id.txtProfileAddress);
        txtWebsite = findViewById(R.id.txtProfileWebsite);
        txtservice = findViewById(R.id.txtProfileService);
        txtrepair = findViewById(R.id.txtProfileRepair);
        txtpaint = findViewById(R.id.txtProfilePaint);
        txtguaratee = findViewById(R.id.txtProfileGurantee);
        txtgroup = findViewById(R.id.txtProfileOrganisation);

       // txtUsername.setText(client.getName());
        txtLocation.setText(client.getSuburb()+","+client.getCity());
        txtEmail.setText(client.getEmail());
        txtAddress.setText(client.getAddress());
        txtCellNumber.setText(client.getCellNumber());
        txtWebsite.setText(client.getWebsite());
        txtProfilebio.setText(client.getBio());
        txtservice.setText(client.getServiceType());
        txtpaint.setText(client.getPaintType());
        txtrepair.setText(client.getRepairType());
        txtguaratee.setText(client.getGuarantee());
        txtgroup.setText(client.getGroup());
        getSupportActionBar().setTitle(client.getName());
        getSupportActionBar().setHomeButtonEnabled(true);

        Picasso.with(getApplicationContext())
                .load(client.getImageUrl())
                .fit()
                .into(profilePic);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_profile_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_client_profile:
                Intent intent = new Intent(getApplicationContext(),EditUserProfileActivity.class);
                intent.putExtra ("Client", client);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
