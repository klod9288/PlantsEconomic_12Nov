package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
