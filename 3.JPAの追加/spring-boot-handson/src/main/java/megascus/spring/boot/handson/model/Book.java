
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
import org.hibernate.validator.constraints.NotEmpty;

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