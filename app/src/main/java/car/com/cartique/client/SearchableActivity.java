package car.com.cartique.client;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import car.com.cartique.client.adapter.RecordsListAdapter;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Order;

public class SearchableActivity extends AppCompatActivity {
    private RecordsListAdapter listAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout layoutNoData;
    private RelativeLayout layoutWithData;
    private List<Order> ordersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private DotLoader bar;
    private FirebaseAuth auth;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_search);
        recyclerView = findViewById(R.id.recordsList);
        listAdapter = new RecordsListAdapter(this, ordersList);
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

        databaseReference.child("Orders").orderByChild("clientName").equalTo(getUserObjInPref().getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                };
                Map<String, Order> orders = dataSnapshot.getValue(genericTypeIndicator);
                System.out.println(orders.toString());
                if (orders != null) {
                    for (String key:orders.keySet()){
                        Order newOrder = orders.get(key);
                            newOrder.setOrderID(key);
                            ordersList.add(newOrder);
                    }
                    Collections.sort(ordersList);
                    Collections.reverse(ordersList);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                listAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                listAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
