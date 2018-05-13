package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;
import srongklod_bangtamruat.plantseconomic.utility.ShopModel;
import srongklod_bangtamruat.plantseconomic.utility.ShopSupplierAdapter;

public class ListShopWhereByCategoryFragment extends Fragment {

    private int indexAnInt;
    private ArrayList<String> nameShopStringArrayList, nameProductStringArrayList;
    private ListView listView;
    private String mainChildString = "ShopSupplier",categoryString;
    private ArrayList<String> nameStringArrayList, descriptionStringArrayList, priceStringArrayList,
            imageStringArrayList, stockStringArrayList;

    private int timesDetail = 0;
    private ArrayList<String> myNameProductStringArrayList;


    public static ListShopWhereByCategoryFragment listShopWhereByCategoryInstance(int indexInt) {

        ListShopWhereByCategoryFragment listShopWhereByCategoryFragment = new ListShopWhereByCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Index", indexInt);
        listShopWhereByCategoryFragment.setArguments(bundle);
        return listShopWhereByCategoryFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        SetUp ArrayList
        setUpArrayList();

//        Show Category
        showCategory();

//        Create ListView
        createListView();


    }//MainMethod

    private void setUpArrayList() {

        nameProductStringArrayList = new ArrayList<>();
        nameStringArrayList = new ArrayList<>();
        descriptionStringArrayList = new ArrayList<>();
        priceStringArrayList = new ArrayList<>();
        imageStringArrayList = new ArrayList<>();
        stockStringArrayList = new ArrayList<>();
        myNameProductStringArrayList = new ArrayList<>();

    }

    private void createListView() {

        listView = getView().findViewById(R.id.listShowShop);

        nameShopStringArrayList = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(mainChildString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    nameShopStringArrayList.add(dataSnapshot1.getKey());


                }//for

                Log.d("28AprilV2", "nameShopArrayList ==> " + nameShopStringArrayList.toString());
                findProduct();



            }//onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }//createListView

