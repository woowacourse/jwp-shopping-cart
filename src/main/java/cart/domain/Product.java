package cart.domain;

import cart.controller.dto.request.product.ProductUpdateRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {

    private static final String URL_REGEX = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
            "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
            "([).!';/?:,][[:blank:]])?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private Integer id;
    private String name;
    private String imageUrl;
    private int price;

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public Product(final Integer id, final String name, final String imageUrl, final int price) {
        validate(imageUrl, price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validate(String imageUrl, int price) {
        validatePrice(price);
        validateImageUrl(imageUrl);
    }

    private void validatePrice(int price) {
        if (price < 100 || price > 10_000_000) {
            throw new IllegalArgumentException("가격은 최소 100, 최대 10,000,000원 입니다.");
        }
    }

    private void validateImageUrl(String imageUrl) {
        Matcher matcher = URL_PATTERN.matcher(imageUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("유효하지 않은 Url 입니다.");
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public Product update(ProductUpdateRequest productUpdateRequest) {
        this.name = productUpdateRequest.getName();
        this.imageUrl = productUpdateRequest.getImageUrl();
        this.price = productUpdateRequest.getPrice();
        return this;
    }

}
