package cart.dtomapper;

import cart.dto.response.CustomerResponseDto;
import cart.entity.customer.CustomerEntity;
import java.util.List;
import java.util.stream.Collectors;

public final class CustomerResponseDtoMapper {

    private CustomerResponseDtoMapper() {

    }

    public static CustomerResponseDto mapToDto(final CustomerEntity customerEntity) {
        return new CustomerResponseDto(
            customerEntity.getId(),
            customerEntity.getEmail(),
            customerEntity.getPassword()
        );
    }

    public static List<CustomerResponseDto> asList(final List<CustomerEntity> customerEntities) {
        return customerEntities.stream()
            .map(CustomerResponseDtoMapper::mapToDto)
            .collect(Collectors.toList());
    }
}
