package cart.service;

import cart.dao.CategoryDao;
import cart.dto.response.CategoryResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findCategories() {
        return categoryDao.findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }
}
