package com.example.android.cabifymobilechallenge.data.pojo.discounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Public class for managing product discounts of "Buy more than N and pay X" type.
 */
public class DiscountTypeNorMore implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private int n;
    private double price;

    /* ************ */
    /* Constructors */
    /* ************ */

    public DiscountTypeNorMore(int n, double price) {
        this.n = n;
        this.price = price;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        dest.writeInt(this.n);
        dest.writeDouble(this.price);
    }

    protected DiscountTypeNorMore(Parcel in) {
        this.n = in.readInt();
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<DiscountTypeNorMore> CREATOR = new Parcelable.Creator<DiscountTypeNorMore>() {
        @Override
        public DiscountTypeNorMore createFromParcel(Parcel source) {
            return new DiscountTypeNorMore(source);
        }

        @Override
        public DiscountTypeNorMore[] newArray(int size) {
            return new DiscountTypeNorMore[size];
        }
    };
}
