package car.com.cartique.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Order;
import car.com.cartique.client.model.OrderStatus;
import car.com.cartique.client.utility.NotificationGenerator;

public class OrderStatusActivity extends AppCompatActivity {
private TextView txtStatusClientName;
private TextView txtStatusOrderNumber;
private DatabaseReference databaseReference;
private OrderStatus status;
private ProgressBar initiatedHorizontalProgressbar;
private ProgressBar preparationHorizontalProgressbar;
private ProgressBar inprogressHorizontalProgressbar;
private ProgressBar finishedHorizontalProgressbar;
private ProgressBar collectionHorizontalProgressbar;
private ProgressBar collectedHorizontalProgressbar;

private ProgressBar initiatedCurcularProgressbar;
private ProgressBar preparationCurcularProgressbar;
private ProgressBar inprogressCurcularProgressbar;
private ProgressBar finishedCurcularProgressbar;
private ProgressBar collectionCurcularProgressbar;
private ProgressBar collectedCurcularProgressbar;

private AppCompatImageView initialStatusActive;
private AppCompatImageView inprogressStatusActive;
private AppCompatImageView preparationStatusActive;
private AppCompatImageView finishedStatusActive;
private AppCompatImageView collectionStatusActive;
private AppCompatImageView collectedStatusActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status_activity);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txtStatusClientName = findViewById(R.id.txtstatusClientName);
        txtStatusOrderNumber = findViewById(R.id.txtstatusOrderNumber);
        Intent i = getIntent();
        final Order order = (Order)i.getSerializableExtra("Order");
        txtStatusOrderNumber.setText(order.getOrderNumber());
        txtStatusClientName.setText(order.getClientName());
        OrderStatus orderStatus = order.getOrderStatus();

        initiatedCurcularProgressbar = findViewById(R.id.initiatedStatusCurcularProgressbar);
        preparationHorizontalProgressbar = findViewById(R.id.preparationStatusHorizontalProgressbar);
        inprogressHorizontalProgressbar = findViewById(R.id.inprogressHorizontalStatusProgressbar);
        finishedHorizontalProgressbar = findViewById(R.id.finishedHorizontalStatusProgressbar);
        collectionHorizontalProgressbar = findViewById(R.id.collectionHorizontalStatusProgressbar);
        collectedHorizontalProgressbar = findViewById(R.id.collectedHorizontalStatusProgressbar);

        preparationCurcularProgressbar = findViewById(R.id.preparationCurcularStatusProgressbar);
        inprogressCurcularProgressbar = findViewById(R.id.inprogressCurcularStatusProgressbar);
        finishedCurcularProgressbar = findViewById(R.id.finishedCicularStatusProgressbar);
        collectionCurcularProgressbar = findViewById(R.id.collectionCircularStatusProgressbar);
        collectedCurcularProgressbar = findViewById(R.id.collectedCircularStatusProgressbar);

        initialStatusActive = findViewById(R.id.initialStatusActive);
        preparationStatusActive = findViewById(R.id.preparationStatusActive);
        inprogressStatusActive = findViewById(R.id.inprogresstatusActive);
        finishedStatusActive = findViewById(R.id.finishedStatusActive);
        collectionStatusActive = findViewById(R.id.collectionStatusActive);
        collectedStatusActive = findViewById(R.id.collectedStatusActive);

        switch(orderStatus){
            case INITIATED:
                preparationHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                break;
            case PREPARATION:
                inprogressHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                break;
            case INPROGRESS:
                finishedHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                break;
            case FINISHED:
                collectionHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                finishedHorizontalProgressbar.setProgress(100);
                break;
            case COLLECTION:
                collectedHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                collectionStatusActive.setVisibility(View.VISIBLE);
                break;
            case COLLECTED:
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                collectionStatusActive.setVisibility(View.VISIBLE);
                collectedStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                finishedHorizontalProgressbar.setProgress(100);
                collectionHorizontalProgressbar.setProgress(100);
                break;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Snackbar.make(view, "Clicked " + parent.getItemAtPosition(position).toString(), Snackbar.LENGTH_LONG).show();
               status = OrderStatus.valueOf(parent.getItemAtPosition(position).toString().toUpperCase());
           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        AppCompatButton btn_setStatus = findViewById(R.id.btn_setStatus);
        btn_setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (status != null){

                    if (status != order.getOrderStatus()){
                        order.setOrderStatus(status);
                        databaseReference.child("Orders").child(order.getOrderID()).child("orderStatus")
                                .setValue(order.getOrderStatus().name()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(v, "New Status has been set to "+order.getOrderStatus().name(), Snackbar.LENGTH_LONG).show();
                                databaseReference.child(Config.LEGACY_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        NotificationGenerator notificationGenerator = new NotificationGenerator();
                                        String message =  notificationGenerator.getFCMNotificationMessage(order,"Cartique",Config.STATUS_CHANGE_MESSAGE);
                                        String key = (String)dataSnapshot.getValue();
                                        notificationGenerator.sendMessageToFcm(message,key);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    } else {
                        Snackbar.make(v, "Cannot set the same Status", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(v, "Please select a Status first", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
