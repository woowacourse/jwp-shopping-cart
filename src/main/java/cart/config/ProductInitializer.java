package cart.config;

import cart.entity.Product;
import cart.entity.dao.ProductDao;
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
        // 더미 데이터 작성
        Product pizza = new Product(
                "피자",
                "https://cdn.dominos.co.kr/admin/upload/goods/20200311_x8StB1t3.jpg",
                13000);
        Product salad = new Product(
                "샐러드",
                "https://m.subway.co.kr/upload/menu/K-%EB%B0%94%EB%B9%84%ED%81%90-%EC%83%90%EB%9F%AC%EB%93%9C-%EB%8B%A8%ED%92%88_20220413025007802.png",
                20000);
        Product chicken = new Product(
                "치킨",
                "https://cdn.thescoop.co.kr/news/photo/202010/41306_58347_1055.jpg",
                10000);

        // 더미 데이터 저장
        productDao.save(pizza);
        productDao.save(salad);
        productDao.save(chicken);
    }
}
