package com.company;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchArtistsExample {
    private static final String accessToken = "BQALXeaZIM5Cj-0gaifbgSw6E7ebZQTblwWeqRQZ7xEABRfMjtHhR6DPYp2baQyLhj_6LHpM_tBQ4xkFpB7bvFzX1wxgEsxir2soey1PgNYsdLsn10Qr92yD6ytGHs-nLO007z_nly0-fXV0LNViqTIPMsAy5pMChOiFFvRFhw";
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
