package com.example.task_onboard_homework.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.task_onboard_homework.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private String verificationID;

    private EditText editPhone;
    private Button send;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private EditText editCode;
    private Button verify;
    private TextView countryPhoneCode;
    private LottieAnimationView lottieAnimationRegistration, lottieAnimationDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        countryPhoneCode = findViewById(R.id.text_countryPhoneCode);
        lottieAnimationRegistration = findViewById(R.id.lottieAnimation_registrationPhone);
        lottieAnimationDesign = findViewById(R.id.lottieAnimation_design);

        editPhone = findViewById(R.id.edit_Phone);
        send = findViewById(R.id.button_send);

        editCode = findViewById(R.id.edit_Code);
        verify = findViewById(R.id.button_verify);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("Phone", "onCodeSent");
                verificationID = s;
                lottieAnimationRegistration.setVisibility(View.GONE);
                lottieAnimationDesign.setAnimation(R.raw.enter_code);
                editCode.setVisibility(View.VISIBLE);
                verify.setVisibility(View.VISIBLE);
            }
        };
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {

                            Log.e("Phone", "Error = " + task.getException().getMessage());
                            Toast.makeText(PhoneActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view) {
        String phone =
                countryPhoneCode.getText().toString().trim() + editPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (phone, 60, TimeUnit.SECONDS, this, callbacks);
        Log.e("anim", "PhoneNumber = " + phone);
        editPhone.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
        countryPhoneCode.setVisibility(View.GONE);
        lottieAnimationRegistration.setAnimation(R.raw.loading);
        lottieAnimationDesign.setVisibility(View.GONE);
    }

    public void verifyClick(View view) {
        String code = editCode.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            editCode.requestFocus();
            return;
        }
        verifyCode(code);
        finish();
    }
}
