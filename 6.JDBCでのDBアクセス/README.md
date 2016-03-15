# JDBCでのデータアクセス

今までの章ではデータベースアクセスにJPAを使用してきましたが、もちろんJDBCによるデータベースアクセスを行うことも出来ます。

## BookJDBCCompornentクラスの追加

以下のクラスを作成してください。

megascus.spring.boot.handson.model.BookJDBCCompornent.java

```java:BookJDBCCompornent.java
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
```
Spring-bootでJDBCを使用する場合は、直接StatementやResultSetを利用することも出来ますが、JDBCTemplateを使用するのが一般的です。
JDBCTemplate#query()メソッドの第三引数でResultSetからBookエンティティへのマッピングを行っています。

※今回、画面に表示するクラスとしてJPAのエンティティクラスを直接使用していますが、ただのgetter/setterを備えただけのクラス(いわゆるDTO)でも問題はありません。

## BookControllerクラスの修正

BookControllerクラスからBookJDBCCompornentクラスを使用するように修正します。

megascus.spring.boot.handson.controller.BookController.java

```java:BookController.java

package megascus.spring.boot.handson.controller;

import megascus.spring.boot.handson.model.BookService;
import megascus.spring.boot.handson.model.Book;
import megascus.spring.boot.handson.model.BookJDBCCompornent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author megascus
 */
@Controller
@RequestMapping("books")
public class BookController {

    // ここから追加
    @Autowired
    BookJDBCCompornent bookjdbc;

    @RequestMapping(method = RequestMethod.GET, params = "title")
    String search(Model model, @RequestParam String title) {
        model.addAttribute("books", bookjdbc.searchByTitle(title));
        return "books/list";
    }
    // ここまで追加

    @Autowired
    BookService service;
・
・
・
・
```

## list.html(検索画面)の修正

画面から検索が出来るようにlist.htmlに検索用のボタンを取り付けます。

```html:list.html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" >
    <head lang="ja">
        <meta charset="UTF-8"/>
        <title>一覧</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" />
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="http://necolas.github.io/normalize.css/3.0.2/normalize.css" />
    </head>
    <body>
        <div layout:fragment="content" class="col-sm-12">
            <!-- 文字列を使用する場合は@～という記法を使用する -->
            <a th:href="@{/books/create}" href="/books/create.html">新規登録</a>
            <!-- ここから追加 -->
            <form th:action="@{/books}" method="get">
                <div class="form-group">
                    <input class="form-control" type="text" name="title" />
                    <input class="btn btn-default" type="submit" name="form" value="検索"/>
                </div>
            </form>
            <!-- ここまで追加 -->
            <table class="table table-striped table-bordered table-condensed">
・
・
・
・
```

## 起動する

プロジェクトを右クリックしてカスタム→spring-boot:runを選択してください。

起動させたら以下のURLにアクセスしてください。

http://localhost:8080/books

前の章で作成した機能でいくつかbookを登録してみます。
登録した後に検索をしてみてSQLで定義したとおり、本の名前でフィルタリングができるようになっていることを確認してください。

これで、JDBCでのDBアクセスが完了しました。




## (参考) JPAで同じことをやる場合

JPAでSQLを使用した例のように検索を行いたい場合はJPQL(Java Persistence Query Language)を使用するのが一般的です。

JPQLはSQLに良く似た構文ですが、実際には違うものです。SQLはテーブルに対してクエリを発行しますが、JPQLはエンティティに対してクエリを発行します。

以下のように書き直してください。

package megascus.spring.boot.handson.model.BookRepository.java

```java:BookRepository.java
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
```

package megascus.spring.boot.handson.model.BookService.java

```java:BookRepository.java
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
・
・
・
・
```

megascus.spring.boot.handson.controller.BookController.java

```java:BookController.java

package megascus.spring.boot.handson.controller;

import megascus.spring.boot.handson.model.BookService;
import megascus.spring.boot.handson.model.Book;
import megascus.spring.boot.handson.model.BookJDBCCompornent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author megascus
 */
@Controller
@RequestMapping("books")
public class BookController {

    // ここから追加
    // 使用しないのでコメントアウト
    // @Autowired 
    // BookJDBCCompornent bookjdbc;

    @RequestMapping(method = RequestMethod.GET, params = "title")
    String search(Model model, @RequestParam String title) {
        // model.addAttribute("books", bookjdbc.searchByTitle(title));
        model.addAttribute("books", repository.searchByTitle(title));
        return "books/list";
    }
    // ここまで追加

    @Autowired
    BookService service;
・
・
・
・
```

これでSQLを使用しなくても検索が出来ます。