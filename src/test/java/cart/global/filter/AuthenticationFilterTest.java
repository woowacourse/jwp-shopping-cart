package cart.global.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationFilterTest {

    private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

    private final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

    private final MockFilterChain mockFilterChain = new MockFilterChain();

    private final AuthenticationFilter authenticationFilter = new AuthenticationFilter();

    @ParameterizedTest
    @ValueSource(strings = {"/cart", "/", "/admin"})
    @DisplayName("doFilterInternal() : /carts로 시작하지 않는 URL은 필터를 거치지 않습니다.")
    void test_doFilterInternal_noFilter(final String path) throws Exception {
        //given
        setHttpMethodAndUri(mockHttpServletRequest, "GET", path);

        //when & then
        assertDoesNotThrow(
                () -> authenticationFilter.doFilterInternal(
                        mockHttpServletRequest,
                        mockHttpServletResponse,
                        mockFilterChain
                )
        );
    }

    @ParameterizedTest
    @CsvSource({
            "POST,/carts/products/1",
            "GET,/carts",
            "DELETE,/carts/products/1",
    })
    @DisplayName("doFilterInternal() : /carts로 시작하는 URL은 헤더가 없으면 401 Unauthorized가 발생합니다.")
    void test_doFilterInternal_Unauthorized(
            final String method, final String path
    ) throws Exception {
        //given
        setHttpMethodAndUri(mockHttpServletRequest, method, path);

        //when
        authenticationFilter.doFilterInternal(
                mockHttpServletRequest,
                mockHttpServletResponse,
                mockFilterChain
        );

        //then
        assertEquals(mockHttpServletResponse.getStatus(), HttpStatus.UNAUTHORIZED.value());
    }

    @ParameterizedTest
    @CsvSource({
            "POST,/carts/products/1",
            "GET,/carts",
            "DELETE,/carts/products/1",
    })
    @DisplayName("doFilterInternal() : /carts로 시작하는 URL은 헤더가 있어야합니다.")
    void test_doFilterInternal_filter(final String method, final String path) throws Exception {
        //given
        setHttpMethodAndUri(mockHttpServletRequest, method, path);

        //email:password Base64 디코딩 값
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "ZW1haWw6cGFzc3dvcmQ=");

        //when & then
        assertDoesNotThrow(
                () -> authenticationFilter.doFilterInternal(
                        mockHttpServletRequest,
                        mockHttpServletResponse,
                        mockFilterChain
                )
        );
    }

    private void setHttpMethodAndUri(
            MockHttpServletRequest mockHttpServletRequest,
            String method,
            String path
    ) {
        mockHttpServletRequest.setMethod(method);
        mockHttpServletRequest.setRequestURI(path);
    }
}
