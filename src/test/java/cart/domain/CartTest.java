package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class CartTest {

    static Stream<Arguments> itemNormalFieldDummy() {
        return Stream.of(
                Arguments.of(1L, 1L, 2L),
                Arguments.of(null, 2L, 3L)
        );
    }

    @DisplayName("아이디, 사용자 아이디, 상품 아이디를 입력받아 생성한다. 아이디는 null 허용")
    @ParameterizedTest
    @MethodSource("itemNormalFieldDummy")
    void create(final Long id, final Long userId, final Long itemId) {
        //when
        Cart cart = new Cart.Builder()
                .id(id)
                .userId(userId)
                .itemId(itemId)
                .build();
        //then
        assertThat(cart).isNotNull();
    }

    @DisplayName("사용자 아이디가 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullUserId() {
        //then
        assertThatThrownBy(() -> new Cart.Builder()
                .id(1L)
                .userId(null)
                .itemId(1L)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 아이디가 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullItemId() {
        //then
        assertThatThrownBy(() -> new Cart.Builder()
                .id(1L)
                .userId(1L)
                .itemId(null)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
