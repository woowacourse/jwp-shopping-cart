package cart.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.AddCartItemRequest;
import cart.dao.CartDao;
import cart.dao.H2ProductDao;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@Sql("/scheme.sql")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String authHeader;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO member (id, email, password) VALUES (30, 'email5@email', 'password5')");
        authHeader = "Basic " + new String(Base64.getEncoder().encode("email5@email:password5".getBytes()));
    }

    @Test
    void showCart() throws Exception {

        H2ProductDao productDao = new H2ProductDao(new NamedParameterJdbcTemplate(jdbcTemplate));
        ProductEntity productEntity = new ProductEntity("피자", "url", 20000);
        final Long productKey = productDao.save(productEntity);
        final CartEntity cartEntity = new CartEntity(30L, productKey);
        final Long cartId = cartDao.save(cartEntity);

        mockMvc.perform(get("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cartId))
                .andExpect(jsonPath("$[0].name").value("피자"))
                .andExpect(jsonPath("$[0].price").value("20000"));
    }

    @Test
    void showCartWithoutHeader() throws Exception {
        mockMvc.perform(get("/carts/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void showCartWithInvalidHeader() throws Exception {
        mockMvc.perform(get("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, "basemfds:pass"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addProduct() throws Exception {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(20L);
        final String body = objectMapper.writeValueAsString(addCartItemRequest);
        final MvcResult mvcResult = mockMvc.perform(post("/carts/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header(HttpHeaders.AUTHORIZATION, authHeader))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn();

        final String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);

        assertThat(location).contains("/carts/products/");
    }

    @Test
    void deleteProduct() throws Exception {

        final CartEntity cartEntity = new CartEntity(30L, 100L);
        final Long cartId = cartDao.save(cartEntity);

        mockMvc.perform(delete("/carts/"+cartId)
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                )
                .andExpect(status().isNoContent());
    }
}
