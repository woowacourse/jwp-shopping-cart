package cart.controller;

import cart.DummyData;
import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.DummyData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    private static final String path = "/admin";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductManagementService managementService;

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getAdminTest() throws Exception {
        final ProductDto productDto = ProductDto.of(dummyId, dummyName, dummyImage, dummyPrice);
        final List<ProductDto> productDtos = List.of(productDto);

        when(managementService.findAll())
                .thenReturn(productDtos);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
