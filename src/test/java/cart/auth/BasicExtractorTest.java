package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import cart.dto.user.UserRequest;

class BasicExtractorTest {

    BasicExtractor basicExtractor = new BasicExtractor();

    @Test
    @DisplayName("header에 담긴 BasicType의 회원정보를 파싱한다.")
    void extract() {
        final HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic YWhkamQ1QGdtYWlsLmNvbTpxd2VyMTIzNA==");

        final UserRequest result = basicExtractor.extractUser(header);

        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("ahdjd5@gmail.com"),
                () -> assertThat(result.getPassword()).isEqualTo("qwer1234")
        );
    }

}
