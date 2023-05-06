package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberPrivacyTest {

    @DisplayName("생성 시 입력한 이름과 휴대전화 번호를 저장한다.")
    @Test
    void shouldReturnNameAndPhoneNumberWhenRequestAfterSave() {
        String name = "test name";
        String phoneNumber = "01012341234";
        MemberPrivacy memberPrivacy = new MemberPrivacy(name, phoneNumber);
        assertThat(memberPrivacy.getName().get()).isEqualTo(name);
        assertThat(memberPrivacy.getPhoneNumber().get()).isEqualTo(phoneNumber);
    }
}
