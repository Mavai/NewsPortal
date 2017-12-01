package newsportal.controller;

import newsportal.model.Category;
import newsportal.model.ImageFile;
import newsportal.model.News;
import newsportal.repository.CategoryRepository;
import newsportal.repository.ImageFileRepository;
import newsportal.repository.NewsRepository;
import newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class NewsController {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ImageFileRepository imageFileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    NewsService newsService;

    @GetMapping("/news")
    public String list(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/news/new")
    public String newItem(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "form";
    }

    @Transactional
    @PostMapping("/news/new")
    public String create(@RequestParam String title, @RequestParam ArrayList<Long> categories, @RequestParam String lead,
                         @RequestParam String content, @RequestParam MultipartFile image) throws IOException {
        News newsItem = newsService.createNewsItem(title, categories, lead, content, image);
        newsRepository.save(newsItem);
        return "redirect:/news";
    }

    @GetMapping("/news/{id}")
    public String show(@PathVariable Long id, Model model) {
        News newsItem = newsRepository.getOne(id);
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", categoryRepository.findAll());
        return "show";
    }

    @DeleteMapping("/news/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        News newsItem = newsRepository.getOne(id);
        for (Category category : newsItem.getCategories()) {
            category.getNewsItems().remove(newsItem);
        }
        newsRepository.deleteById(id);
        return "redirect:/news";
    }
}
