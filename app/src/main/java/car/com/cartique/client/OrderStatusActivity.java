package car.com.cartique.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.transferwise.sequencelayout.SequenceStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import car.com.cartique.client.custom.CustomSpinner;
import car.com.cartique.client.model.Order;
import car.com.cartique.client.model.OrderStatus;

import static car.com.cartique.client.model.OrderStatus.INITIATED;

public class OrderStatusActivity extends AppCompatActivity {
private TextView txtStatusClientName;
private TextView txtStatusOrderNumber;
private SequenceStep statusDoneSequenceStep;
    private SequenceStep statusInitialSequenceStep;
    private SequenceStep statusProgressSequenceStep;
    private SequenceStep statusPreparingSequenceStep;
private DatabaseReference databaseReference;
private OrderStatus status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status_activity);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_order_status);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txtStatusClientName = findViewById(R.id.txtstatusClientName);
        txtStatusOrderNumber = findViewById(R.id.txtstatusOrderNumber);
        statusDoneSequenceStep = findViewById(R.id.statusDoneSequenceStep);
        statusInitialSequenceStep = findViewById(R.id.statusInitialSequenceStep);
        statusPreparingSequenceStep = findViewById(R.id.statusPreparationSequenceStep);
        statusProgressSequenceStep = findViewById(R.id.statusInProgressSequenceStep);
        Intent i = getIntent();
        final Order order = (Order)i.getSerializableExtra("Order");
        txtStatusOrderNumber.setText(order.getOrderNumber());
        txtStatusClientName.setText(order.getClientName());
        OrderStatus orderStatus = order.getOrderStatus();
        switch(orderStatus){
            case INITIATED:
                statusInitialSequenceStep.setActive(true);
                statusInitialSequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                break;
            case INPROGRESS:
                statusProgressSequenceStep.setActive(true);
                statusProgressSequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                break;
            case PREPARATION:
                statusPreparingSequenceStep.setActive(true);
                statusPreparingSequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                break;
            case DONE:
                statusDoneSequenceStep.setActive(true);
                statusDoneSequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                break;
        }

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        List<String> statusList = new ArrayList<>();
        for (OrderStatus status:OrderStatus.values()){
            statusList.add(status.name());
        }
        spinner.setItems(statusList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                status = OrderStatus.valueOf(item);
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
