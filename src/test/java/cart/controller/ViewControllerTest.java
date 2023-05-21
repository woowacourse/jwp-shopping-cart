package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.dto.MemberDto;
import cart.dto.ProductDto;
import cart.repository.entity.MemberEntity;
import cart.repository.entity.ProductEntity;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ViewController.class)
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Test
    void 상품_목록_페이지를_조회한다() throws Exception {
        final ProductEntity productEntity = new ProductEntity("name", "imageUrl", 1000);
        when(productService.findAllProduct()).thenReturn(List.of(ProductDto.from(productEntity)));

        final List<ProductDto> allProduct = productService.findAllProduct();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", allProduct))
                .andExpect(view().name("index"));
    }

    @Test
    void 어드민_페이지를_조회한다() throws Exception {
        final ProductEntity productEntity = new ProductEntity("name", "imageUrl", 1000);
        when(productService.findAllProduct()).thenReturn(List.of(ProductDto.from(productEntity)));

        final List<ProductDto> allProduct = productService.findAllProduct();

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", allProduct))
                .andExpect(view().name("admin"));
    }

    @Test
    void 설정_페이지를_조회한다() throws Exception {
        final MemberEntity memberEntity = new MemberEntity(1L, "name", "email", "password");
        when(memberService.findAllMember()).thenReturn(List.of(MemberDto.from(memberEntity)));

        final List<MemberDto> allMember = memberService.findAllMember();

        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("members", allMember))
                .andExpect(view().name("settings"));
    }

    @Test
    void 장바구니_페이지를_조회한다() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
}
