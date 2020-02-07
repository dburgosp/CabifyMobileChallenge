package com.example.android.cabifymobilechallenge.data.pojo.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Public class for representing all the required information for any Cabify product.
 * Converted to Java class from http://www.jsonschema2pojo.org/
 */
public class Product implements Parcelable {

    /* **************** */
    /* Public constants */
    /* **************** */

    // Codes for identifying products in order to apply discounts.

    private static final String CODE_VOUCHER = "VOUCHER";
    private static final String CODE_TSHIRT = "TSHIRT";

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private Double price;

    // I would like to receive image information (a path for loading the image with Picasso or
    // Glide) and if it has a discount or not. I am going to receive null for this data, but I will
    // add these elements (imageResId and discount) to the class. I will fill this data later with
    // sample information.

    @SerializedName("image")
    @Expose
    private int imageResId;

    @SerializedName("hasDiscount")
    @Expose
    private boolean discount;

    /* ************ */
    /* Constructors */
    /* ************ */

    /**
     * No args constructor for use in serialization
     */
    public Product() {
    }

    /**
     * @param code       is the String code of the product.
     * @param name       is the name of the product.
     * @param price      is the price of the product.
     * @param imageResId is a resource image identifier for the small image in order to show the
     *                   product in a list.
     * @param discount   is true is the product has a discount applied on its price, false
     *                   otherwise.
     */
    public Product(String code, String name, Double price, int imageResId, boolean discount) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.discount = discount;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    /* **************** */
    /* Parcelable stuff */
    /* **************** */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeValue(this.price);
        dest.writeInt(this.imageResId);
        dest.writeByte(this.discount ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.imageResId = in.readInt();
        this.discount = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
