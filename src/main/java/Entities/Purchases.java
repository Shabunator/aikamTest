package Entities;

import java.sql.Date;

public class Purchases {
    Integer buyerId;
    Integer productId;
    Date date;

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Date getDate() {
        return date;
    }

    public Purchases(Integer buyerId, Integer productId, Date date) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.date = date;
    }
}
