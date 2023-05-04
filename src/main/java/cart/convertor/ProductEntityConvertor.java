package cart.convertor;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityConvertor {
	public ProductEntity dtoToEntity (ProductCreateRequest request) {
		return new ProductEntity(null, request.getName(), request.getImageUrl(), request.getPrice());
	}

	public ProductEntity dtoToEntity(ProductUpdateRequest request) {
		return new ProductEntity(null, request.getName(), request.getImageUrl(), request.getPrice());
	}
}
