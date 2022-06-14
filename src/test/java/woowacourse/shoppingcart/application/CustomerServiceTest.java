package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.MeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.exception.NotFoundException;

@SpringBootTest
@Sql(scripts = {"classpath:cleanse_test_db.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    private static Long 식별자;
    private static final String 유효한_아이디 = "유효한_아이디";
    private static final String 유효한_비밀번호 = "password1@";
    private static final String 닉네임 = "닉네임";
    private static final int 나이 = 15;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(유효한_아이디, 유효한_비밀번호, 닉네임, 나이);
        식별자 = customerService.signUp(request);
    }

    @DisplayName("이미 가입된 유저네임으로 가입하면 예외가 발생한다")
    @Test
    void signUp_fail() {
        SignUpRequest request = new SignUpRequest(유효한_아이디, 유효한_비밀번호, 닉네임, 나이);
        assertThatThrownBy(() -> customerService.signUp(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("내 정보를 불러온다.")
    @Test
    void getMeTest_success() {
        MeResponse response = customerService.getMe(식별자);

        assertThat(response.getUsername()).isEqualTo(유효한_아이디);
        assertThat(response.getNickname()).isEqualTo(닉네임);
        assertThat(response.getAge()).isEqualTo(나이);
    }

    @DisplayName("존재하지 않은 정보를 불러오면 예외가 발생한다")
    @Test
    void getMeTest_fail() {
        assertThatThrownBy(() -> customerService.getMe(2L))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("유저네임이 유일하면 true를 반환한다.")
    @Test
    void uniqueUsername_true() {
        String 유일한_유저네임 = "유일한_유저네임";

        UniqueUsernameResponse response = customerService.checkUniqueUsername(유일한_유저네임);

        assertThat(response.getIsUnique()).isTrue();
    }

    @DisplayName("유저네임이 존재하면 false를 반환한다.")
    @Test
    void uniqueUsername_false() {
        UniqueUsernameResponse response = customerService.checkUniqueUsername(유효한_아이디);

        assertThat(response.getIsUnique()).isFalse();
    }

    @DisplayName("내 정보를 업데이트한다.")
    @Test
    void updateMe_success() {
        String 변경된_닉네임 = "변경된_닉네임";
        int 변경된_나이 = 20;
        UpdateMeRequest request = new UpdateMeRequest("변경된_닉네임", 변경된_나이);

        customerService.updateMe(식별자, request);
        MeResponse me = customerService.getMe(식별자);

        assertThat(me.getNickname()).isEqualTo(변경된_닉네임);
        assertThat(me.getAge()).isEqualTo(변경된_나이);
    }

    @DisplayName("비밀번호 수정 성공")
    @Test
    void updatePassword_success() {
        String 새로운_비밀번호 = "password1!";
        UpdatePasswordRequest request = new UpdatePasswordRequest(유효한_비밀번호, 새로운_비밀번호);

        customerService.updatePassword(식별자, request);
    }

    @DisplayName("비밀번호가 변경 시 예전 비밀번호가 틀리면 예외를 반환한다.")
    @Test
    void updatePassword_fail() {
        String 틀린_비밀번호 = "password1!";
        String 새로운_비밀번호 = "password1!";
        UpdatePasswordRequest request = new UpdatePasswordRequest(틀린_비밀번호, 새로운_비밀번호);

        assertThatThrownBy(() -> customerService.updatePassword(식별자, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원을 삭제한다")
    @Test
    void deleteMeTest_success() {
        customerService.deleteMe(식별자);

        assertThatThrownBy(() -> customerService.getMe(식별자))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("존재하지 않은 회원을 삭제하면 예외가 발생한다.")
    @Test
    void deleteMeTest_fail() {
        customerService.deleteMe(식별자);

        assertThatThrownBy(() -> customerService.deleteMe(식별자))
                .isInstanceOf(NotFoundException.class);
    }
}
