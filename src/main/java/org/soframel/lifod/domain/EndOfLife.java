package org.soframel.lifod.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EndOfLife.
 */

@Document(collection = "end_of_life")
public class EndOfLife implements Serializable {

    @Id
    private String id;

    @Field("user")
    private String user;

    @Field("end_of_life_date")
    private LocalDate endOfLifeDate;

    @Field("buying_date")
    private LocalDate buyingDate;

    @Field("product")
    private String product;

    @Field("product_version")
    private String productVersion;

    @Field("brand")
    private String brand;

    @Field("reason")
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getEndOfLifeDate() {
        return endOfLifeDate;
    }

    public void setEndOfLifeDate(LocalDate endOfLifeDate) {
        this.endOfLifeDate = endOfLifeDate;
    }

    public LocalDate getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(LocalDate buyingDate) {
        this.buyingDate = buyingDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EndOfLife endOfLife = (EndOfLife) o;

        if ( ! Objects.equals(id, endOfLife.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EndOfLife{" +
            "id=" + id +
            ", user='" + user + "'" +
            ", endOfLifeDate='" + endOfLifeDate + "'" +
            ", buyingDate='" + buyingDate + "'" +
            ", product='" + product + "'" +
            ", productVersion='" + productVersion + "'" +
            ", brand='" + brand + "'" +
            ", reason='" + reason + "'" +
            '}';
    }
}
