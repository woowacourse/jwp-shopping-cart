package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@SpringBootTest
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
    void 상품을_수정한다() throws Exception {
        // given
        final Product product = new Product("허브티", "tea.jpg", 1000L);
        final Long id = productDao.saveAndGetId(product).get();
        final ProductUpdateRequestDto updateRequestDto = new ProductUpdateRequestDto("고양이", "cat.jpg", 1000000L);
        final String request = objectMapper.writeValueAsString(updateRequestDto);

        // when
        mockMvc.perform(put("/products/" + id)
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

    @Test
    void 상품을_삭제한다() throws Exception {
        // given
        final Product product = new Product("허브티", "tea.jpg", 1000L);
        final Long id = productDao.saveAndGetId(product).get();

        // when
        mockMvc.perform(delete("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        // then
        assertThat(productDao.findAll()).isEmpty();
    }
}

