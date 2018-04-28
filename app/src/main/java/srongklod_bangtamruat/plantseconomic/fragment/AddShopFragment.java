package srongklod_bangtamruat.plantseconomic.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;
import srongklod_bangtamruat.plantseconomic.utility.ShopModel;

public class AddShopFragment extends Fragment{

    private boolean aBoolean = true;
    private ImageView imageView;
    private Uri uri;
    private String nameString,descriptionString,priceString,
            stockString,displayString,uidLoginString,nameImageString,
            urlImageString,unitMoneyString,unitStockString,categoryString;

    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find Login
        findLogin();

//        Create Spinner
        createSpinner();

//        Create Category
        createCategory();

//        Image Controller
        imageController();

//        Button Controller
        buttonController();

    }//Main Method

    private void createCategory() {


        Spinner spinner = getView().findViewById(R.id.spinnerCategory);

        Myconstan myconstan = new Myconstan();

        final String[] strings=myconstan.getCategoryShopStrings();
        categoryString = strings[0];


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,myconstan.getCategoryShopStrings());

        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryString = strings[position];
                Log.d("28AprilV1", "Category Index x ==> " + categoryString);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                categoryString = strings[0];


            }
        });

    }

    private void createSpinner() {

        Spinner moneySpinner = getView().findViewById(R.id.spinnerMoney);
        Spinner stockSpinner = getView().findViewById(R.id.spinnerStock);

        Myconstan myconstan = new Myconstan();
        final String[] moneyStrings = myconstan.getUnitMoneyStrings();
        final String[] stockStrings = myconstan.getUnitStockStrings();

        unitMoneyString = moneyStrings[0];
        unitStockString = stockStrings[0];




        ArrayAdapter<String> moneyStringArrayAdapter = new ArrayAdapter<String>(getActivity()
        ,android.R.layout.simple_list_item_1,moneyStrings);

        ArrayAdapter<String> stockStringArrayAdapter = new ArrayAdapter<String>(getActivity()
        ,android.R.layout.simple_list_item_1,stockStrings);

        moneySpinner.setAdapter(moneyStringArrayAdapter);
        stockSpinner.setAdapter(stockStringArrayAdapter);

        moneySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                unitMoneyString = moneyStrings[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                unitMoneyString = moneyStrings[0];


            }
        });

        stockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                unitStockString = stockStrings[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                unitStockString = stockStrings[0];

            }
        });

    }

    private void findLogin() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

       displayString = firebaseUser.getDisplayName();
       uidLoginString = firebaseUser.getUid();

        Log.d("5AprilV1", "display ==>" + displayString);
        Log.d("5AprilV1", "uidLogin ==>" + uidLoginString);

    }

    private void buttonController() {

        Button button = getView().findViewById(R.id.btnAddShop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Please Wait ...");
                progressDialog.setMessage("Few In Minus Continuous Upload Data");
                progressDialog.show();


                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText descriptionEditText = getView().findViewById(R.id.edtDescription);
                EditText priceEditText = getView().findViewById(R.id.edtPrice);
                EditText stockEditText = getView().findViewById(R.id.edtStock);

                nameString = nameEditText.getText().toString().trim();
                descriptionString = descriptionEditText.getText().toString().trim();
                priceString = priceEditText.getText().toString().trim();
                stockString = stockEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (aBoolean) {
                    myAlert.nomalDialog(getString(R.string.title_Choose_image),
                            getString(R.string.message_choose_image));
                    progressDialog.dismiss();

                } else if (checkSpace()) {
                    myAlert.nomalDialog(getString(R.string.title_have_space),
                            getString(R.string.massage_have_space));
                    progressDialog.dismiss();

                } else {
//                    Upload Image
                    Random random = new Random();
                    int i = random.nextInt(10000);

                    nameImageString = uidLoginString + "-" + Integer.toString(i);

                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReference()
                            .child("ShopSupplier/" + nameImageString);



                    storageReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Log.d("5AprilV1", "Upload Image Success");

                            findMyImagePath();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d("5AprilV1", "Cannot UpLoad Image e ==>" + e.toString());

                        }
                    });


                } //if

            }
        });

    }

    private void findMyImagePath() {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        final String[] strings = new String[1];

        storageReference.child("ShopSupplier/"+nameImageString)
        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                strings[0] = uri.toString();
                urlImageString = strings[0];

                Log.d("5AprilV1", "url ==>" + urlImageString);

                updateNewProductToShop();

            }
        });


    }

    private void updateNewProductToShop() {

        Log.d("5AprilV2","NameProduct ==>"+nameString);
        Log.d("5AprilV2","Description ==>"+descriptionString);
        Log.d("5AprilV2","price ==>"+priceString);
        Log.d("5AprilV2","stock ==>"+stockString);
        Log.d("5AprilV2", "urlPath ==>" +urlImageString);

        priceString = priceString + " " + unitMoneyString;
        stockString = stockString + " " + unitStockString;

        ShopModel shopModel = new ShopModel(nameString,categoryString, descriptionString,
                priceString,stockString,urlImageString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("ShopSupplier")
                .child("Shop-"+uidLoginString);
        databaseReference.child(nameImageString).setValue(shopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentServiceFragment, new ShopSupplierFragment())
                            .commit();
                    progressDialog.dismiss();

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Please Try Again Cannot cUpload"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean checkSpace() {
        return nameString.isEmpty() ||
                descriptionString.isEmpty()||
                priceString.isEmpty()||
                stockString.isEmpty();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==getActivity().RESULT_OK) {

            aBoolean = false;

            uri = data.getData();


            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void imageController() {


        imageView = getView().findViewById(R.id.imvAddShop);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose Image"), 1);

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);

        return view;
    }
}//Main Class
