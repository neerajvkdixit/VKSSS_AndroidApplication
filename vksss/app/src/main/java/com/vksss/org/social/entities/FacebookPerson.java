package com.vksss.org.social.entities;

/**
 * Created by kany on 19/7/16.
 */
public class FacebookPerson {
    private String Name;
    private String dob;
    private String gender;
    private String profilePicUrl;
    private Long facebookId;
    private String emailId;
    private String profileName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getFacebookId() {
        return facebookId;
    }


    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


}
