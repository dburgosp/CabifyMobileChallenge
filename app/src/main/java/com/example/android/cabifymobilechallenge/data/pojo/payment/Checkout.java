package com.example.android.cabifymobilechallenge.data.pojo.payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;

import java.util.ArrayList;

/**
 * Public class for managing the payment process.
 */
public class Checkout implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String userId;
    private String userName;
    private String userPhone;
    private ArrayList<ShoppingCartItem> shoppingCart;
    private Address address;
    private PaymentMethod paymentMethod;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    public Checkout(String userId, String userName, String userPhone,
                    ArrayList<ShoppingCartItem> shoppingCart, Address address,
                    PaymentMethod paymentMethod) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.shoppingCart = shoppingCart;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public ArrayList<ShoppingCartItem> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<ShoppingCartItem> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeTypedList(this.shoppingCart);
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.paymentMethod, flags);
    }

    protected Checkout(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.shoppingCart = in.createTypedArrayList(ShoppingCartItem.CREATOR);
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.paymentMethod = in.readParcelable(PaymentMethod.class.getClassLoader());
    }

    public static final Parcelable.Creator<Checkout> CREATOR = new Parcelable.Creator<Checkout>() {
        @Override
        public Checkout createFromParcel(Parcel source) {
            return new Checkout(source);
        }

        @Override
        public Checkout[] newArray(int size) {
            return new Checkout[size];
        }
    };
}
