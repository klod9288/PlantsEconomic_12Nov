package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class PostModel implements Parcelable{

    private String dateTimeString, postString;

    public PostModel() {


    }

    public PostModel(String dateTimeString, String postString) {
        this.dateTimeString = dateTimeString;
        this.postString = postString;
    }

    protected PostModel(Parcel in) {
        dateTimeString = in.readString();
        postString = in.readString();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public String getPostString() {
        return postString;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateTimeString);
        dest.writeString(postString);
    }
}//Main Class
