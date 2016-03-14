# spring-boot-handson-for-buginners

今回はハンズオン形式でSpring Bootを使用したWebアプリケーションを作成してみます。

## Spring Bootとは

次のような特徴をもったプロダクトです。

- Spring Frameworkを用いたアプリケーションを簡単に構築する仕組み
- MavenまたはGradleに依存関係を追加するだけで設定などを自動でしてくれる
- java -jarコマンドで実行可能なオールインワンなJARを作成できる

Spring Bootを使えば手軽にサクサクとアプリケーション開発をすることができます。

今回はWebアプリケーションに特化した形で入門編として行います。

#対象者
対象者としては以下の通りです。

- Javaの基本的な構文がわかる
- Webアプリケーションが何かわかる
- Javaを使っていまどきなウェブアプリケーションを作れるようになりたい

#事前準備
事前準備としてJDK 8のインストールをお願いします。
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
※自分の環境を選択してダウンロードしてください。

また、今回は開発環境としてNetBeans 8.1を使用します。
インストールが済んでない方はインストールをお願いします。
以下のページよりJava EEを選択してダウンロードしてください。
https://netbeans.org/downloads/?pagelang=ja
(Tomcat/GlassFishのインストールダイアログが途中でますが、そちらはインストールしなくてもかまいません)

#アジェンダ
以下の章に分かれています。

+ mavenから初期起動
+ Thymeleafの追加
+ JPAの追加
+ htmlでの表示
+ RESTでのAPIアクセス
+ JDBCでのDBアクセス
+ デプロイ実行

それぞれの章はフォルダで分けられ、章ごとに終了時点のアプリケーションサンプルが入っていますので必要に応じてそちらを参照してください。
