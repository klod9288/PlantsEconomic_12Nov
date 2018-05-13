package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageCustomerSentToSupplierModel implements Parcelable{
    private String dateTimeString, messageContentString;

    public MessageCustomerSentToSupplierModel() {
    }

    public MessageCustomerSentToSupplierModel(String dateTimeString, String messageContentString) {
        this.dateTimeString = dateTimeString;
        this.messageContentString = messageContentString;
    }

    protected MessageCustomerSentToSupplierModel(Parcel in) {
        dateTimeString = in.readString();
        messageContentString = in.readString();
    }

    public static final Creator<MessageCustomerSentToSupplierModel> CREATOR = new Creator<MessageCustomerSentToSupplierModel>() {
        @Override
        public MessageCustomerSentToSupplierModel createFromParcel(Parcel in) {
            return new MessageCustomerSentToSupplierModel(in);
        }

        @Override
        public MessageCustomerSentToSupplierModel[] newArray(int size) {
            return new MessageCustomerSentToSupplierModel[size];
        }
    };

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public String getMessageContentString() {
        return messageContentString;
    }

    public void setMessageContentString(String messageContentString) {
        this.messageContentString = messageContentString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateTimeString);
        dest.writeString(messageContentString);
    }
}//Main Class
