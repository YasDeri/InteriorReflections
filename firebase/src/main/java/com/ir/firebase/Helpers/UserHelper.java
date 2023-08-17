package com.ir.firebase.Helpers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ir.sqlite.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHelper {
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private List<User> users = new ArrayList<>();

    private static UserHelper obj = null;

    private UserHelper() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");
    }

    public List<User> getUsers(String type) {
        List<User> tmp = new ArrayList<>();
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getType().equals(type)) {
                tmp.add(users.get(i));
            }
        }
        return tmp;
    }

    public void removeuser(final User x) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User i = keyNode.getValue(User.class);
                    if(i.getUsername().equals(x.getUsername())) {
                        ref.child(keyNode.getKey()).removeValue();
                        users.remove(i);
                        //readUsers();
                    }
                }
                //adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void changeType(final User x, final String type) {
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(x.getUsername())) {
                users.get(i).setType(type);
            }
        }
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User i = keyNode.getValue(User.class);
                    if(i.getUsername().equals(x.getUsername())) {
                        ref.child(keyNode.getKey()).child("type").setValue(type);
                        //users.remove(i);
                        //readUsers();
                    }
                }
                //adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static UserHelper getInstance()
    {
        if(obj == null) {
            obj = new UserHelper();
        }
        return obj;
    }

    public boolean validateUser(final String username, final String pass) {

        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username, String pass) {
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(pass)) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getUser(String username) {
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public boolean alreadyExist(String username) {
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void changePass(final String username, final String newpass) {
        for(int i=0; i<users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                users.get(i).setPassword(newpass);
            }
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User i = keyNode.getValue(User.class);
                    if(i.getUsername().equals(username)) {
                        ref.child(keyNode.getKey()).child("password").setValue(newpass);
                        //users.remove(i);
                        //readUsers();
                    }
                }
                //adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readUsers() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    users.add(user);
                }
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readUsersEx(final List<User> usrs) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usrs.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    usrs.add(user);
                }
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeUser(User x) {
        DatabaseReference usersRef = ref;
//        Map<String, User> users = new HashMap<>();
//        users.put(x.getUsername(), x);
        usersRef.child(x.getUsername()).setValue(x);
    }
}
