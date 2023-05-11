//package cart.service;
//
//import cart.dao.CartDao;
//import cart.dao.ProductDao;
//import cart.dao.entity.ProductEntity;
//import cart.dto.CartUpdateRequestDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CartServiceTest {
//
//    private static final Long MEMBER_ID = 1L;
//
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private CartDao cartDao;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Test
//    void 카트에_상품을_추가할_수_있다() {
//        // given
//        final Long productId = productDao.insert(new ProductEntity.Builder()
//                .name("치킨")
//                .image("치킨 사진")
//                .price(30_000)
//                .build()
//        );
//        final CartUpdateRequestDto cartUpdateRequestDto = new CartUpdateRequestDto(productId);
//
//        cartService.addProductToCart(cartUpdateRequestDto, MEMBER_ID);
//
//        assertThat(cartDao.findProductsByMemberId(MEMBER_ID))
//                .contains(
//                        new ProductEntity.Builder()
//                                .name("치킨")
//                                .image("치킨 이미지")
//                                .price(30_000)
//                                .build()
//                );
//    }
//}
