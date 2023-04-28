package cart.controller;

import cart.dto.product.ProductCreateRequestDto;
import cart.dto.product.ProductEditRequestDto;
import cart.dto.product.ProductsResponseDto;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.factory.ProductFactory.createProduct;
import static cart.factory.ProductRequestDtoFactory.createProductCreateRequest;
import static cart.factory.ProductRequestDtoFactory.createProductEditRequest;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMockTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 상품을 찾는다.")
    void find_all_products_success() throws Exception {
        // given
        ProductsResponseDto expected = ProductsResponseDto.from(List.of(createProduct()));
        given(productService.findAll()).willReturn(expected);

        // when
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].name").value("치킨"));

        // then
        verify(productService).findAll();
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void create_product_success() throws Exception {
        // given
        ProductCreateRequestDto req = createProductCreateRequest();

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(ProductCreateRequestDto.class));
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() throws Exception {
        // given
        Long id = 1L;
        ProductEditRequestDto req = createProductEditRequest();

        // when
        mockMvc.perform(put("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        verify(productService).editProduct(anyLong(), any(ProductEditRequestDto.class));
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete_product_success() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isOk());

        // then
        verify(productService).deleteById(id);
    }
}
