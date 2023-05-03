package cart.controller;

import cart.repository.user.UserRepository;
import cart.service.product.ProductCommandService;
import cart.service.product.ProductQueryService;
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
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserRepository userRepository;
}
