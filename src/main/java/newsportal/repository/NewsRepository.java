package newsportal.repository;

import newsportal.model.Category;
import newsportal.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>{

    List<News> findAllByCategories(Category category, Pageable pageable);
}
