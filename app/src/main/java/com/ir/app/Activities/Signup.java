package com.ir.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ir.app.R;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.DatabaseHelper;
import com.ir.sqlite.models.User;

public class Signup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void backClick(View view) {
        finish();
    }

    public void register(View view) {
        EditText fn = findViewById(R.id.edit_fname),
        ln = findViewById(R.id.edit_lname),
        em = findViewById(R.id.edit_email),
        ps = findViewById(R.id.edit_pass1),
        rt = findViewById(R.id.edit_retype_pass),
        ad = findViewById(R.id.edit_address);

        if(fn.getText().toString().matches("") ||
                ln.getText().toString().matches("") ||
                em.getText().toString().matches("") ||
                ps.getText().toString().matches("") ||
                rt.getText().toString().matches("") ||
                ad.getText().toString().matches("")
        ) {
            Toast.makeText(getBaseContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
        }
        else {

            if(ps.getText().toString().equals(rt.getText().toString())) {
                if(ps.getText().toString().length() > 4) {
                    if(UserHelper.getInstance().alreadyExist(em.getText().toString())) {
                        Toast.makeText(getBaseContext(), "This username is not available!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User usr = new User(fn.getText().toString(), ln.getText().toString(), ad.getText().toString(), em.getText().toString(),
                                ps.getText().toString(), "Customer");

                        UserHelper tmp = UserHelper.getInstance();
                        tmp.writeUser(usr);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "Password length should be greater than 4", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getBaseContext(), "Password doesnt match with confirmation!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
