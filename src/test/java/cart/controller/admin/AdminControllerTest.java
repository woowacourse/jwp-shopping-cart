package cart.controller.admin;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_EMAIL;
import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_NAME;
import static cart.config.auth.Base64AuthInterceptor.AUTHORIZATION_HEADER;
import static cart.config.auth.Base64AuthInterceptor.BASIC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    public static final String ADMIN = ADMIN_EMAIL + ":" + ADMIN_NAME;
    public static final String ADMIN_CREDENTIALS = BASIC + " " + Base64Utils.encodeToString(ADMIN.getBytes());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductManagementService productManagementService;

    @Test
    void 상품을_추가한다() throws Exception {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 100000);
        final String requestBody = objectMapper.writeValueAsString(productDto);
        given(productManagementService.addProduct(any()))
                .willReturn(anyLong());

        mockMvc.perform(post("/admin/products")
                        .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_업데이트한다() throws Exception {
        final Long id = 1L;
        final ProductDto updatedProductDto = new ProductDto("코코닥", "https://github.com/", 15000);
        final String requestBody = objectMapper.writeValueAsString(updatedProductDto);
        doNothing()
                .when(productManagementService)
                .updateProduct(any());

        mockMvc.perform(put("/admin/products/{product_id}", id)
                        .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        final Long id = 1L;

        doNothing()
                .when(productManagementService)
                .deleteProduct(anyLong());

        mockMvc.perform(delete("/admin/products/{product_id}", id)
                        .header(AUTHORIZATION_HEADER, ADMIN_CREDENTIALS))
                .andExpect(status().isNoContent());
    }
}
