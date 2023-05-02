package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.service.CartService;
import cart.service.CustomerService;
import cart.service.dto.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerUnitTest {

    @MockBean
    private CartService cartService;
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        given(customerService.findIdByEmail(anyString())).willReturn(1L);
    }

    @Test
    void addProductToCart() throws Exception {
        // given
        given(cartService.save(any(CartRequest.class), anyLong())).willReturn(1L);
        String requestString = objectMapper.writeValueAsString(new CartRequest(1L));
        String testValue = "email:password";
        byte[] encodedBytes = Base64.encodeBase64(testValue.getBytes());
        String encodedString = new String(encodedBytes);

        // when, then
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestString)
                        .header("Authorization", "Basic " + encodedString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cart/1"));
    }
}