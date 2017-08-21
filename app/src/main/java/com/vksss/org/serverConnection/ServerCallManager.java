package com.vksss.org.serverConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by kany on 18/7/16.
 */
public class ServerCallManager {
    private static OkHttpClient client = new OkHttpClient();

    public static Response callServer(Request request){

        MediaType mediaType
                = MediaType.parse("application/json; charset=utf-8");

        String dataToSend = request.getDataToSend();

        String url = request.getServerURL();

        Response toReturnResponse = new Response();


        RequestBody body = RequestBody.create(mediaType, dataToSend);
        okhttp3.Request requestToSend = new okhttp3.Request.Builder().url(url)
                .post(body)
                .build();

        try {
            okhttp3.Response okhttp3Response = client.newCall(requestToSend).execute();
            toReturnResponse.setDataReceived(okhttp3Response.body().string());

        }catch (Exception e){
            toReturnResponse.setDataReceived(null);
        }

        return toReturnResponse;
    }


}
