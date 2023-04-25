package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryProductDao implements ProductDao {

    private static final Map<Integer, ProductEntity> store = new HashMap<>();

    private static int pointer = 0;

    @Override
    public void insertProduct(ProductEntity productEntity) {
        productEntity.setId(++pointer);
        store.put(productEntity.getId(), productEntity);
    }

    @Override
    public List<ProductEntity> selectAllProducts() {
        return new ArrayList<>(store.values());
    }
}
