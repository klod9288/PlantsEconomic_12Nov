package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageModel implements Parcelable{

    private String currentDateString,messageString,nameAnSurnameString, uidSenderString;

    public MessageModel() {
    }

    public MessageModel(String currentDateString, String messageString, String nameAnSurnameString, String uidSenderString) {
        this.currentDateString = currentDateString;
        this.messageString = messageString;
        this.nameAnSurnameString = nameAnSurnameString;
        this.uidSenderString = uidSenderString;
    }

    protected MessageModel(Parcel in) {
        currentDateString = in.readString();
        messageString = in.readString();
        nameAnSurnameString = in.readString();
        uidSenderString = in.readString();
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    public String getCurrentDateString() {
        return currentDateString;
    }

    public void setCurrentDateString(String currentDateString) {
        this.currentDateString = currentDateString;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public String getNameAnSurnameString() {
        return nameAnSurnameString;
    }

    public void setNameAnSurnameString(String nameAnSurnameString) {
        this.nameAnSurnameString = nameAnSurnameString;
    }

    public String getUidSenderString() {
        return uidSenderString;
    }

    public void setUidSenderString(String uidSenderString) {
        this.uidSenderString = uidSenderString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currentDateString);
        dest.writeString(messageString);
        dest.writeString(nameAnSurnameString);
        dest.writeString(uidSenderString);
    }
}
