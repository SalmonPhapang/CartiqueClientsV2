package car.com.cartique.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import car.com.cartique.client.app.Config;

public class ResultsActivity extends AppCompatActivity {
    AppCompatButton btnsweet;
    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_results);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        if (message == null || message.isEmpty()){
            message = Config.GENERIC_RESULT_MESSAGE;
        }
        txtMessage = findViewById(R.id.txt_result_msg);
        txtMessage.setText(message);
        btnsweet = findViewById(R.id.btnAcceptResult);
        btnsweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

}
