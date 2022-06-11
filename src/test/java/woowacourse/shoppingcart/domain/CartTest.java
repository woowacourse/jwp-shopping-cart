package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartTest {

    private final Product product1 = new Product(1L, "[든든] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg", 24500,
        "https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg", 10);
    private final Product product2 = new Product(2L, "[든든] LEROY 노르웨이 생연어 원물 1마리 6~7kg", 193800,
        "https://cdn-mart.baemin.com/sellergoods/main/03751585-2305-4999-85dd-7d3aba184fe6.jpg", 10);
    private Cart cart;

    @BeforeEach
    void setUp() {
        List<CartItem> cartItems = List.of(new CartItem(5, product1), new CartItem(5, product2));
        cart = new Cart(cartItems);
    }

    @Test
    @DisplayName("상품 추가시 존재하는 상품일 경우 예외를 반환한다.")
    void addCartItem_alreadyInStock() {
        //when, then
        assertThatThrownBy(() -> cart.addCartItem(product1, 5))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("이미 담겨있는 상품입니다.");
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addCartItem() {
        //given
        Product productToAdd = new Product(3L, "[든든] 옥구농협 못잊어 신동진(신동진/보통/21년)20KG", 54300,
            "https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg", 10);

        //when
        cart.addCartItem(productToAdd, 5);

        //then
        CartItem actual = cart.getItemOf(productToAdd);
        CartItem expected = new CartItem(5, productToAdd);
        assertThat(actual).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("상품 구매 수량 수정시 재고보다 구매 수량이 많을 경우 예외를 반환한다..")
    void update_overStock() {
        //when, then
        assertThatThrownBy(() -> cart.update(product1, 11))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("상품 구매 수량 수정시 장바구니에 없는 상품일 경우 예외를 반환한다..")
    void update_notAddedProduct() {
        //given
        Product notAddedProduct = new Product(3L, "[든든] 옥구농협 못잊어 신동진(신동진/보통/21년)20KG", 54300,
            "https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg", 10);

        //when, then
        assertThatThrownBy(() -> cart.update(notAddedProduct, 5))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("카트에 담겨있지 않은 상품입니다.");
    }

    @Test
    @DisplayName("상품 구매 수량을 수정한다.")
    void update() {
        //when
        cart.update(product1, 7);

        //then
        CartItem actual = cart.getItemOf(product1);
        CartItem expected = new CartItem(7, product1);
        assertThat(actual).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }
}
