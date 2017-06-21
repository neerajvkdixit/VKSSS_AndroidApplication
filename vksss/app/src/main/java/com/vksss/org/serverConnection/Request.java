package com.vksss.org.serverConnection;

/**
 * Created by kany on 18/7/16.
 */
public class Request {
    private String dataToSend;
    private String serverURL;
    private String serverHitPoint;

    public String getServerHitPoint() {
        return serverHitPoint;
    }

    public void setServerHitPoint(String serverHitPoint) {
        this.serverHitPoint = serverHitPoint;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getDataToSend() {
        return dataToSend;
    }

    public void setDataToSend(String dataToSend) {
        this.dataToSend = dataToSend;
    }
}
