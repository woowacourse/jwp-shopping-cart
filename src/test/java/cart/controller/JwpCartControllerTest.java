package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.ProductEntity;
import cart.service.JwpCartService;
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

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JwpCartController.class)
class JwpCartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwpCartService jwpCartService;

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void index() throws Exception {
        List<ProductDto> expectDtos = List.of(
                ProductDto.fromEntity(new ProductEntity(1L, "product1", "p1p1.com", 1000)),
                ProductDto.fromEntity(new ProductEntity(2L, "product2", "p2p2.com", 2000))
        );

        List<ProductResponseDto> expectResponses = expectDtos.stream()
                .map(ProductResponseDto::fromProductDto)
                .collect(toList());

        when(jwpCartService.findAll()).thenReturn(expectDtos);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("products", expectResponses));
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() throws Exception {
        String name = "리오";
        String imgUrl = "http://asdf.asdf";
        int price = 3000;

        ProductRequestDto productRequestDto = new ProductRequestDto(name, imgUrl, price);
        ProductDto expectDto = ProductDto.fromEntity(new ProductEntity(1L, name, imgUrl, price));

        when(jwpCartService.add(any(ProductRequestDto.class))).thenReturn(expectDto);

        mockMvc.perform(post("/admin/products")
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
        mockMvc.perform(post("/admin/products")
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

        when(jwpCartService.updateById(any(ProductRequestDto.class), any(Long.class))).thenReturn(expectDto);

        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        mockMvc.perform(put("/admin/products/1")
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
        mockMvc.perform(put("/admin/products/{id}", any(Long.class))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedProductRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() throws Exception {
        doNothing().when(jwpCartService).deleteById(any(Long.class));
        mockMvc.perform(delete("/admin/products/{id}", any(Long.class)))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> makeInvalidDto() {
        return Stream.of(Arguments.arguments(new ProductRequestDto("a".repeat(256), "https://naver.com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000)),
                Arguments.arguments(new ProductRequestDto("aaa", "https://naver.com", -1000)));
    }
}
