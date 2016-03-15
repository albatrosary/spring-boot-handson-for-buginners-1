# デプロイ実行

今までで一通りデータベースを使用したウェブアプリケーションの開発を行ってきました。
この章ではIDEからの実行ではなく、実際に運用としてどのようにアプリケーションを実行するのかを見ていきます。

## pom.xml(mavenの設定ファイル)の修正

pom.xmlを以下のように変更してください。

```xml:pom.xml
・
・
・
・
・
    <!-- ここから追加 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    <!-- ここまで追加 -->
</project>
```
このプラグインを追加することで依存ライブラリを含めた一つのjarを作成することが出来るようになります。

## ビルドする

mavenをインストールしている環境では、コマンドプロンプトでプロジェクトのルートフォルダ(pom.xmlの置いてあるフォルダ)に移動して以下のコマンドを実行してください。

```mvn package```

targetフォルダにビルドされたjarファイルが作成されるはずです。

mavenをインストールしていない場合は、NetBeans上でプロジェクトを右クリックし、ビルドを選択することでビルドを行うことが出来ます。

## 実行する

通常、JavaのWebアプリケーションはwarファイルにし、Tomcat等のAPサーバー上で実行することが多いです。
しかしながら、Spring-bootは組み込みTomcatを内蔵しているため、直接実行することが出来ます。

コマンドプロンプトを開いてプロジェクトの下のtargetフォルダに移動し、以下のコマンドを入力してください。

```java -jar spring-boot-handson-1.0-SNAPSHOT.jar```

これだけで起動が出来ます。

これでデプロイ実行が完了しました。

## (参考)ポート番号の変更

今回のアプリケーションではポート番号はapplication.ymlにて定義しています。application.ymlの以下の行を修正してください。

```server.port: 8080```

もしくは実行時にコマンドライン引数で上書きすることも出来ます。以下のような形です。

```java -jar -Dserver.port=8081 spring-boot-handson-1.0-SNAPSHOT.jar```
