package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidProductException;

@EqualsAndHashCode
@Getter
public class ImageUrl {

	private static final Pattern IMAGE_PATTERN = Pattern.compile(
		"^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$"
	);

	private final String value;

	public ImageUrl(String value) {
		validateUrl(value);
		this.value = value;
	}

	private void validateUrl(String value) {
		Matcher matcher = IMAGE_PATTERN.matcher(value);
		if (!matcher.matches()) {
			throw new InvalidProductException(ErrorCode.ARGUMENT, "url 형식이 올바르지 않습니다.");
		}
	}
}
