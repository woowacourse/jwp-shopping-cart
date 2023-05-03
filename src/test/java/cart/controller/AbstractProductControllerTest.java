package cart.controller;

import cart.service.AuthService;
import cart.service.CartCreateService;
import cart.service.CartSearchService;
import cart.service.ProductCreateService;
import cart.service.ProductDeleteService;
import cart.service.ProductSearchService;
import cart.service.ProductUpdateService;
import cart.service.UserSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class AbstractProductControllerTest {

    @MockBean
    protected AuthService authService;
    @MockBean
    protected CartCreateService cartCreateService;
    @MockBean
    protected UserSearchService userSearchService;
    @MockBean
    protected CartSearchService cartSearchService;
    @MockBean
    protected ProductSearchService productSearchService;
    @MockBean
    protected ProductUpdateService productUpdateService;
    @MockBean
    protected ProductCreateService productCreateService;
    @MockBean
    protected ProductDeleteService productDeleteService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
}
