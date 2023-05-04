package cart.cart.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class CartTest {
    @ParameterizedTest(name = "{displayName} : productId={0}, memberId={1}, expectIsSmae={2}")
    @CsvSource({"1,1,true", "1,2,false", "2,1,false"})
    void productId와_memberId가_같은지_확인한다(final Long productId, final Long memberId, final boolean expectIsSame) {
        // given
        final Cart cart = new Cart(1L, 1L, 1L);

        // when
        final boolean actualSame = cart.isSame(productId, memberId);

        // then
        assertThat(actualSame).isEqualTo(expectIsSame);
    }
}
