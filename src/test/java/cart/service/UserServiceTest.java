package cart.service;

import cart.dao.UserDao;
import cart.dao.entity.UserEntity;
import cart.dto.ResponseUserDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    void 사용자를_조회한다() {
        //given
        when(userDao.findAll())
                .thenReturn(List.of(new UserEntity(1L, "huchu@woowahan.com", "1234567a!")));

        //when
        final List<ResponseUserDto> responseUserDtos = userService.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(responseUserDtos).hasSize(1);
            final ResponseUserDto responseUserDto = responseUserDtos.get(0);
            softly.assertThat(responseUserDto.getId()).isEqualTo(1L);
            softly.assertThat(responseUserDto.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(responseUserDto.getPassword()).isEqualTo("1234567a!");
        });
    }
}