    private void findProduct() {

        Log.d("29AprilV2", "ชื่อร้านค้า" + nameShopStringArrayList);

        MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
        String[] nameShopStrings = myChangeArrayListToArray.myArray(nameShopStringArrayList.toString());

        for (final String nameShopString : nameShopStrings) {

            Log.d("29AprilV2", "nameShop ==> " + nameShopString);

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference()
                    .child(mainChildString)
                    .child(nameShopString);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Log.d("29AprilV3", "ชื่อร้าน ==> " + nameShopString);

                        nameProductStringArrayList.add(dataSnapshot1.getKey());

                        findDetail(nameShopString,dataSnapshot1.getKey().toString());

                        Log.d("29AprilV3", "ชื่อสินค้า ==> " + nameProductStringArrayList);

                    }//for

                    Log.d("29AprilV6", "nameString ==> " + nameStringArrayList);

                }//on Data Change

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }//for



    }//find Product

    private void findDetail(final String nameShopString, final String nameProductString) {

        timesDetail += 1;

        Log.d("29AprilV4", "nameShop ==> " + nameShopString);
        Log.d("29AprilV4", "nameProduct ==> " + nameProductString);
        Log.d("29AprilV4", "timesDetail ==> " + timesDetail);

        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference()
                    .child(mainChildString)
                    .child(nameShopString)
                    .child(nameProductString);

            final int[] ints = new int[]{0};
            final List list = new ArrayList();
            final int[] amountProductInt = {0};

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    amountProductInt[0] = (int) dataSnapshot.getChildrenCount();

                    Log.d("29AprilV5", "ร้าน ==> "+nameShopString);
                    Log.d("29AprilV5", "สินค้า ==> " + nameProductString);
                    Log.d("29AprilV5", "dataSnapshot ==> " + dataSnapshot.toString());

                    Map map = (Map) dataSnapshot.getValue();
                    String nameString = String.valueOf(map.get("nameProductString"));

                    Log.d("29AprilV6", "nameString ==> "+nameString);

                    if (categoryString.equals(String.valueOf(map.get("categoryString")))) {


                        myNameProductStringArrayList.add(dataSnapshot.getKey().toString());

                        nameStringArrayList.add(nameString);
                        descriptionStringArrayList.add(String.valueOf(map.get("descriptionString")));
                        priceStringArrayList.add(String.valueOf(map.get("priceString")));
                        imageStringArrayList.add(String.valueOf(map.get("urlImagePathString")));
                        stockStringArrayList.add(String.valueOf(map.get("stockString")));

                    }




                    Log.d("29AprilV6", "ชื่อสินค้าทั้งหมด ==> " + nameStringArrayList);

                    MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());

                    try {

                        ShopSupplierAdapter shopSupplierAdapter = new ShopSupplierAdapter(getActivity(),
                                myChangeArrayListToArray.myArray(nameStringArrayList.toString()),
                                myChangeArrayListToArray.myArray(descriptionStringArrayList.toString()),
                                myChangeArrayListToArray.myArray(priceStringArrayList.toString()),
                                myChangeArrayListToArray.myArray(stockStringArrayList.toString()),
                                myChangeArrayListToArray.myArray(imageStringArrayList.toString()));
                        listView.setAdapter(shopSupplierAdapter);

                        Log.d("12MayV1", "ชื่อ Child  ที่ถูกเลือก cat ==> " + myNameProductStringArrayList.toString());

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Log.d("12MayV1", "You Click ==> " + myNameProductStringArrayList.get(position));
                                confirmAlertDialog(myNameProductStringArrayList.get(position));


                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }//onData Change

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });//



        } catch (Exception e) {
            Log.d("29AprilV4", "e ==> "+e.toString());
        }


    }//Find Detail

    private void confirmAlertDialog(String nameProductString) {

        Log.d("12MayV2", "ชื่อสินค้าที่ถูกเลือก ==> " + nameProductString);

        final String supplierString = nameProductString.substring(0, (nameProductString.length() - 5));
        Log.d("12MayV2", "supplier ==> " + supplierString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Supplier")
                .child(supplierString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();

                String companyString = String.valueOf(map.get("companyString"));
                String businessString = String.valueOf(map.get("bussinessString"));
                String headString = String.valueOf(map.get("headquartersString"));

                showAlertDialog(supplierString, companyString,businessString, headString);


            }//OnDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showAlertDialog(final String supplierString, String companyString,
                                 String businessString, String headString) {

        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_action_alert);
            builder.setTitle("Want To Sent Message ?");
            builder.setMessage("company = " + companyString + "\n"
                    + "business = " + businessString + "\n" + "HeadQuester = " + headString);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            builder.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    moveToSendMessage(supplierString);


                }
            });
            builder.show();

        } catch (Exception e) {



        }
    }

    private void moveToSendMessage(String supplierString) {

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentServiceFragment,
                        MessageCustomerFragment.messageCustomerInstance(supplierString))
                .addToBackStack(null)
        .commit();

    }

    private void createArrayListForShow() {

        for (int y=0;y<nameShopStringArrayList.size();y+=1) {

            for (int i=0;i<nameProductStringArrayList.size();i+=1) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference()
                        .child(mainChildString)
                        .child(nameShopStringArrayList.get(y))
                        .child(nameProductStringArrayList.get(i));

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int[] ints = new int[]{0};

                        List stringArrayList = new ArrayList<>();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            try {

                                ShopModel shopModel = dataSnapshot1.getValue(ShopModel.class);
                                stringArrayList.add(shopModel);

                                ShopModel shopModel1 = (ShopModel) stringArrayList.get(ints[0]);

                                if (categoryString.equals(shopModel1.getCategoryString())) {

                                    nameStringArrayList.add(shopModel1.getNameProductString());


                                }//if

                                ints[0] += 1;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }//for


                        Log.d("28AprilV3", "Name Product ==> " + nameStringArrayList);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }//for

        }//for


    }//createListForShop

    private void showCategory() {

        indexAnInt = getArguments().getInt("Index", 0);
        Log.d("28AprilV1", "Receive Index ==> " + indexAnInt);

        TextView textView = getView().findViewById(R.id.txtTitle);
        Myconstan myconstan = new Myconstan();
        String[] strings = myconstan.getCategoryShopStrings();
        categoryString = strings[indexAnInt];
        textView.setText(strings[indexAnInt]);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_shop_category, container, false);
        return view;


    }//CreateView


}//Main Class
