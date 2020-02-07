package com.example.android.cabifymobilechallenge.data.pojo.shoppingcart;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Public class for managing shopping cart items saved into local storage.
 */
public class StoredShoppingCartItem implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String productCode;
    private int quantity;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    /**
     * @param productCode is the code of the Cabify product.
     * @param quantity    is the number of products of this type.
     */
    public StoredShoppingCartItem(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        dest.writeString(this.productCode);
        dest.writeInt(this.quantity);
    }

    protected StoredShoppingCartItem(Parcel in) {
        this.productCode = in.readString();
        this.quantity = in.readInt();
    }

    public static final Parcelable.Creator<StoredShoppingCartItem> CREATOR = new Parcelable.Creator<StoredShoppingCartItem>() {
        @Override
        public StoredShoppingCartItem createFromParcel(Parcel source) {
            return new StoredShoppingCartItem(source);
        }

        @Override
        public StoredShoppingCartItem[] newArray(int size) {
            return new StoredShoppingCartItem[size];
        }
    };
}
