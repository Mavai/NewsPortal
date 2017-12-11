package newsportal.controller;

import newsportal.model.Category;
import newsportal.model.News;
import newsportal.model.View;
import newsportal.model.Writer;
import newsportal.repository.*;
import newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    @Autowired
    WriterRepository writerRepository;
    @Autowired
    ViewRepository viewRepository;

    @ModelAttribute("modelNewsItem")
    private News getNewsItem() {
        return new News();
    }

    @GetMapping("/news")
    public String list(Model model) {
        model.addAttribute("latestNews", newsRepository.findAll(
                PageRequest.of(0, 10, Sort.Direction.DESC, "publishTime")));
        model.addAttribute("topNews", newsRepository.findAllOrderedByViewCountFromLastWeek(
                PageRequest.of(0, 10)));
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/news/{id}")
    public String show(@PathVariable Long id, Model model) {
        News newsItem = newsRepository.findById(id).get();
        View view = new View();
        view.setViewTime(LocalDateTime.now());
        view.setNewsItem(newsItem);
        viewRepository.save(view);
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", categoryRepository.findAll());
        return "show";
    }

    @Secured("WRITER")
    @GetMapping("/news/new")
    public String newItem(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("writers", writerRepository.findAll());
        return "form";
    }

    @Secured("WRITER")
    @Transactional
    @PostMapping("/news/new")
    public String create(@Valid @ModelAttribute("modelNewsItem") News modelNewsItem, BindingResult bindingResult, @RequestParam String title,
                         @RequestParam HashSet<Long> categories, @RequestParam String lead, @RequestParam String content,
                         @RequestParam MultipartFile newsImage, @RequestParam HashSet<Long> writers, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("writers", writerRepository.findAll());
            return "form";
        }
        News newsItem = newsService.createNewsItem(title, categories, lead, content, newsImage, writers);
        newsRepository.save(newsItem);
        return "redirect:/news/" + newsItem.getId();
    }

    @Transactional
    @Secured("WRITER")
    @DeleteMapping("/news/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        News newsItem = newsRepository.getOne(id);
        for (Category category : newsItem.getCategories()) {
            category.getNewsItems().remove(newsItem);
        }
        for (View view : newsItem.getViews()) {
            viewRepository.delete(view);
        }
        for (Writer writer : newsItem.getWriters()) {
            writer.getNewsItems().remove(newsItem);
        }
        newsRepository.deleteById(id);
        return "redirect:/news";
    }

    @Secured("WRITER")
    @GetMapping("/news/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        News newsItem = newsRepository.getOne(id);
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("writers", writerRepository.findAll());
        return "form";
    }

    @Secured("WRITER")
    @Transactional
    @PostMapping("/news/{id}")
    public String edit(@Valid @ModelAttribute("modelNewsItem") News modelNewsItem, BindingResult bindingResult, @RequestParam Long id,
                       @RequestParam String title, @RequestParam HashSet<Long> categories, @RequestParam String lead,
                       @RequestParam String content, @RequestParam MultipartFile newsImage, @RequestParam HashSet<Long> writers,
                       Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("writers", writerRepository.findAll());
            return "form";
        }
        News newsItem = newsRepository.getOne(id);
        newsService.updateNewsItem(newsItem, title, categories, lead, content, newsImage, writers);
        return "redirect:/news/" + id;
    }
}
