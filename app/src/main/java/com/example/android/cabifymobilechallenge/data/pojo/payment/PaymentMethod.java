package com.example.android.cabifymobilechallenge.data.pojo.payment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Public class for managing the payment methods.
 */
public class PaymentMethod implements Parcelable {

    public static final int PAYMENT_PAYPAL = 0;
    public static final int PAYMENT_CREDIT_CARD = 1;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String name;
    private int code;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    public PaymentMethod(String name, int code) {
        this.name = name;
        this.code = code;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
        dest.writeString(this.name);
        dest.writeInt(this.code);
    }

    protected PaymentMethod(Parcel in) {
        this.name = in.readString();
        this.code = in.readInt();
    }

    public static final Parcelable.Creator<PaymentMethod> CREATOR = new Parcelable.Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel source) {
            return new PaymentMethod(source);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };
}
