package cart.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/로 get 요청을 보내면 상품목록에 대한 html 파일을 응답한다")
    void loadHome() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(
            "<img alt=\"상품 이미지\" src=\"https://searchad-phinf.pstatic.net/MjAyMjEyMjdfMTE1/MDAxNjcyMTAxNTI0Nzg4.WfiSlsy9fTUQJ6q2FTGOaaOVU0QpSB0U1LvplKZQXzIg.H4UgI0VbKUszP7mzC3qhwpSMe15DluJnxjxVGDq_QUgg.PNG/451708-1fa87663-02e3-4303-b8a9-d7eea3676018.png?type=f160_160\">",
            "<p class=\"product-name\">피자</p>", "<p class=\"product-price\">13000</p>",
            "<img alt=\"상품 이미지\" src=\"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjdfMTI2%2FMDAxNjc5OTI1ODQ4NTgy.6RT9z-i5prsnwwc-6B9TaK6Q0Zcgsd3TeDGiUdqyDRIg.rW2kMtzBKNFhWWXyr_X2bZfR15AEPUOz-VJnqVaP0jEg.JPEG.koreasinju%2FIMG_3379.jpg&amp;type=ff332_332\">",
            "<p class=\"product-name\">치킨</p>", "<p class=\"product-price\">27000</p>",
            "<img alt=\"상품 이미지\" src=\"https://searchad-phinf.pstatic.net/MjAyMzA0MThfMjk0/MDAxNjgxODAwNDY4NjU4.FJcjmoGsxyCq0nZqlcmoAL2mwX8mM9ny9DdliQcqGZ0g.9cGk2IQHfPIm2-ABelEOY1cc-_8NBQgPMgPpjFZkGFEg.JPEG/2814800-bb4236af-96dd-42e7-8256-32ffaa73de52.jpg?type=f160_160\">",
            "<p class=\"product-name\">샐러드</p>", "<p class=\"product-price\">9500</p>");
    }

    @Test
    @DisplayName("/admin로 get 요청을 보내면 관리자 페이지에 대한 html 파일을 응답한다")
    void loadAdmin() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(
            "<td>피자</td>", "<td>13000</td>",
            "<img style=\"max-width: 100px;\" src=\"https://searchad-phinf.pstatic.net/MjAyMjEyMjdfMTE1/MDAxNjcyMTAxNTI0Nzg4.WfiSlsy9fTUQJ6q2FTGOaaOVU0QpSB0U1LvplKZQXzIg.H4UgI0VbKUszP7mzC3qhwpSMe15DluJnxjxVGDq_QUgg.PNG/451708-1fa87663-02e3-4303-b8a9-d7eea3676018.png?type=f160_160\">",
            "<td>치킨</td>", "<td>27000</td>",
            "<img style=\"max-width: 100px;\" src=\"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjdfMTI2%2FMDAxNjc5OTI1ODQ4NTgy.6RT9z-i5prsnwwc-6B9TaK6Q0Zcgsd3TeDGiUdqyDRIg.rW2kMtzBKNFhWWXyr_X2bZfR15AEPUOz-VJnqVaP0jEg.JPEG.koreasinju%2FIMG_3379.jpg&amp;type=ff332_332\">",
            "<td>샐러드</td>", "<td>9500</td>",
            "<img style=\"max-width: 100px;\" src=\"https://searchad-phinf.pstatic.net/MjAyMzA0MThfMjk0/MDAxNjgxODAwNDY4NjU4.FJcjmoGsxyCq0nZqlcmoAL2mwX8mM9ny9DdliQcqGZ0g.9cGk2IQHfPIm2-ABelEOY1cc-_8NBQgPMgPpjFZkGFEg.JPEG/2814800-bb4236af-96dd-42e7-8256-32ffaa73de52.jpg?type=f160_160\">"
        );

    }
}