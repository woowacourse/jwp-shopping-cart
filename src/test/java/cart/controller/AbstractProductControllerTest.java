package cart.controller;

import cart.service.ProductCommandService;
import cart.service.ProductQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class AbstractProductControllerTest {

    @MockBean
    protected ProductQueryService productQueryService;
    @MockBean
    protected ProductCommandService productCommandService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
}
