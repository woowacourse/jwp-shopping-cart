package cart.product.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductTest {
    private static final String NORMAL_NAME = "a".repeat(255);
    private static final String NORMAL_IMAGE_URL = "a.com";
    private static final int NORMAL_PRICE = 1000;
    
    @Test
    void 이름_정상_입력() {
        assertThatNoException()
                .isThrownBy(() -> new Product(NORMAL_NAME, NORMAL_IMAGE_URL, NORMAL_PRICE));
    }
    
    @Test
    void 이름_길이가_255자를_초과한_경우_예외_처리() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product("a".repeat(256), NORMAL_IMAGE_URL, NORMAL_PRICE))
                .withMessage("[ERROR] 상품 이름의 길이가 255자를 넘어섰습니다.");
    }
    
    @ParameterizedTest(name = "{displayName} : nameLength = {0}")
    @NullAndEmptySource
    void 이름이_비어있는_경우_예외_처리(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(name, NORMAL_IMAGE_URL, NORMAL_PRICE))
                .withMessage("[ERROR] 상품 이름을 입력해주세요.");
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @NullAndEmptySource
    void 이미지_URL_Null_또는_empty_입력_시_예외_처리(String imageUrl) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(NORMAL_NAME, imageUrl, NORMAL_PRICE))
                .withMessage("[ERROR] 이미지 URL을 입력해주세요.");
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 이미지_URL_정상_입력(String imageUrl) {
        assertThatNoException()
                .isThrownBy(() -> new Product(NORMAL_NAME, imageUrl, NORMAL_PRICE));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(NORMAL_NAME, imageUrl, NORMAL_PRICE))
                .withMessage("[ERROR] 이미지 URL의 형식이 올바르지 않습니다.");
    }
    
    @ParameterizedTest(name = "{displayName} : price = {0}")
    @ValueSource(ints = {0, 10_000_000})
    void 가격_정상_입력(int price) {
        assertThatNoException()
                .isThrownBy(() -> new Product(NORMAL_NAME, NORMAL_IMAGE_URL, price));
    }
    
    @ParameterizedTest(name = "{displayName} : price = {0}")
    @ValueSource(ints = {-1, 10_000_001})
    void 가격_범위_초과_시_예외_처리(int price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(NORMAL_NAME, NORMAL_IMAGE_URL, price))
                .withMessage("[ERROR] 입력할 수 있는 가격의 범위를 벗어났습니다.");
    }
    
    @Test
    void 가격이_null일_시_예외_처리() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(NORMAL_NAME, NORMAL_IMAGE_URL, null))
                .withMessage("[ERROR] 상품 가격을 입력해주세요.");
    }
}
