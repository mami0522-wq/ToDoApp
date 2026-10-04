package todoapp.service;

import todoapp.model.Todo;
import todoapp.repository.TodoRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import todoapp.util.*;

public class TodoManager {
    private TodoRepository repository;
    
    private List<Todo> todos = new ArrayList<>();

    /**
     * TodoManagerを生成します。
     * 
     * 
     * <p>
     * 保存ファイルからTodo一覧を読み込み、
     * 管理対象として初期化します。
     * </p>
     */
    public TodoManager() {
        System.out.println("TodoManager開始");
        repository = new TodoRepository();
        todos = repository.load();
    }



    /**
     * 新しいTodoを追加します。
     *      
     * <p>
     * タイトル、締切日を入力し,
     * Todoを生成して一覧へ追加します。
     * 追加後は保存ファイルへ保存します。
     * 入力途中でキャンセルされた場合は、
     * 何も変更せずに処理を終了します。
     * </p>
     */
    public void addList() {
        String title = InputUtil.inputStrCancel("タイトル");
        if(title == null)
            return;
        LocalDate deadline = InputUtil.inputLocalDate();
        if(deadline == null)
            return;
        todos.add(new Todo(title, deadline));
        repository.save(todos);
    }

    /**
     * Todoを一覧に追加します。
     * 
     * @param todo 追加するTodo
     */
    public void addList(Todo todo) {
        todos.add(todo);
    }

    /**
     * 
     * @param index Todoのインデックス
     * @return 指定した位置のインデックス
     */
    public Todo getTodo(int index) {
        return todos.get(index);
    }

    /**
     * Todo一覧からTodoを1つ選んで削除します。
     * 
     * <p>
     * Todo一覧を表示して削除するTodoを選び、削除します。。
     * 削除後は保存ファイルに保存し
     * 続けて同じ処理を行うか選びます。
     * 入力途中でキャンセルされた場合は
     * 何もせずに処理を終了します。
     * </p>
     */
    public void removeList() {
        if(!hasTodos()){
            return;
        }
        while(true){

        this.showAllTitle();
        int num = (InputUtil.inputIntCancel("削除するタスクを", 1, this.getSize())) ;
        if(num == 0)
            break;
        removeTodo(num - 1);
        repository.save(todos);
        
        System.out.println("続けて削除しますか。");
        }
        return;
    }

    /**
     * Todo一覧から指定したTodoを削除します。
     * 
     * @param index 削除するTodoのインデックス
     */
    public void removeTodo(int index) {
        todos.remove(index);
    }

    /**
     * 全てのTodoを削除します。
     * 
     * <p>
     * 全ての削除するかを確認した後、削除します。
     * 「いいえ」を選ぶと何も変更せずに処理を終了します。
     * </p>
     */
    public void clearTodos(){
        System.out.println("全て削除しても大丈夫ですか?");
        System.out.println("1 : はい");
        System.out.println("2 : いいえ");
        int select = InputUtil.inputIntCancel("選択肢",1,2);
        if(select == 0)
            return;
        
        if(select == 2) 
            return;
        switch(select){
            case 1 -> clearAllTodos();
        }

    }

    /**
     * 全てのタスクを消去します。
     */
    public void clearAllTodos() {
        todos.clear();
    }

    /**
     * Todoの削除の形式を選びます。
     * 
     * <p>
     * Todoの削除方法を選び、Todoを削除します。
     * 削除後は保存ファイルに保存します。
     * 途中でキャンセルした場合は
     * 何も変更をせずに処理を終了します。
     * </p>
     * 
     */
    public void deleteMenu() {
        while(true) {
            System.out.println("1 : タスクを1つ削除する");
            System.out.println("2 : タスクを全て削除する");
            int select = InputUtil.inputIntCancel("選択肢", 1, 2);
            if(select ==0) 
                return;
            switch(select) {
                case 1 -> removeList(); 

                case 2 -> clearTodos();
            }

            repository.save(todos);
            return;

        }
    }

