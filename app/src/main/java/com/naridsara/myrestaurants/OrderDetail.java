package com.naridsara.myrestaurants;

public class OrderDetail {

    private int orderDetailId;
    private String orderId;
    private int foodId;
    private int qty;
    private String foodName;
    private String foodNameUs;
    private String foodImage;
    private int foodPrice;
    private int foodDetailId;
    private int foodTypeId;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, String orderId, int foodId, int qty, String foodName, String foodNameUs, String foodImage, int foodPrice, int foodDetailId, int foodTypeId) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.qty = qty;
        this.foodName = foodName;
        this.foodNameUs = foodNameUs;
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.foodDetailId = foodDetailId;
        this.foodTypeId = foodTypeId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodNameUs() {
        return foodNameUs;
    }

    public void setFoodNameUs(String foodNameUs) {
        this.foodNameUs = foodNameUs;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodDetailId() {
        return foodDetailId;
    }

    public void setFoodDetailId(int foodDetailId) {
        this.foodDetailId = foodDetailId;
    }

    public int getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(int foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

}
