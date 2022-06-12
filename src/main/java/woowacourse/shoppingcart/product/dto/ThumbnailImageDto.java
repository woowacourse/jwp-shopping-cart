package woowacourse.shoppingcart.product.dto;

public class ThumbnailImageDto {
    private String url;
    private String alt;

    public ThumbnailImageDto(String url, String alt) {
        this.url = url;
        this.alt = alt;
    }

    public static ThumbnailImageDto from(ThumbnailImage thumbnailImage) {
        return new ThumbnailImageDto(thumbnailImage.getUrl(), thumbnailImage.getAlt());
    }

    public String getUrl() {
        return url;
    }

    public String getAlt() {
        return alt;
    }
}
