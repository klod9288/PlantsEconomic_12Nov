package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MessageCustomerSentToSupplierModel;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;

public class MessageCustomerFragment extends Fragment{

    //    Explicit
    private String uidSupplierMessageString,officeHeadSupplierString,
    dateString,nameSupplierString,messageString,uidLoginString,refMessageString;





    public static MessageCustomerFragment messageCustomerInstance(String uidSupplierString) {

        MessageCustomerFragment messageCustomerFragment = new MessageCustomerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Supplier",uidSupplierString);
        messageCustomerFragment.setArguments(bundle);
        return messageCustomerFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get UidSupplier
        uidSupplierMessageString = getArguments().getString("Supplier");
        Log.d("12MayV2", "Receive of uidSupplier ==> " + uidSupplierMessageString);

//        Get uidLogin
        getUidLogin();

//        Create RefMessage
        createRefMessage();


//        Show Supplier
        showSupplier();

//        Show Date
        showDate();

//        Send Controller
        sendController();


    }//Main Method

    private void createRefMessage() {

        Random random = new Random();
        int ranInt = random.nextInt(10000);
        refMessageString = "ref-" + Integer.toString(ranInt);



    }

    private void getUidLogin() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        uidLoginString = firebaseUser.getUid();


    }

    private void sendController() {

        Button button = getActivity().findViewById(R.id.btnSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = getView().findViewById(R.id.edtBody);
                messageString = editText.getText().toString().trim();

                if (messageString.isEmpty()) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog(getString(R.string.title_have_space),
                            getString(R.string.massage_have_space));

                } else {

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference()
                            .child("Supplier")
                            .child(uidSupplierMessageString)
                            .child("Message")
                            .child(uidLoginString)
                            .child(refMessageString);

                    MessageCustomerSentToSupplierModel messageCustomerSentToSupplierModel =
                            new MessageCustomerSentToSupplierModel(dateString,messageString);
                    databaseReference.setValue(messageCustomerSentToSupplierModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        //                Back
                                        getActivity().getSupportFragmentManager().popBackStack();

                                    } else {

                                        MyAlert myAlert = new MyAlert(getActivity());
                                        myAlert.nomalDialog("Cannot Sent Message",
                                                task.getException().getMessage().toString());

                                    }
                                }
                            });





                } //if





            }
        });


    }

    private void showDate() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateString = dateFormat.format(calendar.getTime());


        TextView textView = getView().findViewById(R.id.txtCurrentDate);
        textView.setText(dateString);



    }

    private void showSupplier() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Supplier")
                .child(uidSupplierMessageString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map map= (Map) dataSnapshot.getValue();
                officeHeadSupplierString = String.valueOf(map.get("headquartersString"));
                nameSupplierString = String.valueOf(map.get("companyString"));

                TextView headTextView = getView().findViewById(R.id.txtHead);
                headTextView.setText(officeHeadSupplierString);

                TextView nameSupplierTextView = getView().findViewById(R.id.txtsender);
                nameSupplierTextView.setText(nameSupplierString);



            }//onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_customer, container, false);
        return view;



    }




}//Main Class
