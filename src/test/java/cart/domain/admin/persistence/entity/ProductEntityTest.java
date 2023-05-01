package cart.domain.admin.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductEntityTest {

    private final String validName = "01234567890123456789";
    private final int validPrice = 10;
    private final String validUrl = "https://a.com";

    @Test
    void 이름_길이_20_성공_테스트() {
        final String length20Name = "01234567890123456789";

        assertDoesNotThrow(() -> new ProductEntity(length20Name, validPrice, validUrl));
    }

    @Test
    void 이름_길이_21_예외_발생_테스트() {
        final String length21Name = "012345678901234567890";

        assertThrows(ProductEntityMappingException.class, () -> new ProductEntity(length21Name, validPrice, validUrl));
    }

    @Test
    void 금액_10원단위_성공_테스트() {
        final int invalidPrice = 10;

        assertDoesNotThrow(() -> new ProductEntity(validName, invalidPrice, validUrl));
    }

    @Test
    void 금액_1원단위_예외_발생_테스트() {
        final int invalidPrice = 11;

        assertThrows(ProductEntityMappingException.class, () -> new ProductEntity(validName, invalidPrice, validUrl));
    }

    @Test
    void URL_길이_512_성공_테스트() {
        final String length513Url = ".".repeat(512);

        assertDoesNotThrow(() -> new ProductEntity(validName, validPrice, length513Url));
    }

    @Test
    void URL_길이_513_예외_발생_테스트() {
        final String length513Url = ".".repeat(513);

        assertThrows(ProductEntityMappingException.class, () -> new ProductEntity(validName, validPrice, length513Url));
    }
}
