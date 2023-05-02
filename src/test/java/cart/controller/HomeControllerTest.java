package cart.controller;

import cart.dto.ProductResponse;
import cart.service.MemberManagementService;
import cart.service.ProductManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.DummyData.dummyId;
import static cart.DummyData.dummyImage;
import static cart.DummyData.dummyName;
import static cart.DummyData.dummyPrice;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    private static final String path = "/";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductManagementService productManagementService;

    @MockBean
    MemberManagementService memberManagementService;

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getHomeTest() throws Exception {
        final ProductResponse response = ProductResponse.of(dummyId, dummyName, dummyImage, dummyPrice);
        final List<ProductResponse> responses = List.of(response);

        when(productManagementService.findAll())
                .thenReturn(responses);

        mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
