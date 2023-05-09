package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.config.WebConfig;
import cart.dao.MemberDao;
import cart.dto.CartItemResponse;
import cart.dto.CartRequest;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.service.CartService;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

@Import(WebConfig.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

  private static final String AUTHORIZATION = "Authorization";
  private static final String BASIC_TYPE = "BASIC ";
  private static final String EMAIL = "test";
  private static final String PASSWORD = "test";
  private static final String DELIMITER = ":";
  private static final String INVALID_PASSWORD = "INVALID";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CartService cartService;

  @MockBean
  private ProductService productService;

  @MockBean
  private MemberDao memberDao;

  @BeforeEach
  void setUp() {
    given(memberDao.findByMemberEntity(any())).willReturn(
        Optional.of(new MemberEntity(1L, "test", "test")));
  }

  @Test
  void findCartItems() throws Exception {
    given(productService.findById(any())).willReturn(List.of(
            new CartItemResponse(1, new ProductEntity(1L, "치킨", "chicken", 10000)),
            new CartItemResponse(2, new ProductEntity(2L, "피자", "pizza", 20000))));

    mockMvc.perform(get("/carts")
            .header(AUTHORIZATION, BASIC_TYPE + Base64Coder.encodeString(EMAIL + DELIMITER + PASSWORD)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2));

    mockMvc.perform(get("/carts")
            .header(AUTHORIZATION, BASIC_TYPE + Base64Coder.encodeString(EMAIL + DELIMITER + INVALID_PASSWORD)))
        .andExpect(status().isForbidden());
  }

  @Test
  void noAuthentication() throws Exception {
    given(memberDao.findByMemberEntity(any())).willReturn(
        Optional.empty());

    mockMvc.perform(get("/carts")
            .header(AUTHORIZATION, BASIC_TYPE + Base64Coder.encodeString(EMAIL + DELIMITER + INVALID_PASSWORD)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void addCart() throws Exception {
    final String content = objectMapper.writeValueAsString(new CartRequest(1L));

    mockMvc.perform(post("/carts")
            .header(AUTHORIZATION, BASIC_TYPE + Base64Coder.encodeString(EMAIL + DELIMITER + PASSWORD))
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void deleteCart() throws Exception {
    mockMvc.perform(delete("/carts/" + 1L)
            .header(AUTHORIZATION, BASIC_TYPE + Base64Coder.encodeString(EMAIL + DELIMITER + PASSWORD)))
        .andExpect(status().isNoContent());
  }
}
