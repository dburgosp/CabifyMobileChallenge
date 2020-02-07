package com.example.android.cabifymobilechallenge.data.pojo.shoppingcart;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.cabifymobilechallenge.data.pojo.discounts.Discount;

/**
 * Public class for managing shopping cart products, names, prices and discounts.
 */
public class ShoppingCartItem implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String code;
    private int quantity;
    private String name;
    private Double price;
    private Double totalPrice;
    private Discount discount = null;
    private Double discountPrice;
    private String discountText;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    /**
     * @param code          is the String code of the product.
     * @param name          is the name of the product.
     * @param price         is the unit price of the product.
     * @param totalPrice    is the total price (unit price X quantity) of this product line.
     * @param discountPrice is the price to discount to the total price.
     * @param discountText  is the discount short description.
     * @param quantity      is the number of products of this type.
     */
    public ShoppingCartItem(String code, int quantity, String name, Double price, Double totalPrice,
                            Discount discount, Double discountPrice, String discountText) {
        this.code = code;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.discountPrice = discountPrice;
        this.discountText = discountText;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountText() {
        return discountText;
    }

    public void setDiscountText(String discountText) {
        this.discountText = discountText;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /* ********** */
    /* Parcelable */
    /* ********** */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeInt(this.quantity);
        dest.writeString(this.name);
        dest.writeValue(this.price);
        dest.writeValue(this.totalPrice);
        dest.writeParcelable(this.discount, flags);
        dest.writeValue(this.discountPrice);
        dest.writeString(this.discountText);
    }

    protected ShoppingCartItem(Parcel in) {
        this.code = in.readString();
        this.quantity = in.readInt();
        this.name = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.totalPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.discount = in.readParcelable(Discount.class.getClassLoader());
        this.discountPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.discountText = in.readString();
    }

    public static final Parcelable.Creator<ShoppingCartItem> CREATOR = new Parcelable.Creator<ShoppingCartItem>() {
        @Override
        public ShoppingCartItem createFromParcel(Parcel source) {
            return new ShoppingCartItem(source);
        }

        @Override
        public ShoppingCartItem[] newArray(int size) {
            return new ShoppingCartItem[size];
        }
    };
}
