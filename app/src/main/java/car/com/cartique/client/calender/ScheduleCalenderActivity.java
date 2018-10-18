package car.com.cartique.client.calender;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.AppCompatButton;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.ProgressBar;

        import com.applandeo.materialcalendarview.CalendarView;
        import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

        import car.com.cartique.client.R;
        import car.com.cartique.client.model.Order;
        import car.com.cartique.client.utility.NotificationGenerator;

public class ScheduleCalenderActivity extends AppCompatActivity {
    CalendarView calendarView;
    private AppCompatButton btnSetDates;
    private DatabaseReference databaseReference;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_calender_view);

        progressBar = new ProgressDialog(this);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_calender);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        calendarView = (CalendarView) findViewById(R.id.scheduleCalendarView);
        btnSetDates = findViewById(R.id.btn_setSchedualDate);
        btnSetDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                List<Calendar> selectedDates = calendarView.getSelectedDates();
                if (selectedDates!=null){
                    progressBar.setMessage("Please wait a few seconds");
                    progressBar.setTitle("Setting new Dates For Service");
                    progressBar.show();
                    Intent orderIntent = getIntent();
                   final Order order = (Order)orderIntent.getSerializableExtra("order");
                    for (Calendar calendar1:selectedDates){
                        order.getAvailableDates().add(calendar1.getTime());
                    }

                    databaseReference.child("Orders").child(order.getOrderID()).child("availableDates")
                            .setValue(order.getAvailableDates()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sendNotificationMessage(order);
                            progressBar.dismiss();
                            Snackbar.make(v, "New Dates has been set for order "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();

                        }
                    });
                } else {
                    Snackbar.make(v, "No Dates Selected", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        try {
            calendarView.setDate(calendar);

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }
    public void sendNotificationMessage(final Order order){
        databaseReference.child("LegacyKey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String legacyKey = (String)dataSnapshot.getValue();
                NotificationGenerator notificationGenerator = new NotificationGenerator();
                String message = notificationGenerator.getFCMNotificationMessage(order,"Cartique","Service Date Schedule");
                notificationGenerator.sendMessageToFcm(legacyKey,message);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
