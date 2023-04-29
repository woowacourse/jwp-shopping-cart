package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.controller.dto.ProductModifyRequest;
import cart.service.CartService;
import cart.controller.dto.ProductRegisterRequest;
import cart.service.dto.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest
public class AdminControllerUnitTest {

    private final ProductResponse cuteSeonghaDoll =
            new ProductResponse(1, "https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteSeonghaDoll", 25000);

    private final ProductResponse cuteBaronDoll =
            new ProductResponse(2, "https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteBaronDoll", 250000);

    private final ProductModifyRequest cuteModifySeonghaDoll =
            new ProductModifyRequest("CuteSeonghaDoll", 2500000, "https://avatars.githubusercontent.com/u/95729738?v=4");

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("전체 상품 조회 API 호출 시 전체 상품이 반환된다.")
    @Test
    void showAllProducts() throws Exception {
        given(cartService.findAllProducts()).willReturn(List.of(cuteSeonghaDoll, cuteBaronDoll));
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @DisplayName("상품 등록 API 호출 시 상품을 등록한다.")
    @Test
    void registerProduct() throws Exception {
        // given
        String requestString = objectMapper.writeValueAsString(cuteSeonghaDoll);
        given(cartService.save(any(ProductRegisterRequest.class))).willReturn(1L);

        // when then
        mockMvc.perform(post("/admin/product")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/admin/product/1"));
    }

    @DisplayName("상품 정보 수정 API 호출 시 상품 정보가 수정된다.")
    @Test
    void modifyProduct() throws Exception {
        // given
        String requestString = objectMapper.writeValueAsString(cuteModifySeonghaDoll);

        mockMvc.perform(put("/admin/product/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestString))
                .andExpect(status().isNoContent());
    }

    @DisplayName("상품 삭제 API 호출 시 상품이 삭제된다.")
    @Test
    void deleteProduct() throws Exception {
        // when, then
        mockMvc.perform(delete("/admin/product/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("가격이 0이하의 값이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void exceptionWhenPriceNotPositive(int price) throws Exception {
        // given
        ProductRegisterRequest wrongCuteSeonghaDoll =
                new ProductRegisterRequest("https://avatars.githubusercontent.com/u/95729738?v=4", "CuteSeonghaDoll",
                        price);
        String requestString = objectMapper.writeValueAsString(wrongCuteSeonghaDoll);
        given(cartService.save(any(ProductRegisterRequest.class))).willThrow(new IllegalArgumentException("가격은 0보다 커야합니다."));

        // when then
        mockMvc.perform(post("/admin/product")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("가격은 0보다 커야합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("이름이 1자 미만이거나 50자 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"dskjgfdsvesvurevhjdsbvehsbvhjesbvhjesbvfhvsdhvhdsvhfdshv", ""})
    void exceptionWhenNameWrongLength(String name) throws Exception {
        // given
        ProductRegisterRequest wrongCuteSeonghaDoll =
                new ProductRegisterRequest("https://avatars.githubusercontent.com/u/95729738?v=4", name,
                        25000);
        System.out.println(name.length());
        String requestString = objectMapper.writeValueAsString(wrongCuteSeonghaDoll);
        given(cartService.save(any(ProductRegisterRequest.class))).willThrow(new IllegalArgumentException("이름은 1글자 이상 50글자 이하여야합니다."));

        // when then
        mockMvc.perform(post("/admin/product")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이름은 1글자 이상 50글자 이하여야합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("이미지 URL이 없으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void exceptionWhenBlankImgUrl(String imgUrl) throws Exception {
        // given
        ProductRegisterRequest wrongCuteSeonghaDoll =
                new ProductRegisterRequest(imgUrl, "CuteSeonghaDoll",
                        25000);
        String requestString = objectMapper.writeValueAsString(wrongCuteSeonghaDoll);
        given(cartService.save(any(ProductRegisterRequest.class))).willReturn(1L);

        // when then
        mockMvc.perform(post("/admin/product")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(requestString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미지 URL은 필수입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

}