    /**
     * リストにあるTodoを全て表示します。
     * 
     * <p>
     * リストにあるTodoのタスク名、進行状況、作成日、作成日時、締切日を
     * 連番付きで表示します。
     * </p>
     * 
     */
    public void showTodos() {
        if(!hasTodos()){
            return;
        }
        int num = 1;
        for(Todo list: todos) {
            
            System.out.println("No." + num);
            System.out.println(list);
            System.out.println("");
            num ++;
            
        }
    }

    /**
     * リストにあるTodoのタスク名のみを全て表示します。
     * 
     * <p>
     * タスク名を連番付きで一覧表示します。
     * </p>
     */
    public void showAllTitle(){
        if(!hasTodos()){
            return;
        }
        int no = 1;
        for(Todo list: todos){
            System.out.println(no + ":" + list );
            no++;
        }
    }

    /**
     * Todo一覧の件数を数えます。
     * 
     * @return Todo一覧の件数
     */
    public int getSize() {
        return todos.size();
    }

    /**
     * Todoのタスク名を変更します。
     * 
     * <p>
     * リストにある全てのTodoのタスク名を表示して、
     * 変更するTodoを選び、変更後のタスク名を入力します。
     * リストが空だと何も変更せずに処理を終わります。
     * 選択時に0を入力すると何も変更せずに処理を終わります。
     * </p>
     */
    public void editTitle(){
        if(!hasTodos()){
            return;
        }
        this.showAllTitle();
        int index = (InputUtil.inputIntCancel("変更するタスクの番号", 1, todos.size()));
        if(index == 0)
            return;
        index --;

        String title = InputUtil.inputStrCancel("変更後のタイトル");
        
        changeTitle(index,title);


    }

    /**
     * Todoのタスク名を変更します。
     * 
     * @param name 変更後のタスク名
     * @param number 変更するタスクの要素番号
     */
    public void changeTitle(int index,String title) {
        todos.get(index).setTitle(title);
    }

    /**
     * 進行状況を変更します。
     * 
     * <p>
     * リストにある全てのTodoのタスク名を表示して、
     * 変更するTodoを選びます。
     * リストが空だと何も変更せずに処理を終わります。
     * 選択時に0を入力すると何も変更せずに処理を終わります。
     * </p>
     */
    public void toggleDone(){
        if(!hasTodos()){
            return;
        }
        this.showTodos();
        int num = (InputUtil.inputIntCancel("進行状況を変えるタスクの番号", 1, todos.size()));
        if(num == 0)
             return;
        num --;
        todos.get(num).toggleDone();
    }

    /**
     * リストが要素を持っているかを判定します。
     * 
     * @return リストが1つでも要素をもっていればtrue,持っていない場合はfalseを返す。
     */
    public boolean  hasTodos(){
        if(todos.isEmpty()){
            System.out.println("タスクがありません。");
            return false;
        }
        return true;
    }

    /**
     * 締切日を再設定します。
     * 
     * <p>
     * Todo一覧を表示して締切日を変更するタスクを選びます。
     * 途中でキャンセルするとなにも変更せずに処理を終了します。
     * </p>
     */
    public void resetDeadline() {
        if(!hasTodos())
            return;
        showAllTitle();
        int index = InputUtil.inputIntCancel("締切日を変えるタスク",1, todos.size());
        if(index == 0)
            return ;
        index--;

        LocalDate date = InputUtil.inputLocalDate();
        
        changeDeadline(index, date);

    }

    /**
     * 指定してTodoの締切日を変更します。
     * 
     * @param index 締切日を変更するTodoのインデックス
     * @param date　新しい締切日
     */
    public void changeDeadline(int index,LocalDate date) {
        todos.get(index).setDeadline(date);
    }

