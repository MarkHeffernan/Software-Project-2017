package markheffernan.safeeat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText mNameTF;
    private EditText mAllergyTf;
    private EditText mEmailTf;
    private EditText mPasswordTf;

    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProg;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProg= new ProgressDialog(this);

        mNameTF = (EditText) findViewById(R.id.NameTf);
        mAllergyTf = (EditText) findViewById(R.id.AllergyTF);
        mEmailTf = (EditText) findViewById(R.id.EmailTf);
        mPasswordTf = (EditText) findViewById(R.id.PasswordTf);
        mRegisterBtn = (Button) findViewById(R.id.RegisterBtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser() {
        final String name = mNameTF.getText().toString().trim();
        final String allergy = mAllergyTf.getText().toString().trim();
        String email = mEmailTf.getText().toString().trim();
        String password = mPasswordTf.getText().toString().trim();


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(allergy) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProg.setMessage("Registering User");
            mProg.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference currentUser = mDatabase.child(user_id);

                        currentUser.child("name").setValue(name);
                        currentUser.child("allergy").setValue(allergy);

                        mProg.dismiss();





                    }
                }
            });

        }
    }
}
