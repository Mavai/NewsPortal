package newsportal.service;

import newsportal.model.Category;
import newsportal.model.ImageFile;
import newsportal.model.News;
import newsportal.model.User;
import newsportal.repository.CategoryRepository;
import newsportal.repository.ImageFileRepository;
import newsportal.repository.NewsRepository;
import newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    ImageFileRepository imageFileRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public News createNewsItem(String title, ArrayList<Long> categories, String lead, String content, MultipartFile image, String writer) {
        News newsItem = new News();
        newsItem.setTitle(title);
        newsItem.setLead(lead);
        newsItem.setContent(content);
        newsItem.setPublishTime(LocalDateTime.now());
        addCategories(newsItem, categories);
        addImage(newsItem, image);
        return newsItem;
    }



    public News updateNewsItem(News newsItem, String title, ArrayList<Long> categories, String lead, String content, MultipartFile image, String writer) {
        newsItem.setTitle(title);
        newsItem.setLead(lead);
        newsItem.setContent(content);
        addCategories(newsItem, categories);
        addImage(newsItem, image);
        return newsItem;
    }

    private void addImage(News newsItem, MultipartFile image) {
        try {
            if (image.getBytes().length != 0) {
                ImageFile imageFile = new ImageFile();
                imageFile.setContent(image.getBytes());
                if (newsItem.getImage() != null) {
                    imageFileRepository.delete(newsItem.getImage());
                }
                imageFileRepository.save(imageFile);
                newsItem.setImage(imageFile);
            }
        } catch (Exception e) {
        }

    }

    private void addCategories(News newsItem, ArrayList<Long> categories) {
        if (categories != null) {
            deleteCategories(newsItem);
            for (Long categoryId : categories) {
                newsItem.setCategories(new ArrayList<>());
                Category category = categoryRepository.getOne(categoryId);
                newsItem.getCategories().add(category);
                category.getNewsItems().add(newsItem);
            }
        }
    }

    private void deleteCategories(News newsItem) {
        if (newsItem.getCategories() != null) {
            for (Category category : newsItem.getCategories()) {
                category.getNewsItems().remove(newsItem);
            }
        }
    }
}
