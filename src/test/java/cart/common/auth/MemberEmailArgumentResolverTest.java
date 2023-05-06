package cart.common.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.ServletWebRequest;

@ExtendWith(MockitoExtension.class)
class MemberEmailArgumentResolverTest {

    @Mock
    private ServletWebRequest servletWebRequest;

    @InjectMocks
    private MemberEmailArgumentResolver memberEmailArgumentResolver;

    class MemberEmailArgumentResolverTestController {

        void hasParam(@MemberEmail final String memberEmail) {
        }

        void hasNotParam(final String memberEmail) {
        }
    }

    @ParameterizedTest(name = "주어진 파라미터가 MemberEmail 어노테이션을 지원하는지 확인한다.")
    @CsvSource(value = {"hasParam:true", "hasNotParam:false"}, delimiter = ':')
    void supportsParameter(final String methodName, final boolean isSupport)
        throws NoSuchMethodException {
        // given
        final Method method = MemberEmailArgumentResolverTestController.class.getDeclaredMethod(
            methodName, String.class);
        final MethodParameter memberEmailParam = MethodParameter.forExecutable(method, 0);

        // when, then
        assertThat(memberEmailArgumentResolver.supportsParameter(memberEmailParam))
            .isSameAs(isSupport);
    }

    @Test
    @DisplayName("요청의 Authorization 헤더 정보를 바탕으로 사용자 이메일을 추출한다.")
    void resolveArgument() throws NoSuchMethodException {
        // given
        final Method method = MemberEmailArgumentResolverTestController.class.getDeclaredMethod(
            "hasParam", String.class);
        final MethodParameter memberEmailParam = MethodParameter.forExecutable(method, 0);
        when(servletWebRequest.getHeader("Authorization"))
            .thenReturn("Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=");

        // when
        final String memberEmail = memberEmailArgumentResolver.resolveArgument(memberEmailParam,
            null, servletWebRequest, null);

        // then
        assertThat(memberEmail)
            .isEqualTo("journey@gmail.com");
    }
}
