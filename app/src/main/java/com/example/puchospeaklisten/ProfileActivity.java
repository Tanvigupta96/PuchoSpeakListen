package com.example.puchospeaklisten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String phoneNumber;
    private TextView mobileNumber;
    private TextInputLayout nameEditText,ageEditText,hometownEditText,residenceEditText,languageEditText;
    private Spinner spinner;
    private String gender=" ",name=" ",age=" ",home=" ",residence=" ",language=" ";

    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference rootRef;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        rootNode = FirebaseDatabase.getInstance();
        rootRef =  FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();



        initializeFields();
        updateUserInfo();



        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }



    private void initializeFields() {
        nameEditText = findViewById(R.id.name);
        ageEditText = findViewById(R.id.age);
        hometownEditText=findViewById(R.id.hometown);
        residenceEditText = findViewById(R.id.residence);
        languageEditText = findViewById(R.id.language);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // gender = spinner.getSelectedItem().toString();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // get saved phone number
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);

        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

    }

    private void updateUserInfo() {
        name = nameEditText.getEditText().getText().toString();
        age = ageEditText.getEditText().getText().toString();
        home = hometownEditText.getEditText().getText().toString();
        residence =  residenceEditText.getEditText().getText().toString();
        language = languageEditText.getEditText().getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "please write your name...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "please write your age...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(home)){
            Toast.makeText(this, "please write your HomeTown...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(residence)){
            Toast.makeText(this, "please write your Residential Address...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(language)){
            Toast.makeText(this, "Mention Languages you know...", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "please mention your Gender...", Toast.LENGTH_SHORT).show();
        }

        else{
            HashMap<String,String> InfoMap = new HashMap<>();
            InfoMap.put("uid",currentUserId);
            InfoMap.put("name",name);
            InfoMap.put("age",age);
            InfoMap.put("gender",gender);
            InfoMap.put("hometown",home);
            InfoMap.put("residential place",residence);
            InfoMap.put("language",language);

        }




    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gender = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



