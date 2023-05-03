package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductResponseDto;
import cart.entity.ProductEntity;
import cart.service.JwpCartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JwpCartController.class)
class JwpCartControllerTest {

    @Autowired
    MockMvc mockMvc;

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
}
