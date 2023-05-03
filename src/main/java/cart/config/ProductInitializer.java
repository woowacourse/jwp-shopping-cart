package cart.config;

import cart.entity.product.Product;
import cart.entity.product.ProductDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductInitializer implements ApplicationRunner {

    private final ProductDao productDao;

    public ProductInitializer(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        final Product pome = new Product("포메라니안", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F990BA4415C432CC601", 1300);
        final Product poodle = new Product("푸들", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9966693C5C43BCF227", 130000000);
        final Product dockHoon = new Product("닥스훈트", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F995C704C5C43BF6731", 1000);

        productDao.save(pome);
        productDao.save(poodle);
        productDao.save(dockHoon);
    }
}
