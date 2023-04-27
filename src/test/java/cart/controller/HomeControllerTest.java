package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    private static final String path = "/";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductManagementService managementService;

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getHomeTest() throws Exception {
        final ProductDto productDto = ProductDto.of(1L, "test", "http:", 10_000);
        final List<ProductDto> productDtos = List.of(productDto);

        when(managementService.findAll())
                .thenReturn(productDtos);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
