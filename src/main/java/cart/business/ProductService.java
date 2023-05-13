package cart.business;

import cart.business.domain.Product;
import cart.business.domain.Products;
import cart.entity.ProductEntity;
import cart.persistence.ProductDao;
import cart.presentation.dto.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;
    private Products products;

    public ProductService(ProductDao productDao) {
        initSetting();
        this.productDao = productDao;
    }

    private void initSetting() {
        Product dog = new Product("강아지", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTJfMTM5%2FMDAxNjgxMjYzNTU2NDI2.wlJys88BgEe2MzQrd2k5jjtXsObAZaOM4eidDcM3iLUg.5eE5nUvqLadE0MwlF9c8XLOgqghimMWQU2psfcRuvFYg.PNG.noblecase%2F20230412_102917_5.png&type=a340", 10000);
        Product cat = new Product("고양이", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTBfMjcz%2FMDAxNjgxMTAwOTc5Nzg3.MEOt2vmlKWIlW4PQFfgHPILk0dJxwX42KzrDVu4puSwg.GcSSR6FJWup8Uo1H0xo0_4FuIMhJYJpw6tUmpKP9-Wsg.JPEG.catopia9%2FDSC01276.JPG&type=a340", 20000);
        Product hamster = new Product("햄스터", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MjJfMTcx%2FMDAxNjgyMTMzNzQ5MjQ2.DPd6D6NUbKSAOLBVosis9Ptz_lBGkyT4lncgLV0buZUg.KK0-N7fzYAy43jlHd9-4hQJ2CYu7RRqV3UWUi29FQJgg.JPEG.smkh15112%2FIMG_4554.JPG&type=a340", 5000);
        this.products = new Products(new ArrayList<>(List.of(dog, cat, hamster)));
    }

    @Transactional
    public Integer create(ProductRequest request) {
        Product product = new Product(request.getName(), request.getUrl(), request.getPrice());
        products.isSameProductExist(product);

        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> read() {
        return productDao.findAll();
    }

    @Transactional
    public Integer update(Integer id, ProductRequest request) {
        Product product = new Product(request.getName(), request.getUrl(), request.getPrice());
        return productDao.update(id, product);
    }

    @Transactional
    public Integer delete(Integer id) {
        return productDao.remove(id);
    }

    private ProductEntity makeProductFromRequest(ProductRequest request) {
        return new ProductEntity(null, request.getName(), request.getUrl(), request.getPrice());
    }

    public Products getProducts() {
        return products;
    }
}
