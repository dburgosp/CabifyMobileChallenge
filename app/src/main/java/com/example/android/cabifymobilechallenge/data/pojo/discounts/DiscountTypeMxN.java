package com.example.android.cabifymobilechallenge.data.pojo.discounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Public class for managing product discounts of "Get M, pay N" type.
 */
public class DiscountTypeMxN implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private int m;
    private int n;

    /* ************ */
    /* Constructors */
    /* ************ */

    public DiscountTypeMxN(int m, int n) {
        this.m = m;
        this.n = n;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
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
        dest.writeInt(this.m);
        dest.writeInt(this.n);
    }

    protected DiscountTypeMxN(Parcel in) {
        this.m = in.readInt();
        this.n = in.readInt();
    }

    public static final Parcelable.Creator<DiscountTypeMxN> CREATOR = new Parcelable.Creator<DiscountTypeMxN>() {
        @Override
        public DiscountTypeMxN createFromParcel(Parcel source) {
            return new DiscountTypeMxN(source);
        }

        @Override
        public DiscountTypeMxN[] newArray(int size) {
            return new DiscountTypeMxN[size];
        }
    };
}
