package woowacourse.shoppingcart.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.Product;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {

	private Long id;
	private String name;
	private Integer price;
	private String image;

	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getImageUrl()
		);
	}
}
