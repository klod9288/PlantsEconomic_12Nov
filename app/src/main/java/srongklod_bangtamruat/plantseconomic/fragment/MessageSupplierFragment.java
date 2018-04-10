package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.SupplierModel;

public class MessageSupplierFragment extends Fragment{

    private TextView headTextView,currentDateTextView,insideAddressTextView, postionTextView;
    private EditText bodyEditText;
    private String uidLoginString;
    private String uidUserString, companyString,
            addressString, faxString, telephoneString,
            bussinessString, headquartersString,statusString;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find UidLogin
        findUidLogin();


//        Get Value From preference
        getValueFromSharePreference();

//        Get Value From Firebase
        getValueFromFirebase();






    }//Main Method

    private void showView() {

        headTextView = getView().findViewById(R.id.txtHead);
        currentDateTextView = getView().findViewById(R.id.txtCurrentDate);
        insideAddressTextView = getView().findViewById(R.id.txtInsideAddress);
        postionTextView = getView().findViewById(R.id.txtPosition);
        bodyEditText = getView().findViewById(R.id.edtBody);

//        For Head
        headTextView.setText(headquartersString);
//        For Current Date
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
        String currentDateString = dateFormat.format(calendar.getTime());
        currentDateTextView.setText(currentDateString);


    }

    private void getValueFromFirebase() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Supplier")
                .child(uidLoginString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SupplierModel supplierModel = dataSnapshot.getValue(SupplierModel.class);

                uidUserString = supplierModel.getUidUserString();
                companyString = supplierModel.getCompanyString();
                addressString = supplierModel.getAddressString();
                faxString = supplierModel.getFaxString();
                telephoneString = supplierModel.getTelephoneString();
                bussinessString = supplierModel.getBussinessString();
                headquartersString = supplierModel.getHeadquartersString();
                statusString = supplierModel.getStatusString();

                showLog();
                showView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showLog() {

        Log.d("10AprilV2", "uid ==> " + uidUserString);
        Log.d("10AprilV2", "company ==> " + companyString);
        Log.d("10AprilV2", "address ==> " + addressString);
        Log.d("10AprilV2", "fax ==> " + faxString);
        Log.d("10AprilV2", "telephone ==> " + telephoneString);
        Log.d("10AprilV2", "business ==> " + bussinessString);
        Log.d("10AprilV2", "headquarter ==> " + headquartersString);
        Log.d("10AprilV2", "status ==> " + statusString);

    }

    private void findUidLogin() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        uidLoginString = firebaseUser.getUid();
        Log.d("10AprilV2", "uidLogin ==> " + uidLoginString);

    }

    private void getValueFromSharePreference() {
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("LoginFile", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_supplier, container, false);
        return view;
    }
}//Main Class
