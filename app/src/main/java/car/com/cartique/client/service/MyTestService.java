package car.com.cartique.client.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import car.com.cartique.client.MainActivity;
import car.com.cartique.client.R;
import car.com.cartique.client.app.Config;
import car.com.cartique.client.calender.CalenderActivity;
import car.com.cartique.client.model.Client;
import car.com.cartique.client.model.Order;
import car.com.cartique.client.model.OrderStatus;
import car.com.cartique.client.utility.NotificationUtils;

public class MyTestService extends IntentService {
    private DatabaseReference databaseReference;
    public MyTestService() {
        super("MyTestService");
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        System.out.println("MyTestService Service running");
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                databaseReference.child("Orders").orderByChild("clientName").equalTo(getUserObjInPref().getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                        };
                        Map<String, Order> orders = dataSnapshot.getValue(genericTypeIndicator);

                        boolean isExist = false;
                        ArrayList<Order> orderList = new ArrayList<>();
                        if (orders != null) {
                            for (String key:orders.keySet()){
                                Order newOrder = orders.get(key);
                                newOrder.setOrderID(key);
                                orderList.add(newOrder);
                                if (newOrder.getScheduledDate() != null && !newOrder.getOrderStatus().name().equalsIgnoreCase(OrderStatus.INITIATED.name())){
                                    isExist = true;
                                    Calendar currentDate = Calendar.getInstance();
                                    currentDate.setTimeInMillis(System.currentTimeMillis());
                                    Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                                    int daysLeft = newOrder.getScheduledDate().getDate() - currentDate.getTime().getDate()-1;
                                    if (daysLeft == 2){
                                        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                                        notificationUtils.showNotificationMessage("Cartique","2 Days left before Car "+newOrder.getOrderType().name()+" for "+newOrder.getUserName(), Calendar.getInstance().getTime().toString(),intent);
                                    }else if (daysLeft == 1){
                                        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                                        notificationUtils.showNotificationMessage("Cartique","Car "+newOrder.getOrderType().name()+" Tomorrow for "+newOrder.getUserName(), Calendar.getInstance().getTime().toString(),intent);
                                    }
                                    else if (daysLeft == 0){
                                       // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                                        notificationUtils.showNotificationMessage("Cartique",newOrder.getOrderType().name()+" today for "+newOrder.getUserName(), Calendar.getInstance().getTime().toString(),intent);
                                    }
                                }
                            }
                        }else{

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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