package cart.service;

import cart.dao.CategoryDao;
import cart.dto.response.CategoryResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findCategories() {
        return categoryDao.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }
}
