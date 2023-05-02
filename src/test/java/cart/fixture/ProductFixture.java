package cart.fixture;

import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.domain.Product;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;

public class ProductFixture {

    public static Product getProductWithId(final long id, final Product product) {
        return new Product(id, product.getName(), product.getImage(), product.getPrice());
    }

    public static class BLACKCAT {
        private static final String IMAGE_URL = "https://www.rd.com/wp-content/uploads/2021/01/GettyImages-1175550351.jpg";
        private static final String NAME = "블랙캣";
        private static final long PRICE = 10000L;

        public static final Product PRODUCT = new Product(new Name(NAME), new ImageUrl(IMAGE_URL), new Price(PRICE));
        public static final ProductSaveRequestDto SAVE_REQUEST = new ProductSaveRequestDto(NAME, IMAGE_URL, PRICE);
        public static final ProductUpdateRequestDto UPDATE_REQUEST = new ProductUpdateRequestDto(NAME, IMAGE_URL,
                PRICE);
    }

    public static class HERB {
        private static final String IMAGE_URL = "https://t1.daumcdn.net/cfile/tistory/99B332485A2FC6241D";
        private static final String NAME = "허브티";
        private static final long PRICE = 10000000L;

        public static final Product PRODUCT = new Product(new Name(NAME), new ImageUrl(IMAGE_URL), new Price(PRICE));
        public static final ProductSaveRequestDto SAVE_REQUEST = new ProductSaveRequestDto(NAME, IMAGE_URL, PRICE);
        public static final ProductUpdateRequestDto UPDATE_REQUEST = new ProductUpdateRequestDto(NAME, IMAGE_URL,
                PRICE);
    }
}
