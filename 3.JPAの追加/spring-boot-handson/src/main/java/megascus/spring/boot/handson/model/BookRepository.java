
package megascus.spring.boot.handson.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author megascus
 */
@Repository //Springにコンポーネントとして管理されるDBにアクセスするためのクラス 
public interface BookRepository extends JpaRepository<Book, Long> {
}
