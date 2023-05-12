package cart.dummydata;

import cart.dao.ProductDao;
import cart.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ProductInitializer implements ApplicationRunner {

    private final ProductDao productDao;

    @Autowired
    public ProductInitializer(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        Product product1 = Product.of("mouse", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 100000);
        Product product2 = Product.of("keyboard", "https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1", 250000);

        productDao.insert(product1);
        productDao.insert(product2);
    }

}
