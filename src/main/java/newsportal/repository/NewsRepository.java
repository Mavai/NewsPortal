package newsportal.repository;

import newsportal.model.Category;
import newsportal.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>{

    Page<News> findAllByCategories(Category category, Pageable pageable);

    @Query(value = "SELECT n.id, n.content, n.lead, n.title, n.image_Id, n.publish_Time, Count(v.news_Item_Id) AS view_Count"
            + " FROM News n"
            + " LEFT JOIN View v"
            + " ON n.id = v.news_Item_Id"
            + " WHERE v.view_Time >= (CURRENT_DATE - INTERVAL '1 week')"
            + " GROUP BY n.id"
            + " ORDER BY view_Count DESC /*#pageable*/",
            countQuery = "SELECT COUNT(*) FROM News n, View v WHERE v.view_Time >= (CURRENT_DATE - INTERVAL '1 week')",
            nativeQuery = true)
    List<News> findAllOrderedByViewCountFromLastWeek(Pageable pageable);

    @Query(value = "SELECT n.id, n.content, n.lead, n.title, n.image_Id, n.publish_Time, Count(v.news_Item_Id) AS view_Count"
            + " FROM News n"
            + " LEFT JOIN View v"
            + " ON n.id = v.news_Item_Id"
            + " INNER JOIN Category_News_Items ca"
            + " ON n.id = ca.News_Items_Id"
            + " INNER JOIN Category c"
            + " ON c.id = ca.Categories_Id"
            + " WHERE v.view_Time >= (CURRENT_DATE - INTERVAL '1 week')"
            + " AND c.id = :categoryId"
            + " GROUP BY n.id"
            + " ORDER BY view_Count DESC /*#pageable*/",
            countQuery = "SELECT COUNT(*) FROM News n, View v WHERE v.view_Time >= (CURRENT_DATE - INTERVAL '1 week')",
            nativeQuery = true)
    List<News> findAllByCategoryOrderedByViewCountFromLastWeek(@Param("categoryId") Long categoryId, Pageable pageable);


}
