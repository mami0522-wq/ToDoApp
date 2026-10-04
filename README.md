# Todoアプリ

Javaで開発したTodo管理アプリです。

Todoの追加・表示・編集・削除・完了状態の変更・締切日の設定など、基本的なTodo管理機能を実装しています。

Javaの学習を目的として開発を行い、Mavenによるプロジェクト管理とJUnit 5によるテストも実施しています。

---

## 概要

コンソール上で操作するTodo管理アプリです。

Todoには以下の情報を保持しています。

- タイトル
- 完了状態
- 作成日
- 作成日時
- 締切日

Todoのデータはファイルに保存され、アプリを終了して再度起動した場合でも保存したTodoを読み込むことができます。

---

## 主な機能

### Todo管理

- Todoの追加
- Todoの一覧表示
- Todoのタイトル編集
- Todoの削除
- Todoの全削除
- Todoの完了・未完了切り替え

### 締切日管理

- 締切日の設定
- 締切日の変更
- 締切日のリセット
- 締切までの残り日数の表示
- 期限切れの表示

### 並び替え

以下の条件でTodoを並び替えることができます。

- タイトル順
- 作成日時順
- 締切日順

昇順・降順に対応しています。

### データ保存

Todoのデータをファイルに保存します。

アプリ起動時に保存されたTodoを読み込み、アプリ終了後もデータを保持できます。

また、保存データに不正なデータが含まれていた場合は、そのデータを処理から除外し、バックアップを作成する仕組みを実装しています。

---

## 使用技術

- Java 25
- Maven 3.9.16
- JUnit 5
- VS Code
- Git / GitHub

### Java

Javaのオブジェクト指向の学習を兼ねて開発しています。

主に以下の機能・仕組みを使用しています。

- クラス
- オブジェクト
- カプセル化
- コンストラクタ
- コレクション
- 例外処理
- `LocalDate`
- `LocalDateTime`
- `Comparator`
- ファイル入出力

---

## テスト

JUnit 5を使用して各クラスのテストを実装しています。

### テスト結果

`mvn clean test`を実行し、以下の結果を確認しています。

- テスト数：74
- Failure：0
- Error：0
- Skipped：0
- 結果：BUILD SUCCESS

### テスト対象

- `TodoTest`
  - Todoのタイトル・完了状態・締切日などの動作をテスト
- `TodoManagerTest`
  - Todoの追加・編集・削除・完了状態変更・締切日変更・並び替えなどをテスト
- `TodoRepositoryTest`
  - Todoデータの保存・読み込み・不正データへの対応などをテスト
- `InputUtilTest`
  - 文字列・整数・日付などのユーザー入力処理をテスト

### テスト実行

以下のコマンドでテストを実行できます。

```bash
mvn clean test
```
```text
## プロジェクト構成

```text
ToDoApp
├─ .gitignore
├─ pom.xml
├─ README.md
└─ src
   ├─ main
   │  └─ java
   │     └─ todoapp
   │        ├─ Main.java
   │        ├─ model
   │        │  └─ Todo.java
   │        ├─ repository
   │        │  └─ TodoRepository.java
   │        ├─ service
   │        │  └─ TodoManager.java
   │        └─ util
   │           └─ InputUtil.java
   │
   └─ test
      └─ java
         └─ todoapp
            ├─ TodoTest.java
            ├─ TodoManagerTest.java
            ├─ TodoRepositoryTest.java
            └─ InputUtilTest.java