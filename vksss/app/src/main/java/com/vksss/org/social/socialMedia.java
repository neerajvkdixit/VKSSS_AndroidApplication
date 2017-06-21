package com.vksss.org.social;

/**
 * Created by kany on 26/3/16.
 */
public interface socialMedia {
    public boolean authenticate();
    public boolean shareMedia(CustomMessage message);
    public boolean shareMedia(CustomMedia customMedia);
    public boolean shareMedia(CustomMessage message,CustomMedia customMedia);

}
