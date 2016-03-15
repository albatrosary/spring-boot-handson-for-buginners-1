package megascus.spring.boot.handson.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author megascus
 */
@Component //Springで管理されるコンポーネント
@Transactional //本来はここでトランザクションを管理する。今回はBookRespositoryにアクセスするだけなのであまり意味は無い
public class BookService {

    @Autowired //Springで管理されているコンポーネントからBookRepositoryであるクラスを取ってくる
    BookRepository repository;
    
    //ここから追加
    public List<Book> searchByTitle(String title) {
        return repository.searchByTitle(title);
    }
    //ここまで追加

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findOne(Long id) {
        return repository.findOne(id);
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public Book update(Book book) {
        //すでに登録されている場合のみ更新する
        if (repository.findOne(book.getId()) != null) {
            return repository.save(book);
        }
        return book;
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
