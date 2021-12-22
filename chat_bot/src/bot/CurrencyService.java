package bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CurrencyService {

    private final String urlString;

    public CurrencyService(String urlString) {
        this.urlString = urlString;
    }

    public Map<String, Double> getCurrency() throws IOException {
        URL url = new URL(urlString);

        JSONObject jsonObj = new JSONObject(IOUtils.toString(url, StandardCharsets.UTF_8)).getJSONObject("rates");

        return new Gson().fromJson(jsonObj.toString(), new TypeToken<Map<String, Double>>() {
        }.getType());
    }
}
