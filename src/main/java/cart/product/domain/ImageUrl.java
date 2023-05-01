package cart.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public class ImageUrl {
    private static final String IMAGE_URL_FORM = "((http[s]?):\\/\\/)?(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=가-힣]{1,255}[:|\\.][a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+,.~#?&\\/=가-힣]*)";
    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile(IMAGE_URL_FORM);
    
    private final String url;
    
    public ImageUrl(final String url) {
        validateUrl(url);
        this.url = url;
    }
    
    private void validateUrl(final String url) {
        validateBlank(url);
        validateUrlFormmat(url);
    }
    
    private void validateBlank(final String url) {
        if (Objects.isNull(url) || url.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이미지 URL을 입력해주세요.");
        }
    }
    
    private void validateUrlFormmat(final String url) {
        final Matcher matcher = IMAGE_URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 이미지 URL의 형식이 올바르지 않습니다.");
        }
    }
}
