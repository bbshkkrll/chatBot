package bot.database;

public class Post {
    private String title;
    private String text;
    private double minValue;
    private double maxValue;
    private String currency;

    public Post() {

    }

    public Post(String text, String subText) {
        this.title = text;
        this.text = subText;
    }

    public Post(String text, String subText, double minValue, double maxValue, String currency) {
        this.title = text;
        this.text = subText;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
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

}
