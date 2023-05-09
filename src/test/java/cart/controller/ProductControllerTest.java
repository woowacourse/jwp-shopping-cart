package cart.controller;

import cart.dto.product.ProductDto;
import cart.dto.product.ProductRequestDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
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

    static Stream<Arguments> makeInvalidDto() {
        return Stream.of(Arguments.arguments(new ProductRequestDto("a".repeat(256), "https://naver.com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver.com", -1000)));
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProductTest_success() throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int price = 3000;

        ProductRequestDto requestDto = new ProductRequestDto(name, imgUrl, price);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, price));

        when(productService.add(any(ProductRequestDto.class))).thenReturn(expectDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(expectDto.getName()))
                .andExpect(jsonPath("$.imgUrl").value(expectDto.getImgUrl()))
                .andExpect(jsonPath("$.price").value(expectDto.getPrice()));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("상품을 추가한다. - 잘못된 입력을 검증한다.")
    void addProductTest_fail(ProductRequestDto requestDto) throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void updateProductTest_success() throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int modifiedPrice = 1000;

        ProductRequestDto modifiedRequestDto = new ProductRequestDto(name, imgUrl, modifiedPrice);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, modifiedPrice));

        when(productService.updateById(any(ProductRequestDto.class), anyLong())).thenReturn(expectDto);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectDto.getName()))
                .andExpect(jsonPath("$.imgUrl").value(expectDto.getImgUrl()))
                .andExpect(jsonPath("$.price").value(expectDto.getPrice()));
    }

    @ParameterizedTest
    @MethodSource("makeInvalidDto")
    @DisplayName("상품 정보를 수정한다. - 잘못된 입력을 검증한다.")
    void updateProductTest_fail(ProductRequestDto modifiedRequestDto) throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int modifiedPrice = 1000;

        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, modifiedPrice));

        when(productService.updateById(any(ProductRequestDto.class), anyLong())).thenReturn(expectDto);

        mockMvc.perform(put("/products/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductTest() throws Exception {
        doNothing().when(productService).deleteById(anyLong());
        mockMvc.perform(delete("/products/{id}", anyLong()))
                .andExpect(status().isOk());
    }
}