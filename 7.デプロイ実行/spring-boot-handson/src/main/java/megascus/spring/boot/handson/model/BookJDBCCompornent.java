package megascus.spring.boot.handson.model;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author 2568
 */
@Component
public class BookJDBCCompornent {

    @Autowired
    private JdbcTemplate jdbcTemplate; //JDBCTemplateをAutowiredアノテーションをつけて使用することで、SQLを直接発行することができる。

    public List<Book> searchByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM T_BOOK AS B WHERE B.TITLE LIKE ?", new Object[]{"%" + title + "%"}, (ResultSet rs, int i) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setIsbn(rs.getString("isbn"));
            book.setPrice(rs.getInt("price"));
            book.setTitle(rs.getString("title"));
            book.setSummary(rs.getString("summary"));
            return book;
        });

    }
}
