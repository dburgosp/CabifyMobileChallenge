package com.example.android.cabifymobilechallenge.data.pojo.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String id = "";
    private String name = "";
    private String surname = "";
    private String password = "";
    private String phone = "";
    private String email;
    private String token = "";
    private ArrayList<Address> addresses = new ArrayList<>();

    /* ************ */
    /* Constructors */
    /* ************ */

    public User(String id, String name, String surname, String password, String phone, String email,
                String token, ArrayList<Address> addresses) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.token = token;
        this.addresses = addresses;
    }

    public User(String email) {
        this.email = email;
    }

    /* ******************* */
    /* Getters and Setters */
    /* ******************* */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
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
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.token);
        dest.writeTypedList(this.addresses);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.token = in.readString();
        this.addresses = in.createTypedArrayList(Address.CREATOR);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
