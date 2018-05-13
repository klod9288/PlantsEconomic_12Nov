package srongklod_bangtamruat.plantseconomic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import srongklod_bangtamruat.plantseconomic.fragment.CustomerShowFragment;
import srongklod_bangtamruat.plantseconomic.fragment.DrawerMenuCustomerFragment;
import srongklod_bangtamruat.plantseconomic.fragment.DrawerMenuSupplierFragment;
import srongklod_bangtamruat.plantseconomic.fragment.DrawerMenuTransportFragment;
import srongklod_bangtamruat.plantseconomic.fragment.SupplierShowFragment;
import srongklod_bangtamruat.plantseconomic.fragment.TransportShowFragment;
import srongklod_bangtamruat.plantseconomic.utility.CustomerModel;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;
import srongklod_bangtamruat.plantseconomic.utility.SupplierModel;
import srongklod_bangtamruat.plantseconomic.utility.TransportModel;

public class ServiceActivity extends AppCompatActivity {

    //    Explicit
//    About Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    //    General
    private String tag = "30DecV1";
    private String userUidString,titleToolbarString,subTitleToolbarString;
    private boolean statusABoolean = true; // true ==> Don't Find userUid ?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        Find User uid
        findUserUid();

//        Find userUid in Customer
        findUserUidinCustomer();

//        Find userUid in Supplier
        findUserUidinSupplier();


//        Find userUid in Transport
        findUserUidinTransport();

