package com.example.android.cabifymobilechallenge.data.pojo.discounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Public class for representing types of product discounts.
 */
public class Discount<DiscountType extends Parcelable> implements Parcelable {

    public static final int TYPE_M_X_N = 0;
    public static final int TYPE_N_OR_MORE = 1;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private int type;
    private DiscountType discount;

    /* ************ */
    /* Constructors */
    /* ************ */

    public Discount(int type, DiscountType discount) {
        this.type = type;
        this.discount = discount;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DiscountType getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountType discount) {
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
        dest.writeInt(this.type);

        // Write the generic data member class name to the parcel.
        dest.writeString(this.discount.getClass().getName());
        dest.writeParcelable(this.discount, flags);
    }

    protected Discount(Parcel in) {
        this.type = in.readInt();

        // Read the generic data member class name back in order to create its class loader.
        final String className = in.readString();
        try {
            discount = in.readParcelable(Class.forName(className).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final Parcelable.Creator<Discount> CREATOR = new Parcelable.Creator<Discount>() {
        @Override
        public Discount createFromParcel(Parcel source) {
            return new Discount(source);
        }

        @Override
        public Discount[] newArray(int size) {
            return new Discount[size];
        }
    };
}
