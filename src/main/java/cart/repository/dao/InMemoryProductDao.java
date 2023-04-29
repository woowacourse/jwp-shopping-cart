package cart.repository.dao;

import cart.repository.entity.ProductEntity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductDao implements ProductDao {

    private final List<ProductEntity> productEntities = new LinkedList<>();

    @Override
    public Long save(final ProductEntity productEntity) {
        final String name = productEntity.getName();
        final String imageUrl = productEntity.getImageUrl();
        final int price = productEntity.getPrice();

        if (productEntities.isEmpty()) {
            final Long id = 1L;
            productEntities.add(new ProductEntity(id, name, imageUrl, price));
            return id;
        }

        final int lastIndex = productEntities.size() - 1;
        final Long id = productEntities.get(lastIndex).getId() + 1;
        productEntities.add(new ProductEntity(id, name, imageUrl, price));
        return id;
    }

    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>(productEntities);
    }

    @Override
    public int update(final ProductEntity productEntity) {
        final Long id = productEntity.getId();
        final int index = removeProductByIdAndReturnIndex(id);
        productEntities.add(index, productEntity);
        return 1;
    }

    private int removeProductByIdAndReturnIndex(final Long id) {
        for (int i = 0; i < productEntities.size(); i++) {
            final ProductEntity productEntity = productEntities.get(i);
            if (productEntity.getId().equals(id)) {
                productEntities.remove(i);
                return i;
            }
        }
        throw new EmptyResultDataAccessException("해당 id가 존재하지 않습니다.", 1);
    }

    @Override
    public int delete(final Long id) {
        removeProductByIdAndReturnIndex(id);
        return 1;
    }
}
