package com.example.mobooks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobooks.Books.BiographyActivity;
import com.example.mobooks.Books.BusinessActivity;
import com.example.mobooks.Books.HistoryActivity;
import com.example.mobooks.Books.LeadershipActivity;
import com.example.mobooks.Books.NovelsActivity;
import com.example.mobooks.Books.TechnologyActivity;
import com.example.mobooks.DataModels.OnlineBooksMode;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil mFirebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageReference;
    public static FirebaseAuth.AuthStateListener mAuthStateListener;
    public static ArrayList<OnlineBooksMode> onlineBooksSet;
    private static final int RC_SIGN_IN = 123;
    private static HomeActivity callerCheck;
    private static BusinessActivity callerBus;
    private static BiographyActivity callerBio;
    private static LeadershipActivity callerLea;
    private static TechnologyActivity callerTech;
    private static HistoryActivity callerHis;
    private static NovelsActivity callerNov;

    private FirebaseUtil() {
    }

    public static boolean isAdmin;

    //Home openRef Method
    public static void openFbReferenceHome(String ref, final HomeActivity callerActivityHom) {
        Log.d("HomeBooks: ", "Connection to home books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerCheck = callerActivityHom;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //This method populates BusinessActivity with business books from firebase database
    public static void openFbReferenceBus(String ref, final BusinessActivity callerActivityBus) {
        Log.d("BusinessBooks: ", "Connection to business books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerBus = callerActivityBus;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //Method populates BiographyActivity with biography books from firebase database
    public static void openFbReferenceBio(String ref, final BiographyActivity callerActivityBio) {
        Log.d("BiographyBooks: ", "Connection to biography books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerBio = callerActivityBio;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //Method populates LeadershipActivity with leadership books from firebase database
    public static void openFbReferenceLea(String ref, final LeadershipActivity callerActivityLea) {
        Log.d("LeadershipBooks: ", "Connection to leadership books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerLea = callerActivityLea;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //Method populates TechnologyActivity with technology books from firebase database
    public static void openFbReferenceTech(String ref, final TechnologyActivity callerActivityTech) {
        Log.d("TechBooks: ", "Connection to tech books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerTech = callerActivityTech;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //Method populates HistoryActivity with history books from firebase database
    public static void openFbReferenceHis(String ref, final HistoryActivity callerActivityHis) {
        Log.d("HistoryBooks: ", "Connection to history books");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerHis = callerActivityHis;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    //Method populates NovelsActivity with novels from firebase database
    public static void openFbReferenceNov(String ref, final NovelsActivity callerActivityNov) {
        Log.d("Novels: ", "Connection to novels");
        if (mFirebaseUtil == null) {
            mFirebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            callerNov = callerActivityNov;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.singIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                }
            };
            connectStorage();
        }
        onlineBooksSet = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    public static void singIn() {
        Log.d("SignIn: ", "Signing in the user");
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        callerCheck.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .setLogo(R.drawable.logo1)
                        .build(),
                RC_SIGN_IN);
    }

    private static void checkAdmin(String uId) {
        FirebaseUtil.isAdmin = false;
        DatabaseReference ref = mFirebaseDatabase.getReference().child("administrators")
                .child(uId);

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseUtil.isAdmin = true;
                Log.d("Admin: ", "You are an admin");
                callerCheck.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addChildEventListener(listener);
    }

    public static void attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public static void detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    public static void connectStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child("books");
    }
}
