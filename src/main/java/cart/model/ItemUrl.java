package cart.model;

import cart.exception.item.ItemFieldNotValidException;

public class ItemUrl {

    private static final String PREFIX = "http";

    private final String url;

    public ItemUrl(String url) {
        validateUrl(url);
        this.url = url;
    }

    private void validateUrl(String url) {
        if (!url.contains(PREFIX)) {
            throw new ItemFieldNotValidException("이미지 링크는 HTTP, HTTPS 형식으로만 입력할 수 있습니다.");
        }
    }

    String getUrl() {
        return url;
    }
}
