package cart.fixture;

import cart.controller.dto.request.ProductCreationRequest;
import cart.controller.dto.request.ProductUpdateRequest;

public class ProductRequestFixture {
    public static final ProductCreationRequest TEST_CREATION_MEMBER1 = new ProductCreationRequest("땡칠", "asdf", 100L);
    public static final ProductCreationRequest TEST_CREATION_MEMBER2 = new ProductCreationRequest("비버", "SMALL_IMAGE", 100L);
    public static final ProductUpdateRequest TEST_UPDATE_MEMBER1 = new ProductUpdateRequest(1L, "비버", "VERY_BIG_IMAGE", 10000L);
}
