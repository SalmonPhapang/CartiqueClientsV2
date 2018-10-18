package car.com.cartique.client.utility;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Date;

import car.com.cartique.client.model.Order;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationGenerator {
    private static String FCM_ENDPOINT =
            "https://fcm.googleapis.com/fcm/send";
    public void sendMessageToFcm(String jsonMessage,String key) {
        final MediaType mediaType = MediaType.parse("application/json");

        OkHttpClient httpClient = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(FCM_ENDPOINT)
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .addHeader("Authorization", "key" + key)
                    .post(RequestBody.create(mediaType, jsonMessage)).build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Log.e(TAG, "error sending firebase app instance token to app server");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
             /*  log.info("Message has been sent to FCM server "
                        + response.body().string());*/
                    }
                }
            });

        } catch (Exception e) {
            // log.info("Error in sending message to FCM server " + e);
        }

    }
    public String getFCMDataMessage(Order order) {

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("to", order.getUserNotificationToken());

        JsonObject itemInfo = new JsonObject();
        itemInfo.addProperty("Name", order.getClientName());
        itemInfo.addProperty("Date", new Date().toString());
        jsonObj.add("data", itemInfo);

        JsonObject msgObj = new JsonObject();
        msgObj.add("message", jsonObj);

       // log.info("data  message " + msgObj.toString());

        return msgObj.toString();
    }
    public String getFCMNotificationMessage(Order order, String title, String msg) {
        JsonObject jsonObj = new JsonObject();
        // client registration key is sent as token in the message to FCM server
        jsonObj.addProperty("to", order.getUserNotificationToken());

        JsonObject notification = new JsonObject();
        notification.addProperty("body", msg);
        notification.addProperty("title", title);
        jsonObj.add("notification", notification);

        JsonObject message = new JsonObject();
        message.add("message", jsonObj);

        //log.info("notification message " + message.toString());

        return message.toString();
    }


}
