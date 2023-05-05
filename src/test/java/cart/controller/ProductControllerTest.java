package cart.controller;

import cart.product.controller.ProductController;
import cart.product.entity.Product;
import cart.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    public void findAll() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Test1", "test1.png", new BigDecimal(1000)),
                new Product(2L, "Test2", "test2.png", new BigDecimal(2000)),
                new Product(3L, "Test3", "test3.png", new BigDecimal(3000)),
                new Product(4L, "Test4", "test4.png", new BigDecimal(4000))
        );

        Mockito.when(productRepository.findAll())
                .thenReturn(products);

        mockMvc.perform(get("/admin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(model().attribute("products", products))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void createProduct() throws Exception {
        Product product = new Product("테스트", "image.png", new BigDecimal(5000));
        Mockito.when(productRepository.save(product))
                .thenReturn(new Product(1L, product.getName(), product.getImageUrl(), product.getPrice()));

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/admin/product")
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", "/admin/product/1"));
    }
}