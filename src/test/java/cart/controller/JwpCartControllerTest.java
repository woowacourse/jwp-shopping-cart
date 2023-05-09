package cart.controller;

import cart.dto.member.MemberDto;
import cart.dto.member.MemberResponseDto;
import cart.dto.product.ProductDto;
import cart.dto.product.ProductResponseDto;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.service.MemberService;
import cart.service.ProductService;
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
    ProductService productService;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void indexTest() throws Exception {
        List<ProductDto> expectDtos = List.of(
                ProductDto.fromEntity(new ProductEntity(1L, "product1", "p1p1.com", 1000)),
                ProductDto.fromEntity(new ProductEntity(2L, "product2", "p2p2.com", 2000))
        );

        List<ProductResponseDto> expectResponses = expectDtos.stream()
                .map(ProductResponseDto::fromDto)
                .collect(toList());

        when(productService.findAll()).thenReturn(expectDtos);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("products", expectResponses));
    }

    @Test
    @DisplayName("관리자 도구 페이지를 조회한다.")
    void adminTest() throws Exception {
        List<ProductDto> expectDtos = List.of(
                ProductDto.fromEntity(new ProductEntity(1L, "product1", "p1p1.com", 1000)),
                ProductDto.fromEntity(new ProductEntity(2L, "product2", "p2p2.com", 2000))
        );

        List<ProductResponseDto> expectResponses = expectDtos.stream()
                .map(ProductResponseDto::fromDto)
                .collect(toList());

        when(productService.findAll()).thenReturn(expectDtos);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("products", expectResponses));
    }

    @Test
    @DisplayName("사용자 설정 페이지를 조회한다.")
    void settingsTest() throws Exception {
        List<MemberDto> expectDtos = List.of(
                MemberDto.fromEntity(new MemberEntity(1L, "a@a.com", "password1")),
                MemberDto.fromEntity(new MemberEntity(2L, "b@b.com", "password2"))
        );

        List<MemberResponseDto> expectResponses = expectDtos.stream()
                .map(MemberResponseDto::fromDto)
                .collect(toList());

        when(memberService.findAll()).thenReturn(expectDtos);

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(model().attribute("members", expectResponses));
    }

    @Test
    @DisplayName("장바구니 페이지를 조회한다.")
    void cartTest() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
}
