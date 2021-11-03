package com.company;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestParser {
    public Request getRequest() {
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
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            var g = new Gson();
            return g.fromJson(response.toString(), Request.class);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return new Request();

    }
}
