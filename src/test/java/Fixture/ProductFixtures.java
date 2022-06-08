package Fixture;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;

public class ProductFixtures {

    public static final String CHICKEN_NAME = "치킨";
    public static final int CHICKEN_PRICE = 10_000;
    public static final String CHICKEN_IMAGE_URL = "http://example.com/chicken.jpg";
    public static final boolean CHICKEN_SELLING = true;
    public static final String CHICKEN_DESCRIPTION = "치킨입니다.";
    public static final Product CHICKEN = new Product(CHICKEN_NAME, CHICKEN_PRICE, CHICKEN_IMAGE_URL, CHICKEN_SELLING,
            CHICKEN_DESCRIPTION);
    public static final ProductRequest CHICKEN_REQUEST = new ProductRequest(CHICKEN_NAME, CHICKEN_PRICE,
            CHICKEN_IMAGE_URL, CHICKEN_DESCRIPTION);

    public static final String BEER_NAME = "맥주";
    public static final int BEER_PRICE = 5_000;
    public static final String BEER_IMAGE_URL = "http://example.com/beer.jpg";
    public static final boolean BEER_SELLING = true;
    public static final String BEER_DESCRIPTION = "맥주입니다.";
    public static final Product BEER = new Product(BEER_NAME, BEER_PRICE, BEER_IMAGE_URL, BEER_SELLING,
            BEER_DESCRIPTION);
    public static final ProductRequest BEER_REQUEST = new ProductRequest(BEER_NAME, BEER_PRICE,
            BEER_IMAGE_URL, BEER_DESCRIPTION);
}