        createToolbar();

//        Check ReceiveMessage
//        checkReceiveMessage();


    }   // Main Method

    private void checkReceiveMessage() {
        Log.d("29AprilV7", "uidLogin ==> " + userUidString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Customer")
                .child(userUidString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();
                String receiveMessageString = String.valueOf(map.get("ReceiveMessage"));
                Log.d("29AprilV7", "ReceiveMessage ==> " + receiveMessageString);

                if (Boolean.parseBoolean(receiveMessageString)) {
                    Toast.makeText(ServiceActivity.this,"Have Message",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Toast.makeText(ServiceActivity.this,"Cannot Back Process"
        ,Toast.LENGTH_SHORT).show();

    }

    public void myCloseDrawer() {
        drawerLayout.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId()==R.id.itemSignOut) {

            finish();

            return true;
        }

        if (item.getItemId()==R.id.itemLogOut) {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();

            startActivity(new Intent(ServiceActivity.this,MainActivity.class));
            finish();

        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    private void createToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbarService);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titleToolbarString);
        getSupportActionBar().setSubtitle(subTitleToolbarString);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawerLayoutService);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this,
                drawerLayout,R.string.open,R.string.close);



    }

    private void findUserUidinTransport() {

        Log.d("31DecV1", "status ==> " + statusABoolean);

        if (statusABoolean) {

            Myconstan myConstant = new Myconstan();
            String[] fieldStrings = myConstant.getFieldTransportStrings();
            final String[] transportStrings = new String[fieldStrings.length];

            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference().child("Transport");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List list = new ArrayList();
                    int i = 0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        TransportModel transportModel = dataSnapshot1.getValue(TransportModel.class);
                        list.add(transportModel);

                        TransportModel transportModel1 = (TransportModel) list.get(i);

                        if (userUidString.equals(transportModel1.getUidUserString())) {

                            statusABoolean = false;
                            transportStrings[0] = transportModel1.getAddressString();
                            transportStrings[1] = transportModel1.getBranchString();
                            transportStrings[2] = transportModel1.getCompanyString();
                            transportStrings[3] = transportModel1.getFaxString();
                            transportStrings[4] = transportModel1.getHeadquarterString();
                            transportStrings[5] = transportModel1.getTelephoneString();
                            transportStrings[6] = transportModel1.getUidUserString();
                            transportStrings[7] = transportModel1.getStatusString();

                            for (int i1=0; i1<transportStrings.length; i1+=1) {
                                Log.d("31DecV1", "transportString[" + i1 + "] => " + transportStrings[i1]);
                            }

                            titleToolbarString = "Transport";
                            subTitleToolbarString = transportStrings[2];

                            createToolbar();

//                            Add Drawer Menu
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.contentDrawerMenuFragment, new DrawerMenuTransportFragment())
                                    .commit();


//                            Add Content Fragment
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.contentServiceFragment, TransportShowFragment.transportShowInstance(transportStrings))
                                    .commit();
                        }   // if

                        i = i + 1;

                    }   // for

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }   // if

    }

    private void findUserUidinSupplier() {

        final String tag = "14JanV1";

        if (statusABoolean) {

            Myconstan myConstant = new Myconstan();
            String[] fieldStrings = myConstant.getFieldSupplierStrings();
            final String[] supplyStrings = new String[fieldStrings.length];

            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference().child("Supplier");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List list = new ArrayList();
                    int i = 0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        SupplierModel supplierModel = dataSnapshot1.getValue(SupplierModel.class);
                        list.add(supplierModel);

                        SupplierModel supplierModel1 = (SupplierModel) list.get(i);

                        if (userUidString.equals(supplierModel1.getUidUserString())) {

                            statusABoolean = false;
                            supplyStrings[0] = supplierModel1.getAddressString();
                            supplyStrings[1] = supplierModel1.getBussinessString();
                            supplyStrings[2] = supplierModel1.getCompanyString();
                            supplyStrings[3] = supplierModel1.getFaxString();
                            supplyStrings[4] = supplierModel1.getHeadquartersString();
                            supplyStrings[5] = supplierModel1.getTelephoneString();
                            supplyStrings[6] = supplierModel1.getUidUserString();
                            supplyStrings[7] = supplierModel1.getStatusString();

                            titleToolbarString = "Supplier";
                            subTitleToolbarString = supplyStrings[2];

                            for (int i1=0;i1<supplyStrings.length;i1 += 1) {
                                Log.d(tag, "supplier[" + i1 + "] ==> " + supplyStrings[i1]);
                            }


                            createToolbar();

//                            Add Drawer Menu
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.contentDrawerMenuFragment, new DrawerMenuSupplierFragment())
                                    .commit();

                            ArrayList<String> stringArrayList = new ArrayList<>();
                            for (int i1=0;i1<supplyStrings.length;i1+=1) {
                                stringArrayList.add(supplyStrings[i1]);
                            }

                            saveLoginOnSharePreference(stringArrayList.toString());


//                            Add Fragment
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.contentServiceFragment, new SupplierShowFragment())
                                    .commit();

                        }   // if

                        i = i + 1;

                    }   //for


                }   // onDatachange

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }   // if

    }

    private void findUserUidinCustomer() {
        if (statusABoolean) {

            //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);

            Myconstan myConstant = new Myconstan();
            String[] fieldStrings = myConstant.getFieldCustomerStrings();
            final String[] customerStrings = new String[fieldStrings.length];

            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference().child("Customer");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List list = new ArrayList();
                    int timesAInt = 0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        CustomerModel customerModel = dataSnapshot1.getValue(CustomerModel.class);
                        list.add(customerModel);


                        CustomerModel customerModel1 = (CustomerModel) list.get(timesAInt);
                        Log.d(tag, "Name[" + timesAInt + "] ==> " + customerModel1.getNameString());
                        timesAInt = timesAInt + 1;

                        if (userUidString.equals(customerModel1.getUidUserString())) {
                            statusABoolean = false;
                            customerStrings[0] = customerModel1.getLastNameString();
                            customerStrings[1] = customerModel1.getNameString();
                            customerStrings[2] = customerModel1.getPhoneString();
                            customerStrings[3] = customerModel1.getUidUserString();
                            customerStrings[4] = customerModel1.getAvataString();
                            customerStrings[5] = customerModel1.getUrlImageString();

                            for (int i = 0; i < customerStrings.length; i += 1) {
                                Log.d(tag, "custom[" + i + "] ==> " + customerStrings[i]);
                            }

                            titleToolbarString = "Customer";
                            subTitleToolbarString = customerStrings[1] + " " + customerStrings[0];

                            createToolbar();

//                            Add Menu for Drawer
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.contentDrawerMenuFragment, new DrawerMenuCustomerFragment())
                                    .commit();

                            ArrayList<String> stringArrayList = new ArrayList<>();
                            for (int i=0;i<customerStrings.length;i+=1) {
                                stringArrayList.add(customerStrings[i]);
                            }

                            saveLoginOnSharePreference(stringArrayList.toString());

//                            Add Content of Fragment
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.contentServiceFragment, new CustomerShowFragment())
                                    .commit();

                        }   // if

                    }   // for

                    Log.d(tag, "Status ==> " + statusABoolean);

                }   // onDataChange

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }   // if
    }

    private void saveLoginOnSharePreference(String loginString) {

        SharedPreferences sharedPreferences = getSharedPreferences("LoginFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Login",loginString);
        editor.commit();

    }

    private void findUserUid() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userUidString = firebaseUser.getUid();
        String disPlayName = firebaseUser.getDisplayName();
        Log.d(tag, "at Service userUid ==> " + userUidString);
        Log.d(tag, "disPlayName ==> " + disPlayName);

    }


}   // Main Class