package cart.domain;

import cart.controller.dto.request.product.ProductUpdateRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {

    private static final String URL_REGEX = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
            "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
            "([).!';/?:,][[:blank:]])?$";

    private String name;
    private String image;
    private int price;

    public Product(final String name, final String image, final int price) {
        validate(image, price);
        this.name = name;
        this.image = image;
        this.price = price;
    }

    private void validate(String image, int price) {
        validatePrice(price);
        validateImageUrl(image);
    }

    private void validatePrice(int price) {
        if (price < 100 || price > 10_000_000) {
            throw new IllegalArgumentException("가격은 최소 100, 최대 10,000,000원 입니다.");
        }
    }

    private void validateImageUrl(String image) {
        Pattern urlPattern = Pattern.compile(URL_REGEX);
        Matcher matcher = urlPattern.matcher(image);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("유효하지 않은 Url 입니다.");
        }
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public Product update(ProductUpdateRequest productUpdateRequest) {
        this.name = productUpdateRequest.getName();
        this.image = productUpdateRequest.getImage();
        this.price = productUpdateRequest.getPrice();
        return this;
    }

}
