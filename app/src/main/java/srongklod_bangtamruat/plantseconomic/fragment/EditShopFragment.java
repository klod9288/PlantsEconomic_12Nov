package srongklod_bangtamruat.plantseconomic.fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.ShopModel;

public class EditShopFragment extends Fragment{

    private String childEditString,uidLoginString;
    private ImageView imageView;
    private Button button;
    private EditText nameEditText,descriptionEditText,priceEditText, stockEditText;
    private String nameString,descriptionString,priceString, stockString,urlImageString,
            unitMoneyString,unitStockString;
    private boolean imageABoolean = false;
    private Uri uri;



    public static EditShopFragment editShopInstance(String keyString,String uidLoginString) {

        EditShopFragment editShopFragment = new EditShopFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KeyString",keyString);
        bundle.putString("UidLogin",uidLoginString);
        editShopFragment.setArguments(bundle);
        return editShopFragment;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get Value From Argument
        getValueFromArgument();

        initiaView();

        showView();

//        Back Controller
        backController();

//        Image Controller
        imageController();

//        Button Controller
        buttonController();


    }//Main Method

    private void buttonController() {
        Button button = getView().findViewById(R.id.btnAddShop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageABoolean) {
                    uploadImageToFirebase();
                } else {
                    nameString = nameEditText.getText().toString().trim();
                    descriptionString = descriptionEditText.getText().toString().trim();
                    priceString = priceEditText.getText().toString().trim();
                    stockString = stockEditText.getText().toString().trim();

//                Check String
                    Log.d("10AprilV1", "Name ==> "+nameString);
                    Log.d("10AprilV1", "Description ==> "+descriptionString);
                    Log.d("10AprilV1", "Price ==> "+priceString);
                    Log.d("10AprilV1", "Stock ==> "+stockString);
                    Log.d("10AprilV1", "ImageURL ==> "+urlImageString);

                    updateDatabaseFirebase();

                }




//                Edit Value




            }
        });

    }


    private void uploadImageToFirebase() {

        Random random = new Random();
        int i = random.nextInt(10000);
        final String nameImageString = uidLoginString + "-" + Integer.toString(i);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference()
                .child("ShopSupplier/" + nameImageString);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Log.d("9AprilV3", "Upload Success");
                findPathImage(nameImageString);




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.nomalDialog("Cannot Upload",e.toString());

            }
        });


    }

    private void updateDatabaseFirebase() {

        ShopModel shopModel = new ShopModel(nameString, descriptionString, priceString,
                stockString, urlImageString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("ShopSupplier")
                .child("Shop-"+uidLoginString)
                .child(childEditString);
        databaseReference.setValue(shopModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d("10AprilV1", "Edit Success");

                BackToShop();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("10AprilV1", "Edit Not Success ==>" + e.toString());


            }
        });


    }

    private void findPathImage(String nameImageString) {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("ShopSupplier/" + nameImageString)
        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                urlImageString = uri.toString();
                Log.d("9AprilV3","New Image Url ==> "+urlImageString);

                nameString = nameEditText.getText().toString().trim();
                descriptionString = descriptionEditText.getText().toString().trim();
                priceString = priceEditText.getText().toString().trim();
                stockString = stockEditText.getText().toString().trim();

//                Check String
                Log.d("10AprilV1", "Name ==> "+nameString);
                Log.d("10AprilV1", "Description ==> "+descriptionString);
                Log.d("10AprilV1", "Price ==> "+priceString);
                Log.d("10AprilV1", "Stock ==> "+stockString);
                Log.d("10AprilV1", "ImageURL ==> "+urlImageString);

                  updateDatabaseFirebase();


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            imageABoolean = true;
            uri = data.getData();

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap,300,300,false));



            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }

    private void imageController() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose Image"),1);

            }
        });
    }

    private void backController() {

        Button button = getView().findViewById(R.id.btnBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToShop();
            }
        });
    }

    private void BackToShop() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentServiceFragment, new ShopSupplierFragment())
                .commit();
    }

    private void showView() {

        button.setText("Edit Shop");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("ShopSupplier")
                .child("Shop-" + uidLoginString)
                .child(childEditString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ShopModel shopModel = dataSnapshot.getValue(ShopModel.class);

                String nameString = shopModel.getNameProductString();
                String descriptionString = shopModel.getDescriptionString();
                String priceString=shopModel.getPriceString();
                String stockString = shopModel.getStockString();
                urlImageString = shopModel.getUrlImagePathString();

                String[] strings=priceString.split("\\s+");
                String[] strings1=stockString.split("\\s+");

                if (strings.length >1) {

                    priceString = strings[0];
                    unitMoneyString = strings[1];
                    Log.d("19AprilV1", "unitMoney ==>" + unitMoneyString);

                }

                if (strings1.length > 1) {

                    stockString = strings1[0];
                    unitStockString = strings1[1];
                    Log.d("19AprilV1", "uintStock ==>" + unitStockString);

                }


                nameEditText.setText(nameString);
                descriptionEditText.setText(descriptionString);
                priceEditText.setText(priceString);
                stockEditText.setText(stockString);
                Picasso.with(getActivity())
                        .load(urlImageString)
                        .resize(350,350)
                        .into(imageView);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void initiaView() {

        imageView = getView().findViewById(R.id.imvAddShop);
        button = getView().findViewById(R.id.btnAddShop);
        nameEditText = getView().findViewById(R.id.edtName);
        descriptionEditText = getView().findViewById(R.id.edtDescription);
        priceEditText = getView().findViewById(R.id.edtPrice);
        stockEditText = getView().findViewById(R.id.edtStock);


    }

    private void getValueFromArgument() {

        childEditString = getArguments().getString("KeyString");
        uidLoginString = getArguments().getString("UidLogin");
        Log.d("9AprilV3", "childEditString ==>" + childEditString);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);

        return view;
    }
}//Main Class
