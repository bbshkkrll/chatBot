package bot;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Gson g = new Gson();

    public Map<String, Double> getRequest() {
        try {
            final String urlString = "https://www.cbr-xml-daily.ru/latest.js";
            URL urlObject = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();

            if (responseCode == 404) {
                throw new IllegalArgumentException();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Map<String, Object> request = g.fromJson(response.toString(), new TypeToken<Map<String, Object>>() {
            }.getType());

            return g.fromJson(String.valueOf(request.get("rates")), new TypeToken<Map<String, Double>>() {
            }.getType());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new HashMap<>();
    }
}
