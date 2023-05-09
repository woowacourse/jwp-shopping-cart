package cart.vo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cart.vo.Name.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    @DisplayName("올바르지 않은 길이의 이름이 들어갔을 때 예외 발생")
    void createNameFail() {
        String length0 = "";
        String length101 = "a".repeat(101);

        assertThatThrownBy(() -> Name.from(length0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 이름입니다.");
        assertThatThrownBy(() -> Name.from(length101))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 이름입니다.");
    }

    @Test
    @DisplayName("올바른 길이의 이름이 들어갔을 때에는 예외가 발생하지 않는다.")
    void createNameSuccess() {
        String length1 = "a";
        String length100 = "a".repeat(100);

        Name nameOfLength1 = Name.from(length1);
        Name nameOfLength100 = from(length100);

        assertThat(nameOfLength1.getValue()).isEqualTo(length1);
        assertThat(nameOfLength100.getValue()).isEqualTo(length100);
    }

}
