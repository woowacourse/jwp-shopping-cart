package woowacourse.shoppingcart.domain.product.vo;

public class ThumbnailImage {
    private String url;
    private String alt;

    public ThumbnailImage(String url) {
        this(url, null);
    }

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
