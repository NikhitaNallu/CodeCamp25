package com.example.cyhunt;

import com.google.firebase.auth.FirebaseUser;

public class UserSession {
    private static FirebaseUser currentUser;

    public static void setCurrentUser(FirebaseUser user){
        currentUser = user;
    }

    public static FirebaseUser getCurrentUser() {
        return currentUser;
    }
}
