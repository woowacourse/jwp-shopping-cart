package woowacourse.shoppingcart.domain;

public class Image {

    private final String url;
    private final String alt;

    public Image(String url, String alt) {
        this.url = url;
        this.alt = alt;
    }

    public String getUrl() {
        return url;
    }

    public String getAlt() {
        return alt;
    }
}
