package com.vksss.org.social;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vksss.org.R;
import com.vksss.org.fragments.Login;

/**
 * Created by V_NEERAJ on 8/20/2016.
 */
public class GoogleSocialMedia implements socialMedia {


    @Override
    public boolean authenticate() {
        return false;
    }

    @Override
    public boolean shareMedia(CustomMessage message) {
        return false;
    }

    @Override
    public boolean shareMedia(CustomMedia customMedia) {
        return false;
    }

    @Override
    public boolean shareMedia(CustomMessage message, CustomMedia customMedia) {
        return false;
    }

    public static void updateUI(String statusValue,Object context){
        if("Sign in".equalsIgnoreCase(statusValue) || "Sign out".equalsIgnoreCase(statusValue)){
            SignInButton googleSignInButton = (SignInButton) (((Login)context).getView().getRootView().findViewById(R.id.btn_googlesign_in));
            setGooglePlusButtonText(googleSignInButton,statusValue);

        }

    }

    public static void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    public static String getGooglePlusButtonText(SignInButton signInButton) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                return (String) tv.getText();

            }
        }
        return "";
    }


    public static void signOut(final GoogleApiClient googleApiClient, final Object context){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                        if(status.isSuccess()){
                            updateUI("Sign in",context);
                            /*Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            // [START_EXCLUDE]
                                            if(status.isSuccess()){
                                                updateUI("Sign in",context);
                                            }
                                            // [END_EXCLUDE]
                                        }
                                    });*/
                        }
                    }
                });
    }
}
