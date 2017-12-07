package newsportal.controller;

import newsportal.model.Category;
import newsportal.repository.CategoryRepository;
import newsportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;

    @GetMapping("/category/{id}")
    public String show(@PathVariable Long id, Model model) {
        Category category = categoryRepository.getOne(id);
        model.addAttribute("latestNews", newsRepository.findAllByCategories(category, PageRequest.of(0,10, Sort.Direction.DESC, "publishTime", "views")));
        model.addAttribute("topNews", newsRepository.findAllByCategories(category, PageRequest.of(0,10, Sort.Direction.DESC, "views")));
        model.addAttribute("categories", categoryRepository.findAll());
        return "showCategory";
    }
}
