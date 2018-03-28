package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


public class CustomerModel implements Parcelable{

    private String uidUserString,
            nameString,
            lastNameString,
            phoneString,
            avataString,
            urlImageString;

    public CustomerModel() {
    }

    public CustomerModel(String uidUserString, String nameString, String lastNameString, String phoneString, String avataString, String urlImageString) {
        this.uidUserString = uidUserString;
        this.nameString = nameString;
        this.lastNameString = lastNameString;
        this.phoneString = phoneString;
        this.avataString = avataString;
        this.urlImageString = urlImageString;
    }

    protected CustomerModel(Parcel in) {
        uidUserString = in.readString();
        nameString = in.readString();
        lastNameString = in.readString();
        phoneString = in.readString();
        avataString = in.readString();
        urlImageString = in.readString();
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel in) {
            return new CustomerModel(in);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public String getUidUserString() {
        return uidUserString;
    }

    public void setUidUserString(String uidUserString) {
        this.uidUserString = uidUserString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getLastNameString() {
        return lastNameString;
    }

    public void setLastNameString(String lastNameString) {
        this.lastNameString = lastNameString;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public String getAvataString() {
        return avataString;
    }

    public void setAvataString(String avataString) {
        this.avataString = avataString;
    }

    public String getUrlImageString() {
        return urlImageString;
    }

    public void setUrlImageString(String urlImageString) {
        this.urlImageString = urlImageString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uidUserString);
        dest.writeString(nameString);
        dest.writeString(lastNameString);
        dest.writeString(phoneString);
        dest.writeString(avataString);
        dest.writeString(urlImageString);
    }
}   // Main Class