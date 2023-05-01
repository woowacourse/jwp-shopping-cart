package cart.domain.admin.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import cart.domain.EntityMappingException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductEntityTest {

    private final String length20Name = "01234567890123456789";
    private final int priceMultipliedByTen = 10;
    private final String validUrl = "https://a.com";

    @Test
    void 이름_길이_20_성공_테스트() {
        assertDoesNotThrow(() -> new ProductEntity(length20Name, priceMultipliedByTen, validUrl));
    }

    @Test
    void 이름_길이_21_예외_발생_테스트() {
        final String length21Name = length20Name + "0";

        assertThrows(EntityMappingException.class,
            () -> new ProductEntity(length21Name, priceMultipliedByTen, validUrl));
    }

    @Test
    void 금액_10원단위_성공_테스트() {
        assertDoesNotThrow(() -> new ProductEntity(length20Name, priceMultipliedByTen, validUrl));
    }

    @Test
    void 금액_1원단위_예외_발생_테스트() {
        final int invalidPrice = priceMultipliedByTen + 1;

        assertThrows(EntityMappingException.class, () -> new ProductEntity(length20Name, invalidPrice, validUrl));
    }

    @Test
    void URL_길이_512_성공_테스트() {
        final String length513Url = ".".repeat(512);

        assertDoesNotThrow(() -> new ProductEntity(length20Name, priceMultipliedByTen, length513Url));
    }

    @Test
    void URL_길이_513_예외_발생_테스트() {
        final String length513Url = ".".repeat(513);

        assertThrows(EntityMappingException.class,
            () -> new ProductEntity(length20Name, priceMultipliedByTen, length513Url));
    }
}
