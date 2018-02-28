package srongklod_bangtamruat.plantseconomic.utility;

import srongklod_bangtamruat.plantseconomic.R;

/**
 * Created by Administrator on 30/12/2560.
 */

public class Myconstan {

    private int[] iconCustomerInts = new int[]{
            R.drawable.customer_home,
            R.drawable.customer_friend,
            R.drawable.customer_message,
            R.drawable.customer_news,
            R.drawable.customer_shops,
            R.drawable.customer_chat};

    private int[] iconSupplier = new int[]{
            R.drawable.supplier_home,
            R.drawable.supplier_massage,
            R.drawable.supplier_news,
            R.drawable.supplier_shops};

    private int[] iconTransport = new int[]{
            R.drawable.transport_home,
            R.drawable.transport_msg,
            R.drawable.transport_package,
            R.drawable.transport_logistic};

    private String[] titleCustomerStrings = new String[]{"Home","AddFriend","Massage","News","Shops","Chat"};

    private String[] titleSupplierStrings = new String[]{"Home","Massage","News","Shops"};

    private String[] titleTransportStrings = new String[]{"Home","Massage","Package","Logistic"};



    public int[] getIconCustomerInts() {
        return iconCustomerInts;
    }

    public String[] getTitleCustomerStrings() {
        return titleCustomerStrings;
    }

    private String[] fieldCustomerStrings = new String[]{
            "lastNameString ",
            "nameString",
            "phoneString",
            "uidUserString ",};

    private String[] fieldSupplierStrings = new String[]{
            "addressString ",
            "bussinessString ",
            "companyString ",
            "faxString ",
            "headquartersString ",
            "telephoneString ",
            "uidUserString ","statusString"};

    private String[] fieldTransportStrings = new String[]{
            "addressString ",
            "branchString ",
            "companyString ",
            "faxString ",
            "headquarterString ",
            "telephoneString ",
            "uidUserString ","statusString"};


    public int[] getIconTransport() {
        return iconTransport;
    }

    public String[] getTitleTransportStrings() {
        return titleTransportStrings;
    }

    public int[] getIconSupplier() {
        return iconSupplier;
    }

    public String[] getTitleSupplierStrings() {
        return titleSupplierStrings;
    }

    public String[] getFieldCustomerStrings() {
        return fieldCustomerStrings;
    }

    public String[] getFieldSupplierStrings() {
        return fieldSupplierStrings;
    }

    public String[] getFieldTransportStrings() {
        return fieldTransportStrings;
    }
}
