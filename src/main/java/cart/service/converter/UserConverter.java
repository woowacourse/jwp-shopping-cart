package cart.service.converter;

import cart.dto.request.SignUpRequestDto;
import cart.dto.response.UserResponseDto;
import cart.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static List<UserResponseDto> entitiesToResponseDtos(final List<UserEntity> users) {
        return users.stream()
                .map(userEntity ->
                        new UserResponseDto(userEntity.getEmail(), userEntity.getPassword(), userEntity.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    public static UserEntity requestDtoToEntity(final SignUpRequestDto requestDto) {
        return new UserEntity(requestDto.getEmail(), requestDto.getPassword(), requestDto.getName());
    }
}
