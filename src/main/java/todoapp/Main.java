package todoapp;

import todoapp.service.TodoManager;
import todoapp.util.InputUtil;

public class Main{

    private static final int ADD = 1;
    private static final int SHOW = 2;
    private static final int REMOVE = 3;
    private static final int EDIT = 4;
    private static final int DONE = 5;
    private static final int EXIT = 6;

    private final static TodoManager manager = new TodoManager();
    /**
     * ToDoアプリケーションを開始します。
     * 
     * <p>
     * メニューを表示し、ユーザーが終了を選択するまで
     * 各機能を実行します。
     * </p>
     * 
     * @param args コマンドライン引数(使用しません)
     */    public static void main(String[] args){

        while(true){

            showMenu();

            int select = InputUtil.inputInt("選択肢", 1, 6);

            if(select == EXIT)
                break;

            executeMenu(select);
        }
    }

    /**
     * メインメニューを表示します。
     */
    private static void showMenu() {
        
        System.out.println("1: タスク追加");
        System.out.println("2: タスク一覧");
        System.out.println("3: タスク削除");
        System.out.println("4: タスク編集");
        System.out.println("5: タスクの並び替え");
        System.out.println("6: 終了");
    }

    /**
     * 選択されたメニューに対応する処理を実行します。
     * 
     * @param select 選択されたメニュー番号
     */
    private static void executeMenu(int select) {
        
        switch(select) {
            
            case ADD -> manager.addList();

            case SHOW -> manager.showTodos();

            case REMOVE -> manager.deleteMenu();

            case EDIT -> manager.editMenu();

            case DONE -> manager.sortMenu();

        }
    }
}