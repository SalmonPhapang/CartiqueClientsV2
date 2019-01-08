package car.com.cartique.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

import car.com.cartique.client.adapter.QuoteDetailsAdapter;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.images.ViewImagesActivity;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Order;
import car.com.cartique.client.model.Quote;
import car.com.cartique.client.model.QuoteStatus;
import car.com.cartique.client.utility.NotificationGenerator;

public class QuoteDetailsActivity extends AppCompatActivity {
    private TextView txtClientName;
    private TextView txtOrderType;
    private TextView txtOrderStatus;
    private TextView txtOrderDate;
    private TextView txtOrderNumber;
    private TextView txtOrderAmount;
    private TextView txtMake;
    private TextView txtModel;
    private TextView txtYear;
    private TextView txtColor;
    private TextView txtQuotedAmount;
    private RecyclerView quoteRecyclerView;
    private QuoteDetailsAdapter quoteDetailsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout quotesLayout;
    private LinearLayout txtQuotedAmountLayout;
    private AppCompatButton btnViewImages;
    private AppCompatButton btnSetQuote;
    private ProgressDialog progressBar;
    private DatabaseReference databaseReference;
    private Order order;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_details);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_quotes_details);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = new ProgressDialog(this);
        order =(Order) getIntent().getSerializableExtra("Order");
        txtClientName = findViewById(R.id.txtUserName);
        txtOrderNumber = findViewById(R.id.txtOrderNumber);
        txtOrderType = findViewById(R.id.txtOrderType);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderAmount = findViewById(R.id.txtOrderAmount);
        txtQuotedAmount = findViewById(R.id.txtQuotedAmount);
        txtMake = findViewById(R.id.txtCarMake);
        txtModel = findViewById(R.id.txtCarModel);
        txtYear = findViewById(R.id.txtCarYear);
        txtColor = findViewById(R.id.txtCarColor);
        btnViewImages = findViewById(R.id.btnViewImages);
        btnViewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewImagesActivity.class);
                intent.putStringArrayListExtra("images",order.getUploadedImages());
                startActivity(intent);
            }
        });

        btnSetQuote = findViewById(R.id.btnSetQuote);
        btnSetQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String quotedAmount = txtQuotedAmount.getText().toString();
                if (!quotedAmount.isEmpty()){
                    Quote quote = new Quote();
                    quote.setAmount(quotedAmount);
                    quote.setClientName(getUserObjInPref().getName());
                    quote.setQuoteDate(new Date());
                    quote.setLocation(getUserObjInPref().getCity());
                    quote.setQuoteStatus(QuoteStatus.QUOTED);
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(QuoteDetailsActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            token = instanceIdResult.getToken();
                        }
                    });
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (token == null || token.equalsIgnoreCase(" "))
                        token =  preferences.getString(Config.NOTIFICATION_TOKEN," ");

                    quote.setClientNotifictionToken(token);
                    ArrayList<Quote> quotes = new ArrayList<>();
                    quotes.add(quote);
                    order.setQuotes(quotes);
                    progressBar.setMessage("Please wait a few seconds");
                    progressBar.setTitle("Sending Quoted amount for Paint job");
                    progressBar.show();
                    databaseReference.child("Orders").child(order.getOrderID()).child("quotes")
                            .setValue(order.getQuotes()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBar.dismiss();
                            sendNotificationMessage(order);
                            Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
                            intent.putExtra("message", Config.Quote_RESULT_MESSAGE);
                            startActivity(intent);
                            finish();
                            Snackbar.make(v, "New Quote data has been set for order "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Snackbar.make(v, "Please enter Quote amount first "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        txtClientName.setText(order.getClientName());
        txtOrderNumber.setText(order.getOrderNumber());
        txtOrderType.setText(order.getOrderType().toString());
        txtOrderDate.setText(order.getOrderDate().toString());
        txtOrderStatus.setText(order.getOrderStatus().toString());
        if (order.getAmount()==null || order.getAmount().isEmpty()){
            txtOrderAmount.setText("Not Quoted");
        } else{
            txtOrderAmount.setText(order.getAmount());
        }
        txtModel.setText(order.getCar().getModel());
        txtMake.setText(order.getCar().getMake());
        txtYear.setText(order.getCar().getYear());
        txtColor.setText(order.getCar().getColor());
        quoteRecyclerView = findViewById(R.id.quoteList);
        quoteDetailsAdapter = new QuoteDetailsAdapter(this,order.getQuotes());
        quoteRecyclerView.setAdapter(quoteDetailsAdapter);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        quoteRecyclerView.setLayoutManager(linearLayoutManager);
        quoteRecyclerView.setHasFixedSize(true);
        quoteRecyclerView.setItemViewCacheSize(20);
        quoteRecyclerView.setDrawingCacheEnabled(true);
        quotesLayout = findViewById(R.id.qoutesLayout);
        txtQuotedAmountLayout = findViewById(R.id.setQuoteLayout);

        boolean isQuoted = false;
        for (Quote quote:order.getQuotes()){
            if(quote.getClientName().equalsIgnoreCase(getUserObjInPref().getName())){
                isQuoted = true;
                break;
            }
        }
        if(!isQuoted){
            quotesLayout.setVisibility(View.GONE);
            txtQuotedAmountLayout.setVisibility(View.VISIBLE);
        }else {
            quotesLayout.setVisibility(View.VISIBLE);
            txtQuotedAmountLayout.setVisibility(View.GONE);
        }
        if(order.getQuotes() == null || order.getQuotes().size()==0){
            TextView txtHide = findViewById(R.id.lblNodata);
            txtHide.setVisibility(View.VISIBLE);
        }else
            quoteDetailsAdapter.notifyDataSetChanged();
    }
    private Client getUserObjInPref() {
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userString = pref.getString(Config.USER_OBJECT,"");
        Gson gson = new Gson();
        return gson.fromJson(userString,Client.class);
    }
    public void sendNotificationMessage(final Order order){
        databaseReference.child(Config.LEGACY_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String legacyKey = (String)dataSnapshot.getValue();
                NotificationGenerator notificationGenerator = new NotificationGenerator();
                String message = notificationGenerator.getFCMNotificationMessage(order,"Cartique",Config.QUOTATION_SET_MESSAGE);
                notificationGenerator.sendMessageToFcm(message,legacyKey);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_details:
                Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
                intent.putExtra("Order", order);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
