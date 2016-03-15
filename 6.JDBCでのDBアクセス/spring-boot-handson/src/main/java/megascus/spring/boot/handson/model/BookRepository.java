
package megascus.spring.boot.handson.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author megascus
 */
@Repository //Springにコンポーネントとして管理されるDBにアクセスするためのクラス 
public interface BookRepository extends JpaRepository<Book, Long> {
    //ここから追加
    // BookJDBCCompornentのSQLをJPQLで書き直した例。
    @Query("select b from Book b where b.title like %:title%")
    List<Book> searchByTitle(String title);
    //ここまで追加
}
