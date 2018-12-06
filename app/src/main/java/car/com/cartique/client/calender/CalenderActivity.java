package car.com.cartique.client.calender;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import car.com.cartique.client.R;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Order;

public class CalenderActivity extends AppCompatActivity {
    CalendarView calendarView;
    private LinearLayout noEventLayout;
    private LinearLayout eventLayout;
    private TextView txtName;
    private TextView txtDate;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private List<Order> orderList;
    List<EventDay> events = new ArrayList<>();
    List<String> eventNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_calender);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        noEventLayout = findViewById(R.id.noEventLayout);
        eventLayout = findViewById(R.id.eventLayout);
        txtName = findViewById(R.id.eventName);
        txtDate = findViewById(R.id.eventDate);
        orderList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        try {
            calendarView.setDate(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Calender Events");
        progressDialog.setMessage("retrieving all your events");
        progressDialog.show();
        databaseReference.child("Orders").orderByChild("clientName").equalTo(getUserObjInPref().getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                };
                Map<String, Order> orders = dataSnapshot.getValue(genericTypeIndicator);

                boolean isExist = false;
                if (orders != null) {
                    for (String key:orders.keySet()){
                        Order newOrder = orders.get(key);
                        newOrder.setOrderID(key);
                        orderList.add(newOrder);

                        if (newOrder.getScheduledDate() != null){
                            Calendar calendarEvent = Calendar.getInstance();
                            calendarEvent.setTime(newOrder.getScheduledDate());
                            EventDay newEventDay = new EventDay(calendarEvent, R.mipmap.ic_alarm);
                            events.add(newEventDay);
                            eventNames.add(newOrder.getClientName() +" "+ newOrder.getOrderType().name());
                            isExist = true;
                        }
                    }
                    if (isExist){
                        calendarView.setEvents(events);
                        progressDialog.dismiss();
                        calendarView.setOnDayClickListener(new OnDayClickListener() {
                            @Override
                            public void onDayClick(EventDay eventDay) {
                                Calendar clickedDayCalendar = eventDay.getCalendar();
                                boolean isDateFound = false;
                                for (EventDay day:events){
                                    if (clickedDayCalendar.getTime().toString().equalsIgnoreCase(day.getCalendar().getTime().toString())){
                                        txtName.setText(eventNames.get(events.indexOf(day)));
                                        txtDate.setText(day.getCalendar().getTime().toString());
                                        noEventLayout.setVisibility(View.GONE);
                                        eventLayout.setVisibility(View.VISIBLE);
                                        isDateFound = true;
                                    }
                                }
                                if (!isDateFound){
                                    noEventLayout.setVisibility(View.VISIBLE);
                                    eventLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else {
                        progressDialog.dismiss();
                        eventLayout.setVisibility(View.GONE);
                        noEventLayout.setVisibility(View.VISIBLE);
                    }
                }else{
                    progressDialog.dismiss();
                    eventLayout.setVisibility(View.GONE);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
