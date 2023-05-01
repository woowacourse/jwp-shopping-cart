package cart.domain;

import cart.exception.InvalidProductException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MIN_PRICE = 10;
    private static final int MAX_PRICE = 100_000_000;
    private static final String URL_REGEX = "^((https?|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public Product(final long id, final String name, final String imageUrl, final Integer price) {
        validate(name, imageUrl, price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validate(final String name, final String imageUrl, final Integer price) {
        validateName(name);
        validateImageUrl(imageUrl);
        validatePrice(price);
    }

    private void validateName(final String name) {
        if (name.isBlank()) {
            throw new InvalidProductException("상품 이름은 공백을 입력할 수 없습니다.");
        }

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidProductException("상품 이름은 1자 이상 10자 이하만 가능합니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl.isBlank()) {
            throw new InvalidProductException("상품 사진의 url은 공백을 입력할 수 없습니다.");
        }

        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(imageUrl);
        if (!matcher.matches()) {
            throw new InvalidProductException("유효하지 않은 상품 사진 URL 입니다.");
        }
    }

    private void validatePrice(final Integer price) {
        if (price == null) {
            throw new InvalidProductException("상품 가격은 필수 값입니다.");
        }

        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new InvalidProductException("상품 금액은 0원 이상 100,000,000원 이하만 가능합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
