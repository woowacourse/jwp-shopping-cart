package cart.domain.cart;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.cart.CartNotChangeException;
import cart.exception.cart.CartNotFoundException;
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

    @Test
    @DisplayName("장바구니에 없는 상품을 삭제하면 예외가 발생한다.")
    void removeItemFailWithNotExistsItem() {
        assertThatThrownBy(() -> cart.removeItem(MAC_BOOK))
                .isInstanceOf(CartNotFoundException.class)
                .hasMessage("장바구니에 존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("기존 장바구니와 비교해 존재하지 않는 상품을 반환한다.")
    void findDifferentItemSuccess() {
        List<Item> items = List.of(item, MAC_BOOK);

        Item actual = cart.findDifferentItem(items);

        assertThat(actual).isEqualTo(MAC_BOOK);
    }

    @Test
    @DisplayName("기존 장바구니와 비교했을 때 차이가 없다면 예외가 발생한다.")
    void findDifferentItemFailWithSameCartItems() {
        List<Item> items = List.of(this.item);

        assertThatThrownBy(() -> cart.findDifferentItem(items))
                .isInstanceOf(CartNotChangeException.class)
                .hasMessage("장바구니의 변화가 없습니다.");
    }
}
