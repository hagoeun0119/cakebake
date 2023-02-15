package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Category;
import springjpasideproject.cakebake.domain.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findOne(Long categoryId) { return categoryRepository.findOne(categoryId); }

    public Category createCategory(String name) {

        List<Category> categoryList = categoryRepository.findByName(name);

        if (categoryList.isEmpty()) {
                Category category = new Category(name);
                categoryRepository.save(category);
                return category;
        }

        return categoryList.get(0);
    }

}
