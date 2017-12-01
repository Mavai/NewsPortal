package newsportal.service;

import newsportal.model.Category;
import newsportal.model.ImageFile;
import newsportal.model.News;
import newsportal.repository.CategoryRepository;
import newsportal.repository.ImageFileRepository;
import newsportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ImageFileRepository imageFileRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public News createNewsItem(String title, ArrayList<Long> categories, String lead, String content, MultipartFile image) {
        News newsItem = new News();
        newsItem.setTitle(title);
        newsItem.setLead(lead);
        newsItem.setContent(content);
        addCategories(newsItem, categories);
        addImage(newsItem, image);
        return newsItem;
    }

    private void addImage(News newsItem, MultipartFile image) {
        try {
            ImageFile imageFile = new ImageFile();
            imageFile.setContent(image.getBytes());
            newsItem.setImage(imageFile);
            imageFileRepository.save(imageFile);
        } catch (Exception e) {
        }
    }

    private void addCategories(News newsItem, ArrayList<Long> categories) {
        for (Long categoryId : categories) {
            newsItem.setCategories(new ArrayList<>());
            Category category = categoryRepository.getOne(categoryId);
            newsItem.getCategories().add(category);
            category.getNewsItems().add(newsItem);
        }
    }
}
