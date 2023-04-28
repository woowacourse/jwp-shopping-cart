package cart.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dao.ProductDao;
import cart.domain.Product;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(ReplaceUnderscores.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductDao productDao;

    private Matcher<Object> generatePropertiesMatcher(
            final Long id,
            final String name,
            final String image,
            final long price
    ) {
        return allOf(
                hasProperty("id", is(id)),
                hasProperty("name", is(name)),
                hasProperty("image", is(image)),
                hasProperty("price", is(price))
        );
    }

    @Test
    void 메인_페이지에_접근한다() throws Exception {
        // given
        final Product product1 = new Product("허브티", "tea.jpg", 1000L);
        final Product product2 = new Product("고양이", "cat.jpg", 1000000L);
        final Long id1 = productDao.saveAndGetId(product1);
        final Long id2 = productDao.saveAndGetId(product2);

        // expect
        mockMvc.perform(get("/"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute(
                        "products",
                        hasItem(generatePropertiesMatcher(id1, "허브티", "tea.jpg", 1000L))
                ))
                .andExpect(model().attribute(
                        "products",
                        hasItem(generatePropertiesMatcher(id2, "고양이", "cat.jpg", 1000000L))
                ))
                .andDo(print());
    }

    @Test
    void 관리자_페이지에_접근한다() throws Exception {
        // given
        final Product product1 = new Product("허브티", "tea.jpg", 1000L);
        final Product product2 = new Product("고양이", "cat.jpg", 1000000L);
        final Long id2 = productDao.saveAndGetId(product2);
        final Long id1 = productDao.saveAndGetId(product1);

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute(
                        "products",
                        hasItem(generatePropertiesMatcher(id1, "허브티", "tea.jpg", 1000L))
                ))
                .andExpect(model().attribute(
                        "products",
                        hasItem(generatePropertiesMatcher(id2, "고양이", "cat.jpg", 1000000L))
                ))
                .andDo(print());
    }

    @Test
    void 단일_조회_페이지에_접근한다() throws Exception {
        // given
        final Product product = new Product("허브티", "tea.jpg", 1000L);
        final Long id = productDao.saveAndGetId(product);

        // expect
        mockMvc.perform(get("/products/" + id))
                .andExpect(model().attribute(
                        "product",
                        is(generatePropertiesMatcher(id, "허브티", "tea.jpg", 1000L))
                ))
                .andDo(print());
    }

    @Test
    void 존재하지_않는_상품을_단일_조회_하는_경우_404_NotFound_를_응답한다() throws Exception {
        // expect
        mockMvc.perform(get("/products/" + Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("상품을 찾을 수 없습니다."))
                .andDo(print());
    }
}
