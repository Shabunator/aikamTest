package Entities;

public class Products {
    String productName;
    Double price;

    @Override
    public String toString() {
        return "Products{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Products(String productName, Double price) {
        this.productName = productName;
        this.price = price;
    }
}
