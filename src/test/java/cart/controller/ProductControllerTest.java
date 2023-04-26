package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductDao productDao;

    @Test
    void 상품을_저장한다() throws Exception {
        // given
        final ProductSaveRequestDto dto = new ProductSaveRequestDto("허브티", "tea.jpg", 1000L);
        final String request = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        final Product result = productDao.findAll().get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("허브티"),
                () -> assertThat(result.getImage()).isEqualTo("tea.jpg"),
                () -> assertThat(result.getPrice()).isEqualTo(1000L)
        );
    }

    @Test
    void 메인_페이지_접근() throws Exception {
        // given
        final Product product1 = new Product("허브티", "tea.jpg", 1000L);
        final Product product2 = new Product("고양이", "cat.jpg", 1000000L);
        final Long id1 = productDao.saveAndGetId(product1).get();
        final Long id2 = productDao.saveAndGetId(product2).get();

        // expect
        mockMvc.perform(get("/"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("id", is(id1)),
                                hasProperty("name", is("허브티")),
                                hasProperty("image", is("tea.jpg")),
                                hasProperty("price", is(1000L))
                        )
                )))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("id", is(id2)),
                                hasProperty("name", is("고양이")),
                                hasProperty("image", is("cat.jpg")),
                                hasProperty("price", is(1000000L))
                        )
                )))
                .andDo(print());
    }

    @Test
    void 관리자_페이지_접근() throws Exception {
        // given
        final Product product1 = new Product("허브티", "tea.jpg", 1000L);
        final Product product2 = new Product("고양이", "cat.jpg", 1000000L);
        final Long id1 = productDao.saveAndGetId(product1).get();
        final Long id2 = productDao.saveAndGetId(product2).get();

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("id", is(id1)),
                                hasProperty("name", is("허브티")),
                                hasProperty("image", is("tea.jpg")),
                                hasProperty("price", is(1000L))
                        )
                )))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("id", is(id2)),
                                hasProperty("name", is("고양이")),
                                hasProperty("image", is("cat.jpg")),
                                hasProperty("price", is(1000000L))
                        )
                )))
                .andDo(print());
    }

    @Test
    void 상품을_수정한다() throws Exception {
        // given
        final Product product = new Product("허브티", "tea.jpg", 1000L);
        final Long id = productDao.saveAndGetId(product).get();
        final ProductUpdateRequestDto updateRequestDto = new ProductUpdateRequestDto("고양이", "cat.jpg", 1000000L);
        final String request = objectMapper.writeValueAsString(updateRequestDto);

        // when
        mockMvc.perform(patch("/products/" + id)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        // then
        final Product result = productDao.findAll().get(0);
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName()).isEqualTo("고양이"),
                () -> assertThat(result.getImage()).isEqualTo("cat.jpg"),
                () -> assertThat(result.getPrice()).isEqualTo(1000000L)
        );
    }
}

