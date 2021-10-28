package com.company;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {
    private static final String accessToken = "BQB1fU_U3KbsH5v2j-uCkWxD3AjRlOh2sIF6frh4utb-R1OhfxDNka0Pd-I7_Nc7iMg_QKpOdQTcxqRIX9bvjlVQCrFDW8rtKx6exV-ImyLqLnMVHdnS6Rs6XqfVzxecOqUdR_5CXHkYc4L4qavaZIGsAZVfgOiRHwm3dYHhnJL73jh0MzO-";
    
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();

    public static String searchArtists(String name) {
        try {
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(name).build();
            CompletableFuture<Paging<Artist>> pagingFuture = searchArtistsRequest.executeAsync();

            Paging<Artist> artistPaging = pagingFuture.join();

            System.out.println("Total: " + artistPaging.getItems()[0].getExternalUrls().get("spotify"));
            return artistPaging.getItems()[0].getExternalUrls().get("spotify");
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
            return "Error";
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
            return "Error";
        }
    }

}
