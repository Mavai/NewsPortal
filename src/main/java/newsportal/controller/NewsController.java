package newsportal.controller;

import newsportal.model.Category;
import newsportal.model.News;
import newsportal.repository.CategoryRepository;
import newsportal.repository.ImageFileRepository;
import newsportal.repository.NewsRepository;
import newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
        model.addAttribute("latestNews", newsRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "publishTime", "views")));
        model.addAttribute("topNews", newsRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "views")));
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/news/new")
    public String newItem(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "form";
    }

    @Secured("WRITER")
    @Transactional
    @PostMapping("/news/new")
    public String create(@RequestParam String title, @RequestParam(required = false) ArrayList<Long> categories, @RequestParam String lead,
                         @RequestParam String content, @RequestParam MultipartFile image, @RequestParam(required = false) String writer) {
        News newsItem = newsService.createNewsItem(title, categories, lead, content, image, writer);
        newsRepository.save(newsItem);
        return "redirect:/news";
    }

    @Transactional
    @GetMapping("/news/{id}")
    public String show(@PathVariable Long id, Model model) {
        News newsItem = newsRepository.getOne(id);
        newsItem.incrementViews();
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", categoryRepository.findAll());
        return "show";
    }

    @Secured("WRITER")
    @DeleteMapping("/news/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        News newsItem = newsRepository.getOne(id);
        for (Category category : newsItem.getCategories()) {
            category.getNewsItems().remove(newsItem);
        }
        newsRepository.deleteById(id);
        return "redirect:/news";
    }

    @GetMapping("/news/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        News newsItem = newsRepository.getOne(id);
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", categoryRepository.findAll());
        return "form";
    }

    @Secured("")
    @Transactional
    @PostMapping("/news/{id}")
    public String edit(@RequestParam Long id, @RequestParam String title, @RequestParam(required = false) ArrayList<Long> categories,
                       @RequestParam String lead, @RequestParam String content, @RequestParam MultipartFile image, @RequestParam(required = false) String writer) {
        News newsItem = newsRepository.getOne(id);
        newsService.updateNewsItem(newsItem, title, categories, lead, content, image, writer);
        return "redirect:/news/" + id;
    }
}
