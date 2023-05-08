package cart.entity;

import cart.exception.customexceptions.NotValidDataException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberTest {

    @Test
    void 멤버의_이메일이_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Member("   ", "hardy", "sfds1"))
                .isInstanceOf(NotValidDataException.class);
    }

    @Test
    void 멤버의_이름이_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Member("fdsffds", "    ", "sfds1"))
                .isInstanceOf(NotValidDataException.class);
    }

    @Test
    void 멤버의_비밀번호가_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Member("gfdgfd", "hardy", " "))
                .isInstanceOf(NotValidDataException.class);
    }
}
