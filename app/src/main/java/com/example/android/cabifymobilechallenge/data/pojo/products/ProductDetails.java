package com.example.android.cabifymobilechallenge.data.pojo.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.cabifymobilechallenge.data.pojo.discounts.Discount;

/**
 * Public class for representing the product details, adding description and discount information.
 */
public class ProductDetails implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String code;
    private String name;
    private Double price;
    private int imageResId;
    private String description;
    private Discount discount = null;

    /* ************ */
    /* Constructors */
    /* ************ */

    /**
     * Constructor for products without discount.
     *
     * @param code        is the String code of the product.
     * @param name        is the name of the product.
     * @param price       is the price of the product.
     * @param imageResId  is a resource image identifier for the small image in order to show the
     *                    product in a list.
     * @param description is a text containing the description of the product.
     */
    public ProductDetails(String code, String name, Double price, int imageResId,
                          String description) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
    }

    /**
     * Overloaded constructor for products with discount.
     *
     * @param code        is the String code of the product.
     * @param name        is the name of the product.
     * @param price       is the price of the product.
     * @param imageResId  is a resource image identifier for the small image in order to show the
     *                    product in a list.
     * @param description is a text containing the description of the product.
     * @param discount    is a {@link Discount} object with the optional discount applied to the
     *                    product.
     */
    public ProductDetails(String code, String name, Double price, int imageResId,
                          String description, Discount discount) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
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
        dest.writeString(this.description);
        dest.writeParcelable(this.discount, flags);
    }

    protected ProductDetails(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.imageResId = in.readInt();
        this.description = in.readString();
        this.discount = in.readParcelable(Discount.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductDetails> CREATOR = new Parcelable.Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel source) {
            return new ProductDetails(source);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };
}
