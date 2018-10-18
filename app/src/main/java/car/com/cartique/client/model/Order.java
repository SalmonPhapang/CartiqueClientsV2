package car.com.cartique.client.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private Car car;
    private Date orderDate;
    private String orderID;
    private String orderNumber;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private String clientID;
    private String userID;
    private String userName;
    private String clientName;
    private String amount;
    private Date scheduledDate;
    private boolean quoted;
    List<String> logBook = new ArrayList<>();
    private ArrayList<String> uploadedImages = new ArrayList<String>();
    private String userNotificationToken;
    private ArrayList<Quote> quotes = new ArrayList<Quote>();
    private ArrayList<Date> availableDates = new ArrayList<Date>();

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public ArrayList<Date> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(ArrayList<Date> availableDates) {
        this.availableDates = availableDates;
    }
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserNotificationToken() {
        return userNotificationToken;
    }

    public void setUserNotificationToken(String userNotificationToken) {
        this.userNotificationToken = userNotificationToken;
    }
    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public ArrayList<String> getUploadedImages() {
        return uploadedImages;
    }
    public void setUploadedImages(ArrayList<String> uploadedImages) {
        if(this.uploadedImages==null){
            this.uploadedImages = new ArrayList<>();
        }
        this.uploadedImages = uploadedImages;
    }
    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        if(this.quotes==null){
            this.quotes = new ArrayList<>();
        }
        this.quotes = quotes;
    }
    public boolean isQuoted() {
        return quoted;
    }

    public void setQuoted(boolean quoted) {
        this.quoted = quoted;
    }

    public List<String>  getLogBook() {
        return logBook;
    }
    public void setLogBook(List<String>  logBook) {
        this.logBook = logBook;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

}
