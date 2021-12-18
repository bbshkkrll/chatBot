package bot.database;

import java.util.List;

public class Post {
    private String title;
    private double minValue;
    private double maxValue;
    private String currency;

    public Post() {

    }

    public Post(String title) {
        this.title = title;
    }

    public Post(String title, double minValue, double maxValue, String currency) {
        this.title = title;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currency = currency;
    }

    public Post(Object title, Object minValue, Object maxValue, Object currency) {
        this.title = title.toString();
        this.minValue = Double.parseDouble(minValue.toString());
        this.maxValue = Double.parseDouble(maxValue.toString());
        this.currency = currency.toString();
    }

    public String getTitle() {
        return title;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Object> getAsList() {
        return List.of(title, minValue, maxValue, currency);
    }

}
