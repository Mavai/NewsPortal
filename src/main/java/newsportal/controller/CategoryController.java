package newsportal.controller;

import newsportal.model.Category;
import newsportal.model.News;
import newsportal.repository.CategoryRepository;
import newsportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Controller
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;

    @Transactional
    @GetMapping("/category/{id}/page/{pageCount}")
    public String show(@PathVariable Long id, @PathVariable Integer pageCount ,Model model) {
        Category category = categoryRepository.getOne(id);
        Page<News> page = newsRepository.findAllByCategories(category,
                PageRequest.of(pageCount - 1,NewsController.PAGE_SIZE, Sort.Direction.DESC, "publishTime"));
        model.addAttribute("latestNews", page);
        model.addAttribute("topNews", newsRepository.findAllByCategoryOrderedByViewCountFromLastWeek(category.getId(),
                PageRequest.of(0,5)));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("pageCount", page.getTotalPages());
        return "index";
    }
}
