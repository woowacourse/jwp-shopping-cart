package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ItemTest {

    @DisplayName("아이디, 이름, URL, 가격을 입력받아 생성한다. 아이디와 URL은 null 허용")
    @ParameterizedTest
    @MethodSource("itemNormalFieldDummy")
    void create(final Long id, final Name name, final ImageUrl imageUrl, final Price price) {
        //when
        Item item = new Item.Builder()
                .id(id)
                .name(name)
                .imageUrl(imageUrl)
                .price(price)
                .build();
        //then
        assertThat(item).isNotNull();
    }

    static Stream<Arguments> itemNormalFieldDummy() {
        return Stream.of(
                Arguments.of(1L, new Name("레드북"), new ImageUrl("ImageUrl"), new Price(50000)),
                Arguments.of(null, new Name("레드북"), new ImageUrl("ImageUrl"), new Price(50000)),
                Arguments.of(1L, new Name("레드북"), null, new Price(50000)),
                Arguments.of(null, new Name("레드북"), null, new Price(50000))
        );
    }

    @DisplayName("이름이 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullName() {
        //then
        assertThatThrownBy(() -> new Item.Builder()
                .id(1L)
                .name(null)
                .imageUrl(new ImageUrl("ImageUrl"))
                .price(new Price(50000))
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 null일 경우 예외를 반환한다")
    @Test
    void createExceptionWithNullPrice() {
        //then
        assertThatThrownBy(() -> new Item.Builder()
                .id(1L)
                .name(new Name("레드북"))
                .imageUrl(new ImageUrl("ImageUrl"))
                .price(null)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
