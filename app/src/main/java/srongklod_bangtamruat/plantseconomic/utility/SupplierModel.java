package srongklod_bangtamruat.plantseconomic.utility;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 27/12/2560.
 */

public class SupplierModel implements Parcelable{

    private String uidUserString, companyString,
            addressString, faxString, telephoneString,
            bussinessString, headquartersString,statusString,pathLogoString;

    public SupplierModel() {
    }

    public SupplierModel(String uidUserString,
                         String companyString,
                         String addressString,
                         String faxString,
                         String telephoneString,
                         String bussinessString,
                         String headquartersString,
                         String statusString,
                         String pathLogoString) {
        this.uidUserString = uidUserString;
        this.companyString = companyString;
        this.addressString = addressString;
        this.faxString = faxString;
        this.telephoneString = telephoneString;
        this.bussinessString = bussinessString;
        this.headquartersString = headquartersString;
        this.statusString = statusString;
        this.pathLogoString = pathLogoString;
    }

    protected SupplierModel(Parcel in) {
        uidUserString = in.readString();
        companyString = in.readString();
        addressString = in.readString();
        faxString = in.readString();
        telephoneString = in.readString();
        bussinessString = in.readString();
        headquartersString = in.readString();
        statusString = in.readString();
        pathLogoString = in.readString();
    }

    public static final Creator<SupplierModel> CREATOR = new Creator<SupplierModel>() {
        @Override
        public SupplierModel createFromParcel(Parcel in) {
            return new SupplierModel(in);
        }

        @Override
        public SupplierModel[] newArray(int size) {
            return new SupplierModel[size];
        }
    };

    public String getUidUserString() {
        return uidUserString;
    }

    public void setUidUserString(String uidUserString) {
        this.uidUserString = uidUserString;
    }

    public String getCompanyString() {
        return companyString;
    }

    public void setCompanyString(String companyString) {
        this.companyString = companyString;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getFaxString() {
        return faxString;
    }

    public void setFaxString(String faxString) {
        this.faxString = faxString;
    }

    public String getTelephoneString() {
        return telephoneString;
    }

    public void setTelephoneString(String telephoneString) {
        this.telephoneString = telephoneString;
    }

    public String getBussinessString() {
        return bussinessString;
    }

    public void setBussinessString(String bussinessString) {
        this.bussinessString = bussinessString;
    }

    public String getHeadquartersString() {
        return headquartersString;
    }

    public void setHeadquartersString(String headquartersString) {
        this.headquartersString = headquartersString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getPathLogoString() {
        return pathLogoString;
    }

    public void setPathLogoString(String pathLogoString) {
        this.pathLogoString = pathLogoString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uidUserString);
        dest.writeString(companyString);
        dest.writeString(addressString);
        dest.writeString(faxString);
        dest.writeString(telephoneString);
        dest.writeString(bussinessString);
        dest.writeString(headquartersString);
        dest.writeString(statusString);
        dest.writeString(pathLogoString);
    }
}//Main Class
