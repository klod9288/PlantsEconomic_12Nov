package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadAllShop extends AsyncTask<String,Void,String>{

    private Context context;

    public ReadAllShop(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {

        try {

            int countInt = 0;

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference()
                    .child("ShopSupplier")
                    .child("Shop-" + strings[0]);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int amountChileInt = (int) dataSnapshot.getChildrenCount();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}//MAin Class
