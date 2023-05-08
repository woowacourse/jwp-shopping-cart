package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@Sql("/scheme.sql")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductDao productDao;

    @Test
    void addProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("치킨", "test", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addProductWithInvalidName() throws Exception {
        final ProductRequest productRequest = new ProductRequest("  ", "test", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("상품명은 공백일 수 없습니다."));
    }

    @Test
    void addProductWithInvalidPrice() throws Exception {
        final ProductRequest productRequest = new ProductRequest("치킨", "test", -20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("가격은 0보다 커야 합니다."));
    }

    @Test
    void findById() throws Exception {

        ProductEntity productEntity = new ProductEntity("피자", "url", 20000);
        final Long productKey = productDao.save(productEntity);

        mockMvc.perform(get("/products/" + productKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("피자"))
                .andExpect(jsonPath("$.price").value(20000))
                .andExpect(jsonPath("$.imageUrl").value("url"));
    }

    @Test
    void updateProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity("피자", "url", 20000);
        final Long productKey = productDao.save(productEntity);

        final ProductRequest productRequest = new ProductRequest("피자2", "test.url", 230000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/" + productKey)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("피자2"))
                .andExpect(jsonPath("$.price").value(230000))
                .andExpect(jsonPath("$.imageUrl").value("test.url"));
    }

    @Test
    void deleteProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity("피자", "url", 20000);
        final Long productKey = productDao.save(productEntity);
        mockMvc.perform(delete("/products/" + productKey))
                .andExpect(status().isNoContent());
    }
}
