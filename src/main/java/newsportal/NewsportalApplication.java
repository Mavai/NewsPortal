package newsportal;

import newsportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsportalApplication {

    @Autowired
    NewsRepository newsRepository;

    public static void main(String[] args) {

        SpringApplication.run(NewsportalApplication.class, args);
    }
}
