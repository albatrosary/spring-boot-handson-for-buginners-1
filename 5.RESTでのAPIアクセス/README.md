# RESTでのAPIアクセス

前の章ではhtmlの表示を行いましたが、javascriptでデータのみを取り扱うことも多く、その場合はjson形式でデータがやり取りされます。

今回はRESTという形式でやり取りできるようにします。

RESTは以下のようなものです。

+ RESTはREpresentational State Transferの略です。
+ RESTではネットワーク上のリソースを一意なURLで表します。
+ 各リソース（URL）に対してGET、POST、PUT、DELETEでリクエストを送信しレスポンスを受け取ります。


## BookAPIControllerクラスの追加

以下のクラスを作成してください。

megascus.spring.boot.handson.controller.BookAPIController.java

```java:BookAPIController.java
package megascus.spring.boot.handson.controller;

import java.util.List;
import megascus.spring.boot.handson.model.Book;
import megascus.spring.boot.handson.model.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2568
 */
@RestController
@RequestMapping("api/books")
public class BookAPIController {
    
    @Autowired
    BookService service;

    @RequestMapping(method = RequestMethod.GET)
    List<Book> getBooks() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Book getBook(@PathVariable Long id) {
        return service.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Book postBook(@RequestBody Book book) {
        return service.save(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    Book postBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return service.update(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBook(@PathVariable Long id) {
        service.delete(id);
    }
    
}

```

## 起動する

プロジェクトを右クリックしてカスタム→spring-boot:runを選択してください。

起動させたら以下のURLにアクセスしてください。

http://localhost:8080/books

前の章で作成した機能でいくつかbookを登録してみます。
登録した後に以下のURLにアクセスしてみてください。

http://localhost:8080/api/books

JSON形式で同じデータが表示できているのが確認できると思います。


cURL(https://curl.haxx.se/)をインストールしている人がいる場合は以下のコマンドを入力してみてください。入力した後に画面をリロードするとbookが消えていることが確認できます。

```curl -X DELETE -d http://localhost:8080/api/books/${id}```

※${id}は一覧画面に表示されているIDを入力してください。


これで、RESTでのAPIアクセスが完了しました。
