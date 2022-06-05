package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class ThumbnailImageDto {
    @NotBlank
    private String alt;
    @NotBlank
    private String url;

    public ThumbnailImageDto() {
    }

    public ThumbnailImageDto(String url) {
        this(url, "");
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
