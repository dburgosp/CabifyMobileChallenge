package com.example.android.cabifymobilechallenge.data.pojo.user;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String alias;
    private String street;
    private String number;
    private String streetInfo;
    private String location;
    private String region;
    private String postalCode;
    private String country;

    /* ************ */
    /* Constructors */
    /* ************ */

    public Address(String alias, String street, String number, String streetInfo, String location,
                   String region, String postalCode, String country) {
        this.alias = alias;
        this.street = street;
        this.number = number;
        this.streetInfo = streetInfo;
        this.location = location;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
    }

    /* ******************* */
    /* Getters and Setters */
    /* ******************* */

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreetInfo() {
        return streetInfo;
    }

    public void setStreetInfo(String streetInfo) {
        this.streetInfo = streetInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        dest.writeString(this.alias);
        dest.writeString(this.street);
        dest.writeString(this.number);
        dest.writeString(this.streetInfo);
        dest.writeString(this.location);
        dest.writeString(this.region);
        dest.writeString(this.postalCode);
        dest.writeString(this.country);
    }

    protected Address(Parcel in) {
        this.alias = in.readString();
        this.street = in.readString();
        this.number = in.readString();
        this.streetInfo = in.readString();
        this.location = in.readString();
        this.region = in.readString();
        this.postalCode = in.readString();
        this.country = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
