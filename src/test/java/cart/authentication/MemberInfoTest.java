package cart.authentication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberInfoTest {

    @Test
    @DisplayName("이메일 형식이 올바르지 않으면 에러를 반환한다.")
    void invalid_email_form() {
        // given
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        MemberInfo memberInfo = new MemberInfo("ako.wooteco.com", "password");

        Set<ConstraintViolation<MemberInfo>> result = validator.validateProperty(memberInfo, "email");
        String message = result.iterator().next().getMessage();

        // when + then
        assertThat(result.size()).isEqualTo(1);
        assertThat(message).isEqualTo("유효하지 않은 이메일 형식입니다.");
    }

}