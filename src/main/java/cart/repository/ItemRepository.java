package cart.repository;

import cart.repository.dao.ItemDao;
import cart.domain.item.Item;
import cart.exception.item.ItemNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private static final String NOT_FOUND_MESSAGE = "일치하는 상품을 찾을 수 없습니다.";
    private static final int CORRECT_ROW_COUNT = 1;

    private final ItemDao itemDao;

    public ItemRepository(final ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public Item insert(Item item) {
        return itemDao.insert(item);
    }

    public Optional<Item> findById(Long id) {
        return itemDao.findById(id);
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public void update(Item item) {
        int updateRecordCount = itemDao.update(item);

        if (updateRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemNotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    public void delete(Long id) {
        int deleteRecordCount = itemDao.deleteById(id);

        if (deleteRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemNotFoundException(NOT_FOUND_MESSAGE);
        }
    }
}
