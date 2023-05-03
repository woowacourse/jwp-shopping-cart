package cart.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductApiController.class)
class ProductApiControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //    @MockBean
//    ProductService productService;
    @SpyBean
    ProductService productService;

    @MockBean
    ProductDao productDao;

    @Test
    @DisplayName("/products로 POST 요청과 상품의 정보를 보내면, HTTP 201 코드와 상품이 등록된다.")
    void saveProduct는_상품을_저장하고_created상태코드를_반환한다() throws Exception {
        //given
        ProductRequestDto request = new ProductRequestDto("치킨", "치킨image", 20000L);
//        given(productService.saveProduct(request))
//                .willReturn(1L);

        //when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("상품을 생성할 때 이름이 25자를 초과하면, HTTP 400 코드와 에러 메시지가 반환된다")
    void saveProduct예외1_name의_길이가_25자_초과() throws Exception {
        // given
        ProductRequestDto request = new ProductRequestDto("치킨ㄴㅇ라ㅣ낭러;ㅣㅁㄴ얼;ㅣㅁㄴ아ㅓㄹ;ㅣㄴ멍리;ㄴ얼;ㅣㅁ넝ㄹ;ㅣㄴ마얼;ㅁㄴㅇㄹㅁㄴㅇㄹㅇㄴㄹㄴㅇㄹ",
                "치킨image", 20000L);
//        given(productService.saveProduct(request))
//                .willReturn(1L);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품의 이름은 1자 ~ 25자여야 합니다"))
                .andDo(print());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("상품을 생성할 때 이름이 null이면, HTTP 400 코드와 검증 메시지가 반환된다.")
    void saveProduct예외2_name이_Blank(String name) throws Exception {
        // given
        ProductRequestDto request = new ProductRequestDto(name, "치킨image", 20000L);
//        given(productService.saveProduct(request))
//                .willReturn(1L);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품의 이름은 비어있을 수 없습니다."))
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1000L, 100_000_001L, 200_000_000L})
    @DisplayName("상품을 생성할 때 가격이 음수이거나 1억 이상이면 HTTP 400 코드와 검증 메시지가 반환된다.")
    void saveProduct예외3_price가_음수이거나_1억_초과(Long price) throws Exception {
        // given
        ProductRequestDto request = new ProductRequestDto("치킨", "치킨image", price);
        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품 가격은 0 ~ 100000000원까지 가능합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("상품을 생성할 때 가격이 null이면, HTTP 400 코드와 검증 메시지가 반환된다.")
    void saveProduct예외4_price가_Blank() throws Exception {
        // given
        ProductRequestDto request = new ProductRequestDto("치킨", "치킨image", null);
        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"치킨\",\"image\":\"치킨image\",\"price\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품의 가격은 비어있을 수 없습니다."))
                .andDo(print());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("상품을 생성할 때 이미지가 빈 값이면 HTTP 400 코드와 검증 메시지가 반환된다.")
    void saveProduct예외5_image가_Blank(String image) throws Exception {
        // given
        ProductRequestDto request = new ProductRequestDto("치킨", image, 20000L);

        // expect
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품의 이미지 url은 비어있을 수 없습니다."))
                .andDo(print());

    }

    @Test
    @DisplayName("/products/{id}로 put 요청과 상품의 정보를 보내면, HTTP 200 코드와 함께 상품이 수정된다.")
    void updateProduct는_요청한_id를_가진_상품의_정보를_수정하고_200코드를_반환한다() throws Exception {
        // given
        long productId = 1;
        Product savedProduct = new Product(1L, "치킨", "치킨image", 15000L);
        ProductRequestDto request = new ProductRequestDto("치킨", "치킨image", 20000L);

        given(productDao.findProductById(productId))
                .willReturn(Optional.of(savedProduct));

        // expect
        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.name").value("치킨"))
                .andExpect(jsonPath("$.image").value("치킨image"))
                .andExpect(jsonPath("$.price").value(20000L));
    }


    @Test
    @DisplayName("/products/{id}로 put 요청을 보낼 때 해당하는 상품ID가 없으면, HTTP 400 코드와 예외 메시지가 반환된다.")
    void updateProduct예외1_productId가_db에_없을때() throws Exception {
        // given
        long productId = 1;
        ProductRequestDto request = new ProductRequestDto("치킨", "치킨image", 20000L);

        given(productDao.findProductById(productId))
                .willReturn(Optional.empty());

        // expect
        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 상품이 존재하지 않습니다"));
    }

    @Test
    @DisplayName("/products/{id}로 put 요청을 보낼 때, 수정하려는 이름이 25자를 초과하면 HTTP 400 코드와 예외 메시지가 반환된다.")
    void updateProduct예외2_name의_길이가_25자_초과() throws Exception {
        // given
        long productId = 1;
        ProductRequestDto request = new ProductRequestDto("치킨ㅁㄴ아룸니ㅏㅇ러;민아ㅓㄹ;ㅣㅁ나얼;미ㅏㅈ더리;맞더맂더라ㅣㅁㅈ덜ㄷㄹ저ㅣㄷ러짇란ㄹㄷㄹㄴㅈㄸㅈㄹㅈㄷㄹㅈㄷ",
                "치킨image", 20000L);

        // expect
        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품의 이름은 1자 ~ 25자여야 합니다"))
                .andDo(print());
    }

    @Test
    @DisplayName("/products/{productId}로 DELETE 요청을 보내면, HTTP 204 코드와 상품이 삭제된다.")
    void deleteProduct는_상품을_삭제하고_200상태코드를_반환한다() throws Exception {
        // given
        long productId = 1;
        given(productDao.findProductById(productId))
                .willReturn(Optional.of(new Product(productId, "치킨", "치킨image", 20000L)));

        // expect
        mockMvc.perform(delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("/products/{id}로 DELETE 요청을 보낼 때 해당 상품이 없으면 HTTP 400 코드와 예외 메시지가 반환되어야 한다.")
    void deleteProduct예외1_삭제하려는_상품의_productId가_db에_없을때() throws Exception {
        // given
        long productId = 1;
        given(productDao.findProductById(productId))
                .willReturn(Optional.empty());

        // expect
        mockMvc.perform(delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 상품이 존재하지 않습니다"));
    }

    @Test
    @DisplayName("/products로 GET 요청을 보내면 HTTP 200 코드와 함께 상품이 조회되어야 한다.")
    void findAllProducts_success() throws Exception {
        // given
        List<ProductDto> products = List.of(new ProductDto(1L, "치킨", "치킨image", 20000L),
                new ProductDto(2L, "치킨", "치킨image", 20000L));

        willReturn(products)
                .given(productService)
                .findAllProducts();

        // expect
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1L))
                .andExpect(jsonPath("$[0].name").value("치킨"))
                .andExpect(jsonPath("$[0].image").value("치킨image"))
                .andExpect(jsonPath("$[0].price").value(20000L))
                .andDo(print());
    }
}
