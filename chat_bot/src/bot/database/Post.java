package bot.database;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
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
