package bot.sheets;

import bot.database.Post;

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
import java.security.GeneralSecurityException;
import java.util.*;

public class SheetsService {

    private final String APPLICATION_NAME;
    private final String SPREADSHEET_ID;
    private static final File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/2/sheets.googleapis.com-java-quickstart.json");

    private List<Post> posts;

    public SheetsService(String APPLICATION_NAME, String SPREADSHEET_ID) throws GeneralSecurityException, IOException {
        this.APPLICATION_NAME = APPLICATION_NAME;
        this.SPREADSHEET_ID = SPREADSHEET_ID;
        this.posts = createPostsList(getSheetsService());
    }


    private Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = SheetsService.class.getResourceAsStream("credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");
    }

    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
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

    private List<Post> createPostsList(Sheets sheetsService) {
        ValueRange response = new ValueRange();
        try {
            response = sheetsService.spreadsheets().values()
                    .get(SPREADSHEET_ID, "test_1!A2:D")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> values = response.getValues();
        List<Post> postsList = new ArrayList<>();
        if (values.isEmpty()) {
            //throw new EmptyListException("No data found!");
            System.out.println("No data found");
        } else {
            for (List<Object> row : values)
                postsList.add(new Post(row.get(0), row.get(1), row.get(2), row.get(3)));
        }
        return postsList;
    }

    public Post getPostByRandom(Double value, String currency) throws ArrayIndexOutOfBoundsException {
        List<Post> unfilteredList = new ArrayList<>(posts);

        Collections.shuffle(unfilteredList);

        return unfilteredList.stream().filter(e -> (e.getMaxValue() >= value && e.getMinValue() <= value
                && e.getCurrency().equals(currency.toUpperCase()))).toList().get(0);

    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        SheetsService sheetsService = new SheetsService("Currency Daily",
                "1GRsHCqfjalUtgxqCe60R98W_En3VG8lSNCi9ZwBJDkE");


//        var appendBody = new ValueRange().setValues(Arrays.asList(new PostRepo().postsAsList));
//
//        sheetsService.appendPosts(appendBody, sheetsService.sheetsService);

    }


}
