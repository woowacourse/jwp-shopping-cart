package cart.infra;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import cart.dto.CartRequest;
import cart.service.JwpCartService;

class CartItemRequestExtractorTest {
    @MockBean
    private JwpCartService jwpCartService;

    @Test
    @DisplayName("리퀘스트의 바디에 있는 장바구니 상품 ID를 추출한다.")
    void extractTest() throws IOException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequest.setContent("{\"productId\": 1}".getBytes());
        CartRequest extract = CartRequestExtractor.extract(mockHttpServletRequest);
        assertThat(extract.getProductId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("장바구니 상품 ID가 없을 시 예외를 발생시킨다.")
    void extractTestFail() throws IOException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        assertThatThrownBy(() -> CartRequestExtractor.extract(mockHttpServletRequest));
    }
}
