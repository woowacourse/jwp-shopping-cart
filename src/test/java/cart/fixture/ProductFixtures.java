package cart.fixture;

import cart.dto.ProductModifyRequest;
import cart.dto.ProductRegisterRequest;
import cart.dto.ProductResponse;
import cart.entity.ProductEntity;

public class ProductFixtures {

    /**
     * DUMMY_DATA
     */
    public static final Long DUMMY_SEONGHA_ID = 1L;
    public static final String DUMMY_SEONGHA_NAME = "cuteSeongHaDoll";
    public static final String DUMMY_SEONGHA_IMG_URL = "https://avatars.githubusercontent.com/u/95729738?v=4";
    public static final int DUMMY_SEONGHA_PRICE = 25000;
    public static final Long DUMMY_BARON_ID = 2L;
    public static final String DUMMY_BARON_NAME = "cuteBaronDoll";
    public static final String DUMMY_BARON_IMG_URL = "https://avatars.githubusercontent.com/u/95729738?v=4";
    public static final int DUMMY_BARON_PRICE = 250000;

    /**
     * DUMMY_ENTITY
     */
    public static final ProductEntity INSERT_PRODUCT_ENTITY =
            new ProductEntity.Builder()
                    .name(DUMMY_SEONGHA_NAME)
                    .imgUrl(DUMMY_SEONGHA_IMG_URL)
                    .price(DUMMY_SEONGHA_PRICE)
                    .build();

    /**
     * DUMMY_DTO
     */
    public static final ProductResponse DUMMY_SEONGHA_RESPONSE =
            new ProductResponse(DUMMY_SEONGHA_ID, DUMMY_SEONGHA_IMG_URL, DUMMY_SEONGHA_NAME, DUMMY_SEONGHA_PRICE);

    public static final ProductResponse DUMMY_BARON_RESPONSE =
            new ProductResponse(DUMMY_BARON_ID, DUMMY_BARON_IMG_URL, DUMMY_BARON_NAME, DUMMY_BARON_PRICE);

    public static final ProductModifyRequest DUMMY_SEONGHA_MODIFY_REQUEST =
            new ProductModifyRequest("new" + DUMMY_SEONGHA_NAME, 10000 + DUMMY_SEONGHA_PRICE, "new" + DUMMY_SEONGHA_IMG_URL);

    public static final ProductRegisterRequest DUMMY_SEONGHA_REGISTER_REQUEST =
            new ProductRegisterRequest(DUMMY_SEONGHA_IMG_URL, DUMMY_SEONGHA_NAME, DUMMY_SEONGHA_PRICE);

    public static final ProductRegisterRequest DUMMY_BARON_REGISTER_REQUEST =
            new ProductRegisterRequest(DUMMY_BARON_IMG_URL, DUMMY_BARON_NAME, DUMMY_BARON_PRICE);

}
