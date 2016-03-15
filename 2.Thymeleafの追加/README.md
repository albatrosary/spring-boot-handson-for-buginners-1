# Thymeleafの追加

## Thymeleafとは

Thymeleafはテンプレートエンジンです。

Javaからhtmlを生成する場合、昔はServletが使われていましたが、htmlを出力するためだけにJavaのクラスを作るのは面倒ということで、htmlに近い形でhtmlを作成する部品が作れるようにということで、テンプレートエンジンが生まれました。
Thymeleafはそのテンプレートエンジンのうちの一つです。

## 実際に使用してみる

### RootControllerを修正する

先ほど作成したRootControllerを以下のように修正します。


megascus.spring.boot.handson.RootController.java

```java:RootController.java

package megascus.spring.boot.handson;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author megascus
 */
//@RestController
@Controller
public class RootController {
    
    @RequestMapping("/")
    public String get() {
        // return "hello world";
        return "index"; //resources/templates/index.htmlファイルの中身を参照しに行く
    }
}

```

@Controllerアノテーションは画面アクセスのルーティングを定義するために使用するクラスにつけるアノテーションです。
用語についてはいわゆるMVCに則ってつけられています。

### HTMLテンプレートファイルの追加

プロジェクトからその他のソース→src/main/resourcesを選択し、その下にtemplatesフォルダを作成します。※NetBeans上はJavaのパッケージのように見えます。

その下に以下のファイルを作成します。

```html:index.html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Getting Started: Serving Web Content</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <p th:text="'Hello, world!'">こんにちは</p>
    </body>
</html>
```

このファイルを直接ブラウザで開いてみてください。"こんにちは"と表示されることが確認できます。

### 起動する

プロジェクトを右クリックしてカスタム→spring-boot:runを選択してください。

起動させたら以下のURLにアクセスしてください。

http://localhost:8080/

右クリックからソースを表示するを選択すると以下のように表示されているのが確認できると思います。


```html:index.html
<!DOCTYPE HTML>

<html>
    <head>
        <title>Getting Started: Serving Web Content</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <p>Hello, world!</p>
    </body>
</html>
```

th:text属性で指定した文字列でpタグの内容が置き換わっています。
Thymeleafの特徴として、そのまま表示できるhtmlに対して、追加で属性を記載するだけでプログラマブルに表示を変更できることがあります。
これはプログラマーとデザイナーが共同で作業をする場合にプログラマーが追加した属性をそのままにしながらデザイナーがデザインを修正できることを意味しています。
そういった利点があるため、Thymeleafは幅広く使用されています。

これで、Thymeleafの追加が完了しました。
