package cart.domain.user;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserPrivacyTest {

    private static final String DEFAULT_VALUE = "저장되지 않음.";

    @DisplayName("생성 시 입력한 이름과 휴대전화 번호를 저장한다.")
    @Test
    void shouldReturnNameAndPhoneNumberWhenRequestAfterSave() {
        String name = "test name";
        String phoneNumber = "01012341234";
        UserPrivacy userPrivacy = UserPrivacy.create(name, phoneNumber);
        assertThat(userPrivacy.getName()).isEqualTo(name);
        assertThat(userPrivacy.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @DisplayName("생성 시 이름을 입력하지 않으면, 기본 값이 반환된다.")
    @Test
    void shouldReturnDefaultNameWhenSaveWithoutNameInput() {
        String phoneNumber = "01012341234";
        UserPrivacy userPrivacy = UserPrivacy.createWithoutName(phoneNumber);
        assertThat(userPrivacy.getName()).isEqualTo(DEFAULT_VALUE);
    }

    @DisplayName("생성 시 휴대전화 번호를 입력하지 않으면, 기본 값이 반환된다.")
    @Test
    void shouldReturnDefaultNameWhenSaveWithoutPhoneNumberInput() {
        String name = "test name";
        UserPrivacy userPrivacy = UserPrivacy.createWithoutPhoneNumber(name);
        assertThat(userPrivacy.getPhoneNumber()).isEqualTo(DEFAULT_VALUE);
    }

    @DisplayName("기본 값과 같은 이름을 입력하면, 예외가 발생한다.")
    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameInputIsSameWithDefaultValue() {
        assertThatThrownBy(() -> UserPrivacy.create(DEFAULT_VALUE, "01012341234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("저장하지 않은 경우의 이름과 동일합니다. 다른 이름을 입력해주세요." + lineSeparator() +
                        "입력된 이름: " + DEFAULT_VALUE);
    }
}
