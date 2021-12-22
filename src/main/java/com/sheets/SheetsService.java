package com.sheets;

import com.database.Post;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class SheetsService {

    private final String APPLICATION_NAME;
    private final String SPREADSHEET_ID;
    private final File DATA_STORE_DIR = new java.io.File("CREDENTIALS_DIR");

    public List<Post> posts;

    private final Sheets sheetsService;

    public SheetsService(String APPLICATION_NAME, String SPREADSHEET_ID) throws Exception {
        this.APPLICATION_NAME = APPLICATION_NAME;
        this.SPREADSHEET_ID = SPREADSHEET_ID;
        sheetsService = getSheetsService();
        updateOrCreatePostsList(sheetsService);
    }


    private Credential authorize() throws Exception {
        InputStream in = SheetsService.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public Sheets getSheetsService() throws Exception {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private void appendPosts(ValueRange values, Sheets sheetsService) throws IOException {
        AppendValuesResponse appendValues = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "test_1", values)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true).execute();
    }

    public synchronized void updateOrCreatePostsList(Sheets sheetsService) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "test_1!A2:D")
                .execute();


        List<List<Object>> values = response.getValues();
        List<Post> postsList = new ArrayList<>();

        if (values != null && !values.isEmpty()) {
            for (List<Object> row : values)
                postsList.add(new Post(row.get(0), row.get(1), row.get(2), row.get(3)));
        }

        posts = postsList;

    }

    public Post getPostByRandom(Double value, String currency) throws IndexOutOfBoundsException {
        List<Post> unfilteredList = new ArrayList<>(this.posts);


        var list = unfilteredList.stream().filter(e -> (e.getMaxValue() >= value && e.getMinValue() <= value
                && e.getCurrency().equals(currency.toUpperCase()))).collect(Collectors.toList());
        Collections.shuffle(list);
        return list.get(0);


    }

    public static void main(String[] args) throws Exception {

        SheetsService sheetsService =
                new SheetsService(System.getenv("APPLICATION_NAME"),
                        System.getenv("SPREADSHEET_ID"));

        ValueRange appendBody = new ValueRange().setValues(Arrays.asList(new PostRepo().postsAsList));

        sheetsService.appendPosts(appendBody, sheetsService.sheetsService);

    }


}
