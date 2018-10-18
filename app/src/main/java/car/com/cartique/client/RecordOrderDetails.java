package car.com.cartique.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import car.com.cartique.client.calender.CalenderActivity;
import car.com.cartique.client.calender.ScheduleCalenderActivity;
import car.com.cartique.client.model.Order;

public class RecordOrderDetails extends AppCompatActivity {
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
    private TextView btnSave;
    private TextView btnEdit;
    private TextView btnCancel;
    private EditText txtEditLogbook;
    private TextInputLayout txtEditLogbookLayout;
    private AppCompatButton btnScheduleDate;
    private LinearLayout logBookLayout;
    private LinearLayout saveOrEditLayout;
    private  Order order;
    private ProgressDialog progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_records_details);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        order =(Order) getIntent().getSerializableExtra("Order");
        progressBar = new ProgressDialog(this);
        txtClientName = findViewById(R.id.txtUserName);
        txtOrderNumber = findViewById(R.id.txtOrderNumber);
        txtOrderType = findViewById(R.id.txtOrderType);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderAmount = findViewById(R.id.txtOrderAmount);
        txtMake = findViewById(R.id.txtCarMake);
        txtModel = findViewById(R.id.txtCarModel);
        txtYear = findViewById(R.id.txtCarYear);
        txtColor = findViewById(R.id.txtCarColor);
        txtEditLogbook = findViewById(R.id.txtEditLogBook);
        txtEditLogbookLayout = findViewById(R.id.txtEditLogBookInput);
        txtEditLogbookLayout.setVisibility(View.GONE);

        btnScheduleDate = findViewById(R.id.btn_setDate);
        logBookLayout = findViewById(R.id.commentsLayout);
        saveOrEditLayout = findViewById(R.id.saveOrEditLayout);
        btnEdit = findViewById(R.id.btnEditLog);
        btnSave = findViewById(R.id.btnSaveLog);
        btnCancel = findViewById(R.id.btnCancelLog);

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

        if(order.getScheduledDate()== null){
            logBookLayout.setVisibility(View.GONE);
            btnScheduleDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ScheduleCalenderActivity.class);
                    intent.putExtra("order",order);
                    startActivity(intent);
                }
            });
        }else {
            TextView scheduleDate = findViewById(R.id.txt_schedule_date);
            scheduleDate.setText(getResources().getString(R.string.text_schedule_logbook) +" "+ order.getScheduledDate()+"\n");
            for (String log:order.getLogBook()){
                scheduleDate.setText(scheduleDate.getText().toString()+ log);
            }
            logBookLayout.setVisibility(View.VISIBLE);
            btnScheduleDate.setVisibility(View.GONE);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtEditLogbookLayout.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.GONE);
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String logbookText = txtEditLogbook.getText().toString();
                    order.getLogBook().add(logbookText);
                    if (!logbookText.isEmpty() || !logbookText.equalsIgnoreCase("")){
                        progressBar.setMessage("Please wait a few seconds");
                        progressBar.setTitle("Setting new Logbook Data for Service");
                        progressBar.show();
                        databaseReference.child("Orders").child(order.getOrderID()).child("logBook")
                                .setValue(order.getLogBook()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.dismiss();
                                Snackbar.make(v, "New Logbook data has been set for order "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Snackbar.make(v, "Please enter logbook text", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtEditLogbook.setText("");
                    txtEditLogbookLayout.setVisibility(View.GONE);
                    btnSave.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);
                    btnEdit.setVisibility(View.VISIBLE);
                }
            });
        }

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
