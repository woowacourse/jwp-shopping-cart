package woowacourse.shoppingcart.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashToolTest {

    private static final String HASHED_PASSWORD = "30e1584e0098940c71f9c1c2322fd044f5fc460ee16983480b8ee6f3c8953c45";

    private final HashTool hashTool = new HashTool();

    @DisplayName("비밀번호를 정상적으로 hash한다.")
    @Test
    void hash() {
        String password = "anPang";

        assertThat(hashTool.hashing(password)).isEqualTo(HASHED_PASSWORD);
    }
}