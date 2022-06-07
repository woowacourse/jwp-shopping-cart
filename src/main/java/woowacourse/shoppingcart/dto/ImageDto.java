package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Image;

public class ImageDto {

    private String url;
    private String alt;

    public ImageDto() {
    }

    public ImageDto(String url, String alt) {
        this.url = url;
        this.alt = alt;
    }

    public static ImageDto of(Image image) {
        return new ImageDto(image.getUrl(), image.getAlt());
    }

    public String getUrl() {
        return url;
    }

    public String getAlt() {
        return alt;
    }
}
