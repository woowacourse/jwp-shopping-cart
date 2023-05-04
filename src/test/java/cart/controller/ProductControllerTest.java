package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int price = 3000;

        ProductRequestDto productRequestDto = new ProductRequestDto(name, imgUrl, price);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, price));

        when(productService.add(any(ProductRequestDto.class))).thenReturn(expectDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.imgUrl").value(imgUrl))
                .andExpect(jsonPath("$.price").value(price));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("상품을 추가한다. - 잘못된 입력을 검증한다.")
    void addProductInvalidInput(ProductRequestDto productRequestDto) throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void updateProduct() throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int price = 3000;
        int modifiedPrice = 1000;

        ProductRequestDto productRequestDto = new ProductRequestDto(name, imgUrl, price);
        ProductRequestDto modifiedProductRequestDto = new ProductRequestDto(name, imgUrl, modifiedPrice);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, modifiedPrice));

        when(productService.updateById(any(ProductRequestDto.class), any(Long.class))).thenReturn(expectDto);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedProductRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.imgUrl").value(imgUrl))
                .andExpect(jsonPath("$.price").value(modifiedPrice));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("상품 정보를 수정한다. - 잘못된 입력을 검증한다.")
    void updateProductInvalidInput(ProductRequestDto modifiedProductRequestDto) throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int price = 3000;
        int modifiedPrice = 1000;

        ProductRequestDto productRequestDto = new ProductRequestDto(name, imgUrl, price);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, modifiedPrice));

        when(productService.updateById(any(ProductRequestDto.class), any(Long.class))).thenReturn(expectDto);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        mockMvc.perform(put("/products/{id}", any(Long.class))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedProductRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteById(any(Long.class));
        mockMvc.perform(delete("/products/{id}", any(Long.class)))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> makeInvalidDto() {
        return Stream.of(Arguments.arguments(new ProductRequestDto("a".repeat(256), "https://naver.com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver.com", -1000)));
    }
}