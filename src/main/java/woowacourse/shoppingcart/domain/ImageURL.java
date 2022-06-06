package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInformationException;

import java.util.Objects;

public class ImageURL {
    private static final String URL_REGEX = "^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-z0-9.?#/]+)?";
    private static final String NOT_VALID_URL = "[ERROR] URL 형식이 올바르지 않습니다.";

    private final String url;

    public ImageURL(String url) {
        validateURL(url);
        this.url = url;
    }

    private void validateURL(String url) {
        if (!url.matches(URL_REGEX)) {
            throw new InvalidInformationException(NOT_VALID_URL);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageURL imageURL = (ImageURL) o;
        return Objects.equals(url, imageURL.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    public String getUrl() {
        return url;
    }
}
