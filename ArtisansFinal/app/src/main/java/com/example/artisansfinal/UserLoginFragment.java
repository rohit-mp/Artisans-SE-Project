package com.example.artisansfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserLoginFragment extends Fragment {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private  Button SignUp;
    private TextView forgotpassword;
    private Intent intent;
    private String userType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        intent = getActivity().getIntent();

        //userType = intent.getStringExtra("userType");
        final View view=inflater.inflate(R.layout.activity_login, container, false);



        Name = (EditText) view.findViewById(R.id.login_page_et_user_name);
        Password = (EditText) view.findViewById(R.id.login_page_et_user_password);
        Login = (Button) view.findViewById(R.id.login_page_button_user_login);
        //userRegistration=(Button)findViewById(R.id.login_page_button_user_registration);
        SignUp=(Button) view.findViewById(R.id.login_page_button_user_registration);
        forgotpassword = (TextView) view.findViewById(R.id.login_page_tv_user_forgotpassword);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            if(user.getPhoneNumber().length() == 0) {
                Log.d("HERE1",user.getEmail());
                Intent intent1 = new Intent(new Intent(getContext(), UserHomePageActivity.class));
                //Log.d("HERE1",Name.getText().toString().trim());
                startActivity(intent1);
                getActivity().finish();
            }
//            else {
//                Intent intent1 = new Intent(new Intent(getContext(), ArtisanHomePageActivity.class));
//                intent1.putExtra("userType", "a");
//                startActivity(intent1);
//            }
        }
//        userRegistration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(),RegistrationActivity.class));
//            }
//        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PasswordActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (Password.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                } else {
                    validate(Name.getText().toString(), Password.getText().toString());
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RegistrationActivity.class));
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

    }

    private void validate(String userName, String userPassword) {
        progressDialog.setMessage("Please wait while we Log you in");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                    if(userType.equals("u")) {

                    Intent intent1 = new Intent(getContext(), UserHomePageActivity.class);
                    getActivity().finish();
                    startActivity(intent1);

//                    }
//                    else {
//                        Intent intent1 = new Intent(new Intent(getContext(), ArtisanHomePageActivity.class));
//                        intent1.putExtra("userType", userType);
//                        startActivity(intent1);
//                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }





}
