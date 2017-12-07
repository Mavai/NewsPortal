package newsportal.repository;

import newsportal.model.Category;
import newsportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{


}
