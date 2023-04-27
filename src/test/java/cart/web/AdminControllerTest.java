package cart.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.AdminService;
import cart.domain.product.CartService;
import cart.domain.product.TestFixture;
import cart.domain.product.dto.ProductDto;
import cart.web.dto.ProductCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private CartService cartService;

    @DisplayName("/admin 요청시, admin.html을 반환한다.")
    @Test
    void loadAdminPage() throws Exception {
        List<ProductDto> expectedProducts = List.of(
                ProductDto.from(TestFixture.PIZZA),
                ProductDto.from(TestFixture.CHICKEN)
        );
        when(cartService.getAllProducts())
                .thenReturn(expectedProducts);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("admin"))
                .andDo(print());
    }

    @DisplayName("POST 요청시, 상품을 등록할 수 있다.")
    @Test
    void postProduct() throws Exception {
        String productName = "ProductA";
        int productPrice = 18_000;
        ProductCreateRequest request =
                new ProductCreateRequest(productName, productPrice, "FOOD", "image.com");
        when(adminService.save(any()))
                .thenReturn(1L);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.price").value(productPrice));
    }
}
