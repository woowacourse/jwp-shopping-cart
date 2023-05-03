package cart.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.domain.user.User;
import cart.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserViewControllerTest extends AbstractProductControllerTest {

    @Test
    void 유저_조회_테스트() throws Exception {
        //given
        final List<User> given = List.of(
                new User("a@a.com", "password1"),
                new User("b@b.com", "password2")
        );
        given(userSearchService.findAll()).willReturn(given);

        final List<UserResponse> expected = List.of(
                new UserResponse("a@a.com", "password1"),
                new UserResponse("b@b.com", "password2")
        );
        
        //when
        mockMvc.perform(get("/settings"))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("members", equalTo(expected)));
    }
}
