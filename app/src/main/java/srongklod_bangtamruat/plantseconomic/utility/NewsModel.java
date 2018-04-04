package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsModel implements Parcelable{
    private String newsString;

    public NewsModel() {
    }

    public NewsModel(String newsString) {
        this.newsString = newsString;
    }

    protected NewsModel(Parcel in) {
        newsString = in.readString();
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    public String getNewsString() {
        return newsString;
    }

    public void setNewsString(String newsString) {
        this.newsString = newsString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsString);
    }
}
