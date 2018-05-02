package com.dev.nathan.socialblock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private FloatingActionButton addPostBtn;

    private BottomNavigationView mainbottomNav;

    private HomeFragment homeFragment;
    private FriendsFragment notificationFragment;
    private ProfileFragment profileFragment;
    private NewPostFragment newPostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_logo);



        if(mAuth.getCurrentUser() != null) {

            mainbottomNav = findViewById(R.id.mainBottomNav);

            // FRAGMENTS
            homeFragment = new HomeFragment();
            notificationFragment = new FriendsFragment();
            profileFragment = new ProfileFragment();
            newPostFragment = new NewPostFragment();

            initializeFragment();
            disableShiftMode(mainbottomNav);
            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

                    switch (item.getItemId()) {

                        case R.id.bottom_action_home:

                            replaceFragment(homeFragment, currentFragment);
                            getSupportActionBar().setTitle(getString(R.string.bottom_home_text));
                            return true;

                        case R.id.bottom_action_profile:

                            replaceFragment(profileFragment, currentFragment);
                            getSupportActionBar().setTitle(getString(R.string.menu_title_profile));
                            return true;

                        case R.id.bottom_action_friends:

                            replaceFragment(notificationFragment, currentFragment);
                            getSupportActionBar().setTitle(getString(R.string.menu_title_friends));
                            return true;

                        case R.id.bottom_action_new_post:

                            replaceFragment(newPostFragment, currentFragment);
                            getSupportActionBar().setTitle(getString(R.string.menu_title_new_post));
                            return true;

                        case R.id.bottom_action_follower:

                            replaceFragment(notificationFragment, currentFragment);
                            getSupportActionBar().setTitle(getString(R.string.menu_title_follower));
                            return true;

                        default:
                            return false;


                    }

                }
            });




        }


    }
    //fixa os bottoes na navigation vieew button

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            sendToLogin();

        } else {

            current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){

                        if(!task.getResult().exists()){

                            Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                            startActivity(setupIntent);
                            finish();

                        }

                    } else {

                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();


                    }

                }
            });

        }

    }




    private void logOut() {

        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, homeFragment);
        fragmentTransaction.add(R.id.main_container, notificationFragment);
        fragmentTransaction.add(R.id.main_container, profileFragment);
        fragmentTransaction.add(R.id.main_container, newPostFragment);

        fragmentTransaction.hide(newPostFragment);
        fragmentTransaction.hide(notificationFragment);
        fragmentTransaction.hide(profileFragment);

        fragmentTransaction.commit();

    }

    private void replaceFragment(Fragment fragment, Fragment currentFragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(fragment == homeFragment){

            fragmentTransaction.hide(newPostFragment);
            fragmentTransaction.hide(profileFragment);
            fragmentTransaction.hide(notificationFragment);

        }

        if(fragment == profileFragment){

            fragmentTransaction.hide(newPostFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(notificationFragment);

        }

        if(fragment == notificationFragment){

            fragmentTransaction.hide(newPostFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);

        }
        if(fragment == newPostFragment){

            fragmentTransaction.hide(notificationFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);

        }
        fragmentTransaction.show(fragment);

        //fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }




}
