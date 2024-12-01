package model;

public class Sale {
    private String code;
    private String productName;
    private int quantity;
    private double price;
    private String date;
    public Sale(String code, String productName, int quantity, double price, String date) {
        this.code = code;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Sale [code=" + code + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + ", date=" + date + "]";
    }
    
}
