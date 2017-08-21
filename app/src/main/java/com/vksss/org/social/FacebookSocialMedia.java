package com.vksss.org.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vksss.org.R;
import com.vksss.org.social.entities.FacebookPerson;

import org.json.JSONObject;

import java.util.Arrays;


/**
 * Created by kany on 26/3/16.
 */
public class FacebookSocialMedia extends FragmentActivity implements socialMedia {

    CallbackManager callbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.facebooklogin);
        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
        LoginButton loginButton = (LoginButton) findViewById(R.id.loginbuttonFacebook);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        /*loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends","user_birthday",""));*/
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email","user_birthday"));

        final FacebookPerson facebookPerson = new FacebookPerson();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            facebookPerson.setName(profile.getName());
                            facebookPerson.setProfilePicUrl(profile.getProfilePictureUri(400, 400).toString());


                        }
                        //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            facebookPerson.setEmailId(object.getString("email"));
                                            facebookPerson.setGender(object.getString("gender"));
                                            facebookPerson.setProfileName(object.getString("name"));
                                            facebookPerson.setFacebookId(object.getLong("id"));

                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            //  e.printStackTrace();
                                        }

                                    }

                                });

                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.v("facebook error",exception.getMessage());
                        Toast.makeText(getBaseContext(), "Error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

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
}
