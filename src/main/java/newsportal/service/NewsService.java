package newsportal.service;

import newsportal.model.*;
import newsportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class NewsService {

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

    public News createNewsItem(String title, HashSet<Long> categories, String lead, String content, MultipartFile image,
                               HashSet<Long> writers) throws IOException {
        News newsItem = new News();
        newsItem.setPublishTime(LocalDateTime.now());
        updateNewsItem(newsItem, title, categories, lead, content, image, writers);
        return newsItem;
    }


    public News updateNewsItem(News newsItem, String title, HashSet<Long> categories, String lead, String content,
                               MultipartFile image, HashSet<Long> writers) throws IOException {
        newsItem.setTitle(title);
        newsItem.setLead(lead);
        newsItem.setContent(content);
        addCategories(newsItem, categories);
        addImage(newsItem, image);
        addWriters(newsItem, writers);
        return newsItem;
    }

    public void addImage(News newsItem, MultipartFile image) throws IOException {
        if (image.getBytes().length != 0) {
            ImageFile imageFile = new ImageFile();
            imageFile.setContent(image.getBytes());
            if (newsItem.getImage() != null) {
                imageFileRepository.delete(newsItem.getImage());
            }
            imageFileRepository.save(imageFile);
            newsItem.setImage(imageFile);
        }
    }

    public void addCategories(News newsItem, HashSet<Long> categories) {
        if (categories != null) {
            deleteCategories(newsItem);
            newsItem.setCategories(new ArrayList<>());
            for (Long categoryId : categories) {
                Category category = categoryRepository.getOne(categoryId);
                newsItem.getCategories().add(category);
                category.getNewsItems().add(newsItem);
            }
        }
    }

    public void addWriters(News newsItem, HashSet<Long> writers) {
        if (writers != null) {
            deleteWriters(newsItem);
            for (Long writerId : writers) {
                newsItem.setWriters(new ArrayList<>());
                Writer writer = writerRepository.getOne(writerId);
                newsItem.getWriters().add(writer);
                writer.getNewsItems().add(newsItem);
            }
        }
    }

    public void deleteCategories(News newsItem) {
        if (newsItem.getCategories() != null) {
            for (Category category : newsItem.getCategories()) {
                category.getNewsItems().remove(newsItem);
            }
        }
    }

    public void deleteWriters(News newsItem) {
        if (newsItem.getWriters() != null) {
            for (Writer writer : newsItem.getWriters()) {
                writer.getNewsItems().remove(newsItem);
            }
        }
    }

    public void deleteReferences(News newsItem) {
        for (Category category : newsItem.getCategories()) {
            category.getNewsItems().remove(newsItem);
        }
        for (View view : newsItem.getViews()) {
            viewRepository.delete(view);
        }
        for (Writer writer : newsItem.getWriters()) {
            writer.getNewsItems().remove(newsItem);
        }
    }
}
