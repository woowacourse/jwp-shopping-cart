package woowacourse.shoppingcart.domain;

public class ThumbnailImage {
    private final String url;
    private final String alt;

    public ThumbnailImage(String url, String alt) {
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
