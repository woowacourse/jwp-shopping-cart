package cart.controller.admin;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(AdminPageController.class)
public class AdminPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductManagementService productManagementService;

    @Test
    void 어드민_페이지를_조회한다() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        given(productManagementService.findAllProduct())
                .willReturn(products);

        mockMvc.perform(get("/admin"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products))
                .andExpect(status().isOk());
    }
}
