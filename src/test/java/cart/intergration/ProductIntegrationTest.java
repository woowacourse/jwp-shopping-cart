package cart.intergration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.request.member.MemberSignupRequest;
import cart.dto.request.product.ProductCreateRequest;
import cart.dto.request.product.ProductUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ProductIntegrationTest {
    private static final String PRODUCT_PATH = "/products";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("상품을 등록하고, 상품의 조회가 정상적으로 돼야 한다.")
    void saveProductAndFindProducts() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png");
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        // expect
        mockMvc.perform(get(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("1개의 상품이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].productId").value(productId))
                .andExpect(jsonPath("$.result[0].name").value("글렌피딕"))
                .andExpect(jsonPath("$.result[0].price").value(10000))
                .andExpect(jsonPath("$.result[0].imageUrl").value("https://image.com/image.png"));
    }

    @Test
    @DisplayName("상품을 등록하고, 삭제한 뒤 상품을 조회하면 상품이 없어야 한다.")
    void saveProductAndDeleteProductAndFindProducts() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png");
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        // when
        mockMvc.perform(delete(PRODUCT_PATH + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        mockMvc.perform(get(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("0개의 상품이 조회되었습니다."));
    }

    @Test
    @DisplayName("상품을 등록하고, 수정한 뒤 상품을 조회하면 수정이 반영되어야 한다.")
    void saveProductAndUpdateProductAndFindProducts() throws Exception {
        // given
        ProductCreateRequest createRequest = new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png");
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        // when
        ProductUpdateRequest updateRequest = new ProductUpdateRequest("글렌리벳", 20000, "https://image.com/image2.png");
        mockMvc.perform(patch(PRODUCT_PATH + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        mockMvc.perform(get(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("1개의 상품이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].productId").value(productId))
                .andExpect(jsonPath("$.result[0].name").value("글렌리벳"))
                .andExpect(jsonPath("$.result[0].price").value(20000))
                .andExpect(jsonPath("$.result[0].imageUrl").value("https://image.com/image2.png"));
    }

    @ParameterizedTest(name = "상품을 등록할 때 상품의 이름이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidProductName")
    void saveProduct_invalidProductName(String displayName, String productName) throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest(productName, 10000, "https://image.com/image.png");

        // expect
        mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.name").exists());
    }

    static Stream<Arguments> invalidProductName() {
        Map<String, String> testData = new HashMap<>();
        testData.put("비어있는 상품 이름", "");
        testData.put("비어있는 상품 이름2", " ");
        testData.put("null", null);
        testData.put("10글자를 넘는 이름", "12345678901");
        return testData.entrySet().stream()
                .map(data -> Arguments.of(data.getKey(), data.getValue()));
    }

    @ParameterizedTest(name = "상품을 등록할 때 상품의 가격이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidPrice")
    void saveProduct_invalidPrice(String displayName, int price) throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("글렌피딕", price, "https://image.com/image.png");

        // expect
        mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.price").exists());
    }

    static Stream<Arguments> invalidPrice() {
        Map<String, Integer> testData = new HashMap<>();
        testData.put("음수", -1000);
        testData.put("0원", 0);
        testData.put("1000만원을 초과하는 가격", 10_000_001);
        return testData.entrySet().stream()
                .map(data -> Arguments.of(data.getKey(), data.getValue()));
    }

    @ParameterizedTest(name = "상품을 등록할 때 이미지의 URL이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidImageUrl")
    void saveProduct_invalidImageUrl(String displayName, String imageUrl) throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("글렌피딕", 10000, imageUrl);

        // when
        mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.imageUrl").exists());
    }

    static Stream<Arguments> invalidImageUrl() {
        Map<String, String> testData = new HashMap<>();
        testData.put("형식에 맞지 않는 URL", "image.png");
        testData.put("잘못된 이미지 포맷", "https://image.com/image.exe");
        testData.put("100글자가 넘는 URL", "https://img.com/" + "123456789".repeat(9) + ".png"); // 101
        return testData.entrySet().stream()
                .map(data -> Arguments.of(data.getKey(), data.getValue()));
    }

    @ParameterizedTest(name = "상품을 수정할 때 상품의 이름이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidProductName")
    void updateProduct_invalidProductName(String displayName, String productName) throws Exception {
        // given
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png"))))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        ProductUpdateRequest request = new ProductUpdateRequest(productName, 10000, "https://image.com/image.png");

        // expect
        mockMvc.perform(patch(PRODUCT_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.name").exists());
    }

    @ParameterizedTest(name = "상품을 수정할 때 상품의 가격이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidPrice")
    void updateProduct_invalidPrice(String displayName, int price) throws Exception {
        // given
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png"))))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        ProductUpdateRequest request = new ProductUpdateRequest("글렌피딕", price, "https://image.com/image.png");

        // expect
        mockMvc.perform(patch(PRODUCT_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.price").exists());
    }

    @ParameterizedTest(name = "상품을 수정할 때 이미지의 URL이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    @MethodSource("invalidImageUrl")
    void updateProduct_invalidImageUrl(String displayName, String imageUrl) throws Exception {
        // given
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png"))))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        ProductUpdateRequest request = new ProductUpdateRequest("글렌피딕", 10000, imageUrl);

        // expect
        mockMvc.perform(patch(PRODUCT_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.imageUrl").exists());
    }

    @Test
    @DisplayName("상품이 장바구니에 담겨있으면 상품을 삭제할 수 없다.")
    void deleteProduct_productInCart() throws Exception {
        // given
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ProductCreateRequest("글렌피딕", 10000, "https://image.com/image.png"))))
                .andReturn();
        int productId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.result.productId");

        mockMvc.perform(post("/members" + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MemberSignupRequest("glen@naver.com", "123456"))));

        mockMvc.perform(post("/api/cart" + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, "Basic Z2xlbkBuYXZlci5jb206MTIzNDU2"))
                .andExpect(status().isOk());

        // expect
        mockMvc.perform(delete(PRODUCT_PATH + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 상품이 장바구니에 존재합니다."));
    }
}
