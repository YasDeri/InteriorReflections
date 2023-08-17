package com.ir.app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ir.app.R;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.Session;
import com.ir.sqlite.models.User;


public class SettingsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String uname = getArguments().getString("usrn");
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button x = view.findViewById(R.id.update);

        EditText p1 = view.findViewById(R.id.new_pass_edit), p2 = view.findViewById(R.id.confirm_pass_edit);
        EditText old = view.findViewById(R.id.old_pass_edit);


        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usr = UserHelper.getInstance().getUser(uname, old.getText().toString());
                if(usr == null || !p1.getText().toString().equals(p2.getText().toString()) || p1.getText().toString().length() < 4) {
                    if(usr == null) {
                        Toast.makeText(getActivity(), "Invalid Old Password!", Toast.LENGTH_SHORT).show();
                    }
                    else if(p1.getText().toString().length() < 4) {
                        Toast.makeText(getActivity(), "Length must be greater than 4", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Password confirmation failed", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    UserHelper.getInstance().changePass(uname, p1.getText().toString());
                    Toast.makeText(getActivity(), "Sucessfully changed!", Toast.LENGTH_SHORT).show();

                }


            }
        });
        return view;
    }

}
