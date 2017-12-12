package newsportal.service;

import newsportal.model.Category;
import newsportal.model.News;
import newsportal.model.Writer;
import newsportal.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ImageFileRepository imageFileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WriterRepository writerRepository;
    @Autowired
    ViewRepository viewRepository;
    @Autowired
    NewsService newsService;

    @Test
    @Transactional
    public void categoriesAddedCorrectly() {
        Category category1 = setUpCategory("category1");
        Category category2 = setUpCategory("category2");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        News newsItem = new News();
        HashSet<Long> categoryIds = new HashSet<>(Arrays.asList(new Long[]{category1.getId(), category2.getId()}));
        newsService.addCategories(newsItem, categoryIds);
        assertTrue(newsItem.getCategories().contains(category1));
        assertTrue(newsItem.getCategories().contains(category2));
    }

    private Category setUpCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setNewsItems(new ArrayList<>());
        return category;
    }

}