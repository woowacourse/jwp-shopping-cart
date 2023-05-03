package cart.controller;

import cart.repository.user.UserRepository;
import cart.service.cart.CartCommandService;
import cart.service.cart.CartQueryService;
import cart.service.product.ProductCommandService;
import cart.service.product.ProductQueryService;
import cart.service.user.UserQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class AbstractControllerTest {

    @MockBean
    protected ProductQueryService productQueryService;
    @MockBean
    protected ProductCommandService productCommandService;
    @MockBean
    protected UserQueryService userQueryService;
    @MockBean
    protected CartCommandService cartCommandService;
    @MockBean
    protected CartQueryService cartQueryService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserRepository userRepository;
}
