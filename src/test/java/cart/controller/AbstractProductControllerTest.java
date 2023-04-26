package cart.controller;

import cart.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class AbstractProductControllerTest {

    @MockBean
    protected ProductSearchService productSearchService;
    @Autowired
    protected MockMvc mockMvc;
}
