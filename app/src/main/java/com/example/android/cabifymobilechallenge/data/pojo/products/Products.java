package com.example.android.cabifymobilechallenge.data.pojo.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Public class for representing a list of Cabify {@link Product} objects.
 * Converted to Java class from http://www.jsonschema2pojo.org/
 */
public class Products implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    /* ************ */
    /* Constructors */
    /* ************ */

    /**
     * No args constructor for use in serialization
     */
    public Products() {
    }

    /**
     * @param products is a list of Cabify {@link Product} objects.
     */
    public Products(List<Product> products) {
        super();
        this.products = products;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
        dest.writeTypedList(this.products);
    }

    protected Products(Parcel in) {
        this.products = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}