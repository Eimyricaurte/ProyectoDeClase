package model;

public class Product {
    private String code;
    private String name;
    private int stock;
    private int price;

    public Product(String code, String name, int stock, int price) {
        this.code = code;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Inventario [code=" + code + ", name=" + name + ", stock=" + stock + ", price=" + price + "]";
    }
    
}
