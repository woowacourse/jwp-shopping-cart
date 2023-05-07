package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
import cart.service.MemberService;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private Validation validation;

    @Test
    @DisplayName("상품을 추가한다")
    void addProduct() throws Exception {
        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto("치킨", 10000, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void modifyProduct() throws Exception {
        ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto("치킨", 10000, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productModifyRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품을 제거한다.")
    void removeProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품의 가격이 0원 미만이면 예외가 발생한다. - 상품 추가")
    void validatePrice_add() throws Exception {
        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto("치킨", -1, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("가격은 0원 이상이여야 합니다.")));
    }

    @Test
    @DisplayName("상품의 이름이 빈 값이면 예외가 발생한다. - 상품 추가")
    void validateNull_add() throws Exception {
        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(null, 10000, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("상품 이름을 입력해주세요.")));
    }

    @Test
    @DisplayName("상품의 이미지가 빈 값이면 예외가 발생한다. - 상품 추가")
    void validateNullImage_add() throws Exception {
        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto("치킨", 10000, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("이미지 url을 입력해주세요.")));
    }

    @Test
    @DisplayName("상품의 가격이 0원 미만이면 예외가 발생한다. - 상품 수정")
    void validatePrice_modify() throws Exception {
        ProductModifyRequestDto productAddRequestDto = new ProductModifyRequestDto("치킨", -1, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("가격은 0원 이상이여야 합니다.")));
    }

    @Test
    @DisplayName("상품의 이름이 빈 값이면 예외가 발생한다. - 상품 수정")
    void validateNull_modify() throws Exception {
        ProductModifyRequestDto productAddRequestDto = new ProductModifyRequestDto(null, 10000, "이미지");

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("상품 이름을 입력해주세요.")));
    }

    @Test
    @DisplayName("상품의 이미지가 빈 값이면 예외가 발생한다. - 상품 수정")
    void validateNullImage_modify() throws Exception {
        ProductModifyRequestDto productAddRequestDto = new ProductModifyRequestDto("치킨", 10000, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productAddRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("이미지 url을 입력해주세요.")));
    }
}
