package car.com.cartique.client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.bhargavms.dotloader.DotLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import car.com.cartique.client.adapter.QuoteListAdapter;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Order;
import car.com.cartique.client.model.OrderType;

public class QuotesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuoteListAdapter listAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout layoutNoData;
    private RelativeLayout layoutWithData;
    private List<Order> ordersList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DotLoader bar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_order);
        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_quotes);
        recyclerView = findViewById(R.id.recordsList);
        listAdapter = new QuoteListAdapter(this, ordersList);
        recyclerView.setAdapter(listAdapter);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        layoutNoData = findViewById(R.id.layoutNoData);
        layoutWithData = findViewById(R.id.layoutWithData);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        bar = findViewById(R.id.prgload);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Orders").orderByChild("clientName").equalTo("Quotations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                };
                Map<String, Order> orders = dataSnapshot.getValue(genericTypeIndicator);
                if (orders != null) {
                    for (String key:orders.keySet()){
                        Order newOrder = orders.get(key);
                        newOrder.setOrderID(key);
                        ordersList.add(newOrder);
                    }
                    layoutNoData.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    bar.setVisibility(View.GONE);
                }else{
                    bar.setVisibility(View.GONE);
                    layoutWithData.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private Client getUserObjInPref() {
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userString = pref.getString(Config.USER_OBJECT,"");
        Gson gson = new Gson();
        return gson.fromJson(userString,Client.class);
    }
}

