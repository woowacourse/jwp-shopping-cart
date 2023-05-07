package cart.domain.cart;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    Item item;
    Cart cart;

    @BeforeEach
    void setUp() {
        item = new Item(3L, "자전거", "https://image.com", 10_000);

        List<Item> items = new ArrayList<>();
        items.add(item);
        cart = new Cart(1L, items);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addItemSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> cart.addItem(MAC_BOOK)),
                () -> assertThat(cart.getItems()).hasSize(2)
        );
    }

    @Test
    @DisplayName("이미 장바구니에 존재하는 상품을 다시 저장하면 예외가 발생한다.")
    void addItemFailWithAlreadyExistsItem() {
        assertThatThrownBy(() -> cart.addItem(item))
                .isInstanceOf(CartAlreadyExistsException.class)
                .hasMessage("이미 장바구니에 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다.")
    void removeItemSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> cart.removeItem(item)),
                () -> assertThat(cart.getItems()).isEmpty()
        );
    }
}
