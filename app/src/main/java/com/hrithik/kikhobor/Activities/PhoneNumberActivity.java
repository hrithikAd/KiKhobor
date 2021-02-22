package com.hrithik.kikhobor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.hrithik.kikhobor.R;
import com.hrithik.kikhobor.databinding.ActivityPhoneNumberBinding;
import com.rilixtech.CountryCodePicker;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding binding;
    FirebaseAuth auth;
    CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ccp

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(PhoneNumberActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        getSupportActionBar().hide();

        binding.phoneBox.requestFocus();

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneNumberActivity.this, OTPActivity.class);

                ccp.registerPhoneNumberTextView(binding.phoneBox);
                 intent.putExtra("phoneNumber", ccp.getFullNumberWithPlus());
                startActivity(intent);
            }
        });

    }
}