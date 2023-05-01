package cart.product.domain;

import cart.product.domain.Name;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NameTest {
    @Test
    void 이름_정상_입력() {
        assertThatNoException()
                .isThrownBy(() -> new Name("a".repeat(255)));
    }
    
    @Test
    void 이름_길이가_255자를_초과한_경우_예외_처리() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Name("a".repeat(256)))
                .withMessage("[ERROR] 상품 이름의 길이가 255자를 넘어섰습니다.");
    }
    
    @ParameterizedTest(name = "{displayName} : nameLength = {0}")
    @NullAndEmptySource
    void 이름이_비어있는_경우_예외_처리(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Name(name))
                .withMessage("[ERROR] 상품 이름을 입력해주세요.");
    }
}
