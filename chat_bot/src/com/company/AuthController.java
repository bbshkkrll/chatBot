package com.company;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.checkerframework.framework.qual.RequiresQualifier;
import org.glassfish.jersey.process.internal.RequestScoped;

import java.net.URI;

public class AuthController {
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback");
    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("7d4b9f4af6124f6a89ac20c3c56a9d88")
            .setClientSecret("0b3a15f2cc00465cad9501677b9fe5cd")
            .setRedirectUri(redirectUri)
            .build();

    public static String spotifyLogin(){
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read")
                .response_type("code")
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }


    public String getSpotifyUserCode(String userCode){
        code = userCode;
        return code;
    }
}
