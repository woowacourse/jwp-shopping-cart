package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import cart.dao.ProductCartDao;
import cart.dao.ProductDao;
import cart.dto.CartsResponse;
import cart.dto.CartsResponse.CartResponse;
import cart.dto.ProductCartResponse;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductCartServiceTest {

    private ProductCartService productCartService;
    private ProductCartDao productCartDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productCartDao = Mockito.mock(ProductCartDao.class);
        productDao = Mockito.mock(ProductDao.class);
        productCartService = new ProductCartService(productCartDao, productDao);
    }

    @DisplayName("member를 기준으로 cart의 product를 반환한다")
    @Test
    void findAllMyProductCartTest() {
        given(productCartDao.findAllByMember(any()))
                .willReturn(List.of(
                        new ProductCart(1L, 1L, 1L),
                        new ProductCart(2L, 2L, 1L),
                        new ProductCart(3L, 3L, 1L),
                        new ProductCart(4L, 4L, 1L)
                ));
        given(productDao.findById(anyLong()))
                .willReturn(
                        Optional.of(new Product(1L, "boxster1", "https://boxster1.com", 10000)),
                        Optional.of(new Product(2L, "boxster2", "https://boxster2.com", 20000)),
                        Optional.of(new Product(3L, "boxster3", "https://boxster3.com", 30000)),
                        Optional.of(new Product(4L, "boxster4", "https://boxster4.com", 40000))
                );

        CartsResponse cartsResponse = productCartService.findAllMyProductCart(
                new Member(1L, "boxster@email.com", "boxster"));
        assertAll(
                () -> assertThat(cartsResponse.getCartResponses()).map(CartResponse::getId)
                        .contains(1L, 2L, 3L, 4L),
                () -> assertThat(cartsResponse.getCartResponses()).hasSize(4)
        );
    }

    @DisplayName("내 장바구니에 저장한다")
    @Test
    void addMyCartTest() {
        given(productDao.findById(anyLong()))
                .willReturn(Optional.of(new Product(1L, "boxster", "https://boxster.com", 10000)));
        given(productCartDao.save(any()))
                .willReturn(new ProductCart(1L, 1L, 1L));

        ProductCartResponse response = productCartService.addCart(1L, new Member(1L, "email@email.com", "password"));

        assertThat(response.getId()).isEqualTo(1L);
    }
}
