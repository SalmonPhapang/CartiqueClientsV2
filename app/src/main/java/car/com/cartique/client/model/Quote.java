package car.com.cartique.client.model;

import java.io.Serializable;
import java.util.Date;

public class Quote implements Serializable {
    private String clientName;
    private String location;
    private String amount;
    private Date quoteDate;
    private QuoteStatus quoteStatus;
    private String clientNotifictionToken;
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public QuoteStatus getQuoteStatus() {
        return quoteStatus;
    }
    public String getClientNotifictionToken() {
        return clientNotifictionToken;
    }

    public void setClientNotifictionToken(String clientNotifictionToken) {
        this.clientNotifictionToken = clientNotifictionToken;
    }

    public void setQuoteStatus(QuoteStatus quoteStatus) {
        this.quoteStatus = quoteStatus;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
