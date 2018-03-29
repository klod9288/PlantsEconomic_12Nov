package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;

public class MyChangeArrayListToArray {

    private Context context;

    public MyChangeArrayListToArray(Context context) {
        this.context = context;
    }

    public String[] myArray(String arrayListString) {

        String s = arrayListString.substring(1, arrayListString.length() - 1);
        String[] strings=s.split(",");
        for (int i=0;i<strings.length;i+=1) {
            strings[i] = strings[i].trim();
        }

        return strings;
    }


}//Main Class
