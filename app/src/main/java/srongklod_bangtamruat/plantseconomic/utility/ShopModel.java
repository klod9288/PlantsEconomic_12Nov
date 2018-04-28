package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopModel implements Parcelable{

    private String nameProductString,categoryString, descriptionString,
            priceString,stockString, urlImagePathString;

    public ShopModel() {
    }

    public ShopModel(String nameProductString,
                     String categoryString,
                     String descriptionString,
                     String priceString,
                     String stockString,
                     String urlImagePathString) {
        this.nameProductString = nameProductString;
        this.categoryString = categoryString;
        this.descriptionString = descriptionString;
        this.priceString = priceString;
        this.stockString = stockString;
        this.urlImagePathString = urlImagePathString;
    }

    protected ShopModel(Parcel in) {
        nameProductString = in.readString();
        categoryString = in.readString();
        descriptionString = in.readString();
        priceString = in.readString();
        stockString = in.readString();
        urlImagePathString = in.readString();
    }

    public static final Creator<ShopModel> CREATOR = new Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel in) {
            return new ShopModel(in);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };

    public String getNameProductString() {
        return nameProductString;
    }

    public void setNameProductString(String nameProductString) {
        this.nameProductString = nameProductString;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public String getDescriptionString() {
        return descriptionString;
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getStockString() {
        return stockString;
    }

    public void setStockString(String stockString) {
        this.stockString = stockString;
    }

    public String getUrlImagePathString() {
        return urlImagePathString;
    }

    public void setUrlImagePathString(String urlImagePathString) {
        this.urlImagePathString = urlImagePathString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameProductString);
        dest.writeString(categoryString);
        dest.writeString(descriptionString);
        dest.writeString(priceString);
        dest.writeString(stockString);
        dest.writeString(urlImagePathString);
    }
}//Main Class
