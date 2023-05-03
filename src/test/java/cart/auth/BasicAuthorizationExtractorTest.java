package cart.auth;

import cart.auth.dto.AuthenticationDto;
import cart.exception.AuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class BasicAuthorizationExtractorTest {

    @Test
    @DisplayName("디코딩 성공 테스트")
    void decode_success() {
        // given
        final String authorizationHeader = "Basic ZGl0b29Ad29vdGVjby5jb206ZGl0b28xMjM0";

        // when
        final AuthenticationDto authenticationDto = BasicAuthorizationExtractor.extract(authorizationHeader);

        // then
        assertAll(
                () -> assertThat(authenticationDto.getEmail()).isEqualTo("ditoo@wooteco.com"),
                () -> assertThat(authenticationDto.getPassword()).isEqualTo("ditoo1234")
        );
    }

    @Test
    @DisplayName("접두어가 Basic이 아닌 경우 예외가 발생한다.")
    void decode_fail_invalid_prefix() {
        // given
        final String authorizationHeader = "Ditoo ZGl0b29Ad29vdGVjby5jb206ZGl0b28xMjM0";

        // when, then
        assertThatThrownBy(() -> BasicAuthorizationExtractor.extract(authorizationHeader))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    @DisplayName("':'으로 구분되는 값이 2개가 아니면 예외가 발생한다.")
    void decode_fail_split_error() {
        // given
        final String authorizationHeader = "Basic ZGl0b29Ad29vdGVjby5jb21kaXRvbzEyMzQ=";

        // when, then
        assertThatThrownBy(() -> BasicAuthorizationExtractor.extract(authorizationHeader))
                .isInstanceOf(AuthorizationException.class);
    }
}