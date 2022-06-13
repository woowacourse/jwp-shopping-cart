package woowacourse.shoppingcart.dto;

public class ThumbnailImageDto {
    private String alt;
    private String url;

    public ThumbnailImageDto() {
    }
    
    public ThumbnailImageDto(String url, String alt) {
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
