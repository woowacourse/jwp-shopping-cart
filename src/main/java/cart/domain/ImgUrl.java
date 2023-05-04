package cart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgUrl {

    public static final int MAX_URL_LENGTH = 8_000;
    private static final String URL_REGEX = "^((https?|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
    private final String imgUrl;

    public ImgUrl(String imgUrl) {
        this.imgUrl = validate(imgUrl);
    }

    private String validate(String imgUrl) {
        validateNotEmpty(imgUrl);
        validateUrlFormat(imgUrl);
        validateLength(imgUrl);
        return imgUrl;
    }

    private void validateNotEmpty(String imgUrl) {
        if (imgUrl.isEmpty()) {
            throw new IllegalArgumentException("이미지 URL을 입력하세요.");
        }
    }

    private void validateUrlFormat(String imgUrl) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(imgUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("유효하지 않은 URL 입니다.");
        }
    }

    private void validateLength(String imgUrl) {
        int length = imgUrl.length();
        if (length > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("url은 " + MAX_URL_LENGTH + "자 이하여야 합니다. (현재 " + length + "자)");
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImgUrl imgUrl1 = (ImgUrl) o;
        return Objects.equals(imgUrl, imgUrl1.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgUrl);
    }

    @Override
    public String toString() {
        return "ImgUrl{" +
                "imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
