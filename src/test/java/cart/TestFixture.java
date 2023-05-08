package cart;

import cart.domain.Product;
import cart.domain.User;

public class TestFixture {

    public static final String IMAGE_CHICKEN = "https://user-images.githubusercontent.com/39221443/235916727-e62f14ed-ad8a-49df-958d-8783d8820152.png";
    public static final String IMAGE_ICE_CREAM = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7qFa1NmOi5ZMOF2hlBLb7oEGzjoH6rPXzvQ&usqp=CAU";
    public static final String IMAGE_VANILLA_LATTE = "https://globalassets.starbucks.com/assets/1b317a079984402895bb5cf4d2894940.jpg?impolicy=1by1_wide_topcrop_630";

    public static final String NAME_CHICKEN = "치킨";
    public static final String NAME_ICE_CREAM = "아이스크림";
    public static final String NAME_VANILLA_LATTE = "바닐라 라떼";

    public static final Long PRICE_CHICKEN = 23_000L;
    public static final Long PRICE_ICE_CREAM = 5_000L;
    public static final Long PRICE_VANILLA_LATTE = 3_800L;

    public static final Product PRODUCT_CHICKEN = new Product(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN);
    public static final Product PRODUCT_ICE_CREAM = new Product(NAME_ICE_CREAM, IMAGE_ICE_CREAM, PRICE_ICE_CREAM);
    public static final Product PRODUCT_VANILLA_LATTE = new Product(NAME_VANILLA_LATTE, IMAGE_VANILLA_LATTE, PRICE_VANILLA_LATTE);

    public static final String EMAIL_0CHIL = "0@chll.it";
    public static final String EMAIL_BEAVER = "beaver@wooteco.com";

    public static final String PASSWORD_0CHIL = "weakpassword";
    public static final String PASSWORD_BEAVER = "veryverysecurepassword";

    public static final User USER_0CHIL = new User(null, EMAIL_0CHIL, PASSWORD_0CHIL);
}