    /**
     * Todoの変更内容を選択します。
     * 
     * <p>
     * タイトル、進行状況、締切日の中から内容を変更するものを選びます。
     * 処理が終了しても"編集をやめる"を選ぶまで続けて編集できます。
     * 途中入力をキャンセルすると変更せずに処理を終了します。
     * </p>
     */
    public void editMenu() {
        while(true) {
    
            System.out.println("1 : タイトル変更");
            System.out.println("2 : 進行状況の変更");
            System.out.println("3 : 締切日の変更");
            int select = InputUtil.inputIntCancel("選択肢",1,3);
            if(select == 0) 
                return;
            switch(select) {
                case 1 -> editTitle();

                case 2 -> toggleDone();

                case 3 -> resetDeadline();
            }

            System.out.println("続けて編集しますか？");
           
            System.out.println("1 : 続けて編集する");
            System.out.println("2 : 編集をやめる");

            select = InputUtil.inputIntCancel("選択肢", 1,2);
            if(select == 0)
                return;

            if(select == 1)
                continue;
            if(select == 2)
                break;

        }     
    }

    /**
     * Todoの並び替えをします。
     * 
     * <p>
     * ソートの基準をタイトル、作成日、締切日の中から選びます。
     * </p>
     * 
     */
    public void sortMenu() {
        while(true) {
            System.out.println("1 : タイトル順");
            System.out.println("2 : 作成日順");
            System.out.println("3 : 締切日順");
            System.out.println("0 : 戻る");

            int select = InputUtil.inputIntCancel("ソートの基準", 1, 3);
            if(select == 0)
                return;

            switch(select) {
                case 1 : sortByTitle();
                        break;
                
                case 2 : sortByCreateDate();
                        break;

                case 3 : sortByDeadline();
                        break;
            }
            return;
        }
    }

    /**
     * タイトル順で並べ替えをします。
     * 
     * <p>
     * リストが要素を持っているかを確認して,
     * 昇順か降順かを決めてタイトル順に並べ替えます。
     * タイトルが同じ場合は作成日順で並び替えます。
     * 処理後に保存ファイルに保存します。
     * キャンセルすると何も変更せずに処理を終了します。
     * </p>
     */
    public void sortByTitle() {
        if(!hasTodos()){
            return;
        }


        boolean asc = InputUtil.selectTwo( "昇順", "降順");
        
        sortByTitle(asc);

        repository.save(todos);
    }

    public void sortByTitle(boolean asc) {
        if(!hasTodos())
            return;

        Comparator<Todo> comparator = Comparator.comparing(Todo::getTitle).thenComparing(Todo::getCreateDate);

        if(!asc) {
            comparator = comparator.reversed();
        }

        todos.sort(comparator);
    }

    /**
     * 作成日順で並べ替えをします。
     * 
     * <p>
     * リストが要素を持っているかを確認して,
     * 昇順か降順かを決めて作成日順で並び替えます。
     * 作成日が同じ場合はタイトル順で並び替えます。
     * 処理後に保存ファイルに保存します。
     * キャンセルすると何も変更せずに処理を終了します。
     * </p>
     */

    public void sortByCreateDate() {
        if(!hasTodos())
            return;


        boolean asc = InputUtil.selectTwo("昇順", "降順");
        
        sortByCreateDate(asc);


        repository.save(todos);
    }

    /**
     * 作成日順に並べ替えをします。
     * 
     * @param asc 昇順、降順を切り替えます。
     */
    public void sortByCreateDate(boolean asc) {
        Comparator<Todo> comparator = Comparator.comparing(Todo::getCreateDateTime).thenComparing(Todo::getTitle);

        if(!asc){
            comparator = comparator.reversed();
        }

        todos.sort(comparator);
    }

    /**
     * 締切日順で並べ替えをします。
     * 
     * <p>
     * リストが要素を持っているかを確認して,
     * 昇順か降順かを決めて締切日順に並び替えます。
     * 締切日が同じ場合はタイトル順で並び替えます。
     * 処理後に保存ファイルに保存します。
     * キャンセルすると何も変更せずに処理を終了します。
     * </p>
     */
    public void sortByDeadline() {
        if(!hasTodos())
            return;

        boolean asc = InputUtil.selectTwo("昇順", "降順");

        sortByDeadline(asc);

        repository.save(todos);
    }

    public void sortByDeadline(boolean asc) {
        Comparator<Todo> comparator = Comparator.comparing(Todo::getDeadline,Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Todo::getTitle);

        if(!asc) {
            comparator = comparator.reversed();
        }

        todos.sort(comparator);
    }
}
