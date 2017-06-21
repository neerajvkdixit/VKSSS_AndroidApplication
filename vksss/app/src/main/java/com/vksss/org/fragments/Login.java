package com.vksss.org.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.vksss.org.MainMenuActivity;
import com.vksss.org.R;
import com.vksss.org.social.FacebookSocialMedia;
import com.vksss.org.social.GoogleSocialMedia;
import com.vksss.org.social.entities.FacebookPerson;
import com.vksss.org.splashscreen.VkSplashScreen;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by kany on 24/3/16.
 */
public class Login extends android.support.v4.app.Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    ImageButton signInGmailButton = null;
    com.facebook.login.widget.LoginButton loginFbButton = null;
    CallbackManager callbackManager = null;
    private ProfileTracker mProfileTracker = null;
    private FacebookPerson facebookPerson = null;
    private SignInButton googleSignInButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_social, null);
        //signInGmailButton = (ImageButton) root.findViewById(R.id.signInWithGoogle);

        googleSignInButton = (SignInButton) root.findViewById(R.id.btn_googlesign_in);

        googleSignInButton.setOnClickListener(this);

        //signInGmailButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN)).requestEmail()
                .build();

        if(mGoogleApiClient == null){

            mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                    .enableAutoManage(this.getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .addApi(Plus.API)
                    .build();

            /*GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this.getContext());
            builder.enableAutoManage((FragmentActivity) this.getContext() *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*);
            builder.addApi(Auth.GOOGLE_SIGN_IN_API, gso);
            builder.addApi(Plus.API);
            builder.build();*/
        }

        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        googleSignInButton.setScopes(gso.getScopeArray());

        loginFbButton = (LoginButton) root.findViewById(R.id.login_button_Facebook);
        registerFbComponent();
        return root;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSocialMedia.setGooglePlusButtonText(googleSignInButton,"Sign out");
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                @Override
                public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                    Person person = loadPeopleResult.getPersonBuffer().get(0);

                    JSONObject toReturnResult = new JSONObject();
                    try {
                        if(person.hasName())
                            toReturnResult.put("name", person.getName().getGivenName());
                        if(person.hasGender()) {
                            if(person.getGender() == 0 )
                                toReturnResult.put("gender", "male");
                            else if(person.getGender() == 1)
                                toReturnResult.put("gender","female");
                            else
                                toReturnResult.put("gender","other");
                        }
                        if(person.hasBirthday())
                            toReturnResult.put("dob",person.getBirthday());
                        if(person.hasId())
                            toReturnResult.put("id",person.getId());
                        if(person.hasImage())
                            toReturnResult.put("",person.getImage().getUrl());
                      //  sendResultToLoginFragment(toReturnResult,100);

                        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("test",0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("IsUserLogin",true);
                        editor.putString("currentLoginSession","Google Account");
                        editor.putString("currentLoginDetails",toReturnResult.toString());
                        editor.commit();


                    }catch (Exception e){
                        //sendResultToLoginFragment(toReturnResult,101);
                    }finally {
                        loadPeopleResult.release();
                    }

                }
            });

            /*Person person  = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            Log.i(TAG, "--------------------------------");
            Log.i(TAG, "Display Name: " + person.getDisplayName());
            Log.i(TAG, "Gender: " + person.getGender());
            Log.i(TAG, "AboutMe: " + person.getAboutMe());
            Log.i(TAG, "Birthday: " + person.getBirthday());
            Log.i(TAG, "Current Location: " + person.getCurrentLocation());
            Log.i(TAG, "Language: " + person.getLanguage());*/


        } else {
            Toast.makeText(this.getActivity().getApplicationContext(),"Unsuccessful Login with gmail ",Toast.LENGTH_LONG);
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }


    public void getFacebookProfileInformation(Profile profile ){
        facebookPerson = new FacebookPerson();
        if (profile != null) {
            facebookPerson.setName(profile.getName());
            facebookPerson.setProfilePicUrl(profile.getProfilePictureUri(400, 400).toString());
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
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,gender,email");
            request.setParameters(parameters);
            request.executeAsync();

        }


    }

    private void registerFbComponent() {

        //loginFbButton.setReadPermissions("user_friends");

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email","user_birthday"));

        //loginFbButton.setPublishPermissions(Arrays.asList("public_profile", "user_friends", "email","user_birthday"));

        loginFbButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email","user_birthday"));




        // If using in a fragment
        loginFbButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginFbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getActivity().getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

                Profile profile = null;

                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();
                            getFacebookProfileInformation(profile2);
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                    getFacebookProfileInformation(profile);
                }





            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getActivity().getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getActivity().getApplicationContext(),"error "+exception.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("error->",exception.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 101)
            return;
        else if(resultCode == 100){

        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    public void initiateGoogleSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.signInWithGoogle :
                Toast.makeText(getActivity().getApplicationContext(),"button clicked",Toast.LENGTH_LONG).show();

                Intent startActivityIntent = new Intent(this.getActivity(),com.vksss.org.social.SignInActivity.class);
                startActivityForResult(startActivityIntent,4);
               // Intent startActivityIntent = new Intent(this.getActivity(), FacebookSocialMedia.class);
               // startActivity(startActivityIntent);



                break;*/
            case R.id.btn_googlesign_in:

                String googleSignInButtonText = GoogleSocialMedia.getGooglePlusButtonText(googleSignInButton);
                try {
                    if ("Sign in".equals(googleSignInButtonText)) {
                        initiateGoogleSignIn();
                    } else if ("Sign out".equals(googleSignInButtonText)) {
                        GoogleSocialMedia.signOut(mGoogleApiClient, this);
                    }
                }catch (Exception e){
                    Log.e("social Media Operation->","failed");
                }


        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
