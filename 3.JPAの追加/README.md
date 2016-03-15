# JPAの追加

## JPAとは

JPAはO/Rマッパーの仕様です。Javaのオブジェクト(R)をリレーショナルデータベース(R)に格納できる形に変換(mapping)します。

JPAの特徴としては、xmlを作成しなくてもJavaのクラスを作成するだけでJavaのオブジェクトとテーブル間の変換の定義が行えることです。
昔はO/Rマッパーでは変換のルールを定義するためにxmlを記述する必要があったりと大変なものでした。そちらの労力が削られています。

JPA自体は仕様しか定めていません。Spring-bootではJPAを扱うための実装としてHibernate 4が採用されています。

## 実際に使用してみる

### Bookクラスの追加

まずはJPAでJavaのオブジェクトとテーブルをどのようにマッピングするのかを見たいと思います。

以下のクラスを追加してください。


megascus.spring.boot.handson.model.Book.java ※パッケージに注意

```java:Book.java

package megascus.spring.boot.handson.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author megascus
 */
@Entity // JPAのエンティティ(DBとJavaをマッピングしているクラス)
@Table(name = "T_BOOK") // DBに作成されるテーブル名称
public class Book implements Serializable {

    @Id // Primary Key
    @GeneratedValue //自動採番
    // @javax.persistence.Column(name = "id") DBに作成されるカラム名はデフォルトではjavaのフィールド名称と同じになる。変更したい場合は@Columnを使用する。
    private Long id;
    
    @NotBlank
    @Size(max = 50) // 最大50桁
    private String isbn;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotNull
    @Digits(integer = 6, fraction = 0) //最大6桁の整数
    private Integer price;

    @NotBlank
    @Size(max = 200)
    private String summary;

    //ここから下はエディタに自動で生成させる
    //右クリック→コードを挿入→取得メソッドおよび設定メソッド
    //右クリック→コードを挿入→toString()
    //右クリック→コードを挿入→equals()及びにhashCode()

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn=" + isbn + ", title=" + title + ", price=" + price + ", summary=" + summary + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
```

JPAでは@Entityというアノテーションが付いているクラスはO/Rマッパーのマッピングを定義していると解釈されます。

@Tableアノテーションで指定したテーブル名(省略した場合はクラス名)でテーブルが自動作成され、フィールドの名称でカラムが作成されます。

フィールドについているアノテーション(@NotNull等)はBean Validationのアノテーションです。JPAでは直接使用しませんが、画面からの入力値チェックに使用します。
ここではBean Validationについての説明は割愛します。

### BookRepositoryインターフェースの追加

BookクラスではJavaのオブジェクトとテーブルのマッピングを定義しました。実際にどうやって使うのかというとRepositoryと呼ばれるインターフェースを使用します。
以下のインターフェースを作成してください。

megascus.spring.boot.handson.model.BookRepository.java

```java:BookRepository.java

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
```

Repositoryはただのインターフェースです。実装はコンテナにより自動作成されます。
これだけだと良くわからないと思いますので、このインターフェースを使用するServiceクラスを作成します。

## BookServiceクラスの追加

Repositoryを使用するクラスとして以下のクラスを作成してください。

```java:BookService.java

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
```
@Autowired アノテーションをつけたフィールドを用意することでコンテナからRepositoryの実装クラスのオブジェクトを勝手に取得してきます。
これはDependency Injection(DI:依存性の注入)と呼ばれます。

＠Controllerアノテーションや、@Compornentアノテーションや、@Repositoryアノテーションをつけることでコンテナ管理のクラスとなり@Autowiredアノテーションでインジェクションできるようになります。

これを実際に画面からどうやって使っていくのかは次の章に続きます。

これで、JPAの追加が完了しました。
