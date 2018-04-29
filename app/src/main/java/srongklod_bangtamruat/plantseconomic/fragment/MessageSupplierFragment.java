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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.CustomerModel;
import srongklod_bangtamruat.plantseconomic.utility.MessageModel;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;
import srongklod_bangtamruat.plantseconomic.utility.SupplierModel;

public class MessageSupplierFragment extends Fragment{

    private TextView headTextView,currentDateTextView, postionTextView;
    private EditText bodyEditText;
    private String uidLoginString;
    private String uidUserString, companyString,
            addressString, faxString, telephoneString,
            bussinessString, headquartersString,statusString;

    private ArrayList<String> nameAnSurnameStringArrayList,uidSenderStringArrayList;
    private String currentDateString,senderString,messageString,uidSenderString;

    private String idMessageString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find UidLogin
        findUidLogin();


//        Get Value From preference
        getValueFromSharePreference();

//        Get Value From Firebase
        getValueFromFirebase();

//        Create Sender Spinner
        createSenderSpinner();

//        Send Controoler
        sendControoler();

    }//Main Method

    private void sendControoler() {

        Button button = getView().findViewById(R.id.btnSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAlert myAlert = new MyAlert(getActivity());
                EditText editText = getView().findViewById(R.id.edtBody);
                messageString = editText.getText().toString().trim();

                if (senderString.equals("Please Choose Sender")) {

                    myAlert.nomalDialog("Non Choose Sender","Please Choose Sender");



                } else if (messageString.isEmpty()) {
                    myAlert.nomalDialog(getString(R.string.title_have_space),
                            getString(R.string.massage_have_space));

                } else {
//                    Update Message to Firebase
                    Log.d("18AprilV2", "Current Time ==> " +currentDateString);
                    Log.d("18AprilV2", "Message ==> " +messageString);
                    Log.d("18AprilV2", "Sender ==> " +senderString);
                    Log.d("18AprilV2", "uid of sender ==>" +uidSenderString);

//                    Upload value to Firebase
                    MessageModel messageModel = new MessageModel(currentDateString,
                            messageString,senderString,uidSenderString);
                    Random random = new Random();
                    int i = random.nextInt(10000);
                    idMessageString = "idMessage-" + Integer.toString(i);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference()
                            .child("Supplier")
                            .child(uidLoginString)
                            .child("Message").child(idMessageString);
                    databaseReference.setValue(messageModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    changeReceiveMessage();

                                    Toast.makeText(getActivity(),"Success Update to Firebase",
                                            Toast.LENGTH_SHORT).show();

                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.contentServiceFragment, new MessageListFragment())
                                            .commit();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(),"Cannot Update to Firebase",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });


                }//if

            }//onClick
        });


    }

    private void changeReceiveMessage() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Customer")
                .child(uidSenderString);

        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("ReceiveMessage","true");
        databaseReference.updateChildren(stringObjectMap);


    }

    private void createSenderSpinner() {

        final Spinner spinner = getView().findViewById(R.id.senderSpinner);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Customer");

        final int[] timesInts = new int[]{0};
        nameAnSurnameStringArrayList = new ArrayList<>();
        nameAnSurnameStringArrayList.add("Please Choose Sender");

        uidSenderStringArrayList = new ArrayList<>();
        uidSenderStringArrayList.add("non");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int amountCustomerInt = (int) dataSnapshot.getChildrenCount();
                Log.d("18AprilV1", "Amount of Customer ==>" + amountCustomerInt);

                List list = new ArrayList();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Setter Model
                    CustomerModel customerModel = dataSnapshot1.getValue(CustomerModel.class);
                    list.add(customerModel);

//                    GetterFrom Model
                    CustomerModel customerModel1 = (CustomerModel) list.get(timesInts[0]);
                    String nameString = customerModel1.getNameString();
                    String surnameString = customerModel1.getLastNameString();
                    String resultString = nameString + " " + surnameString;
                    Log.d("18AprilV1", "Result Name an Sur[" + timesInts[0] + "] ==>"+resultString);

                    nameAnSurnameStringArrayList.add(resultString);

                    uidSenderStringArrayList.add(customerModel1.getUidUserString());

                    timesInts[0] += 1;
                }//for

                MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());

                final String[] strings = myChangeArrayListToArray.myArray(nameAnSurnameStringArrayList.toString());

                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),android.R.layout.simple_list_item_1,strings);
                spinner.setAdapter(stringArrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        senderString = strings[position];
                        uidSenderString = uidSenderStringArrayList.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        senderString = strings[0];

                    }
                });



            }//onDAtaChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }//Create Sender Spinner

    private void showView() {

        headTextView = getView().findViewById(R.id.txtHead);
        currentDateTextView = getView().findViewById(R.id.txtCurrentDate);
        postionTextView = getView().findViewById(R.id.txtPosition);
        bodyEditText = getView().findViewById(R.id.edtBody);

//        For Head
        headTextView.setText(headquartersString);
//        For Current Date
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
        currentDateString = dateFormat.format(calendar.getTime());
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
