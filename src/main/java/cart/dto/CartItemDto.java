package cart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CartItemDto {

    private Long id;
    private String productName;
    private String productImageUrl;
    private Integer productPrice;
}
