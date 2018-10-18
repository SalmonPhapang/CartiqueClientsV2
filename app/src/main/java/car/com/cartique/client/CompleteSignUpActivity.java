package car.com.cartique.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.schibstedspain.leku.LocationPickerActivity;

import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.User;

public class CompleteSignUpActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtSurname;
    private EditText txtAddress;
    private EditText txtUserPhone;
    AppCompatButton btnNext;
    public static final int MAP_BUTTON_REQUEST_CODE =208;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_registration_activity);
        btnNext = (AppCompatButton) findViewById(R.id.btnNext);
        txtName = (EditText) findViewById(R.id.userName);
        txtSurname = (EditText) findViewById(R.id.userSurname);
        txtUserPhone = (EditText) findViewById(R.id.userPhone);
        txtAddress = (EditText) findViewById(R.id.userAddress);

        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationPickerIntent = new LocationPickerActivity.Builder()
                        .withLocation(41.4036299, 2.1743558)
                        .withGeolocApiKey(getResources().getString(R.string.google_api_key))
                        .withSearchZone("es_ES")
                        .shouldReturnOkOnBackPressed()
                        .withStreetHidden()
                        .withCityHidden()
                        .withZipCodeHidden()
                        .withSatelliteViewHidden()
                        .withGooglePlacesEnabled()
                        .withGoogleTimeZoneEnabled()
                        .withVoiceSearchHidden()
                        .build(getApplicationContext());

                startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtName.getText().toString().isEmpty()) {
                        txtName.setError("Enter Your Name");
                    } else if (txtSurname.getText().toString().isEmpty()) {
                        txtSurname.setError("Enter Your Surname");
                    } else if (txtUserPhone.getText().toString().isEmpty()) {
                        txtUserPhone.setError("Enter Your Contact Number");
                    } else {
                        User user = new User(txtName.getText().toString(), txtSurname.getText().toString(), txtAddress.getText().toString(), txtUserPhone.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(Config.SIGNUP_USER, user);
                        startActivity(intent);
                    }
                }
            });
        }

    }
