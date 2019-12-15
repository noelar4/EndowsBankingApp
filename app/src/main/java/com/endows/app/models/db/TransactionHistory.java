package com.endows.app.models.db;

public class TransactionHistory {
    private String isDebit;
    private String isCredit;
    private String from;
    private String to;
    private String amount;
    private String timestamp;

    public String getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(String isDebit) {
        this.isDebit = isDebit;
    }

    public String getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(String isCredit) {
        this.isCredit = isCredit;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "isDebit='" + isDebit + '\'' +
                ", isCredit='" + isCredit + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount='" + amount + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
