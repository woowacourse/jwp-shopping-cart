package cart.controller;

import cart.auth.AuthenticationService;
import cart.dto.ProductResponse;
import cart.mapper.ProductResponseMapper;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(AuthenticationService.class)
@WebMvcTest(AdminController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminControllerTest {

    private static final String path = "/admin";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService managementService;

    @Test
    void 상품_전체_목록을_조회하면_상태코드_200을_반환하는지_확인한다() throws Exception {
        final ProductResponse response = ProductResponseMapper.from(INITIAL_PRODUCT_ONE);
        final List<ProductResponse> responses = List.of(response);

        when(managementService.findAll())
                .thenReturn(responses);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
