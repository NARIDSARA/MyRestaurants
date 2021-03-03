package com.naridsara.myrestaurants;

public class Order {

    private String orderId;
    private int customerId;
    private String status;
    private String created;

    public Order() {
    }

    public Order(String orderId, int customerId, String status, String created) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.created = created;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
