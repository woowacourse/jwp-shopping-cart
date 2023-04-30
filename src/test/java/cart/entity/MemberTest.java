package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MemberTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("이메일과 비밀번호를 이용해 Member 객체를 만들 수 있다.")
    void member() {
        assertThatCode(() -> new Member("test@gmail.com", "password1234!"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이메일 형식이 아닌 경우 예외가 발생할 수 있다.")
    void notEmail() {
        Set<ConstraintViolation<Member>> violations = validator.validate(new Member("testgmailcom", "password1234!"));

        List<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertThat(violationMessages)
                .contains("이메일 형식의 입력이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"email@gmail.com::비밀번호는 비어있을 수 없습니다.", ":password1234!:이메일은 비어있을 수 없습니다."}, delimiter = ':')
    @DisplayName("이메일 항목이나 비밀번호 항목이 비어있을 경우 예외가 발생할 수 있다.")
    void empty(String email, String password, String validationMessage) {
        Set<ConstraintViolation<Member>> violations = validator.validate(new Member(email, password));

        List<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertThat(violationMessages)
                .contains(validationMessage);
    }
}