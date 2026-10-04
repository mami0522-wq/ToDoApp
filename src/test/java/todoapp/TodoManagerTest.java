package todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import todoapp.service.TodoManager;
import todoapp.model.Todo;

public class TodoManagerTest{
    @Test 
    void Todoを追加できる() {
        TodoManager manager = new TodoManager();

        int before = manager.getSize();

        Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));

        manager.addList(todo);

        assertEquals(before + 1, manager.getSize());
    }

    @Test 
    void Todoを削除できる() {
        TodoManager manager = new TodoManager();

        Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));
        manager.addList(todo);

        int before = manager.getSize();

        manager.removeTodo(before -1);

        assertEquals(before - 1,manager.getSize());
    }

    @Test 
    void Todoを全て削除できる() {
        TodoManager manager = new TodoManager();

        Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));
        manager.addList(todo);

        manager.clearAllTodos();

        assertEquals(0,manager.getSize());
    }

    @Test 
    void Todoのタスク名を変更できる() {
        TodoManager manager = new TodoManager();

        Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));
        manager.addList(todo);

        manager.changeTitle(0,"売り物");

        assertEquals("売り物",manager.getTodo(0).getTitle());
    }

    @Test 
    void Todoが存在する場合trueを返す() {
        TodoManager manager =new TodoManager();

        Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));
        manager.addList(todo);

        assertTrue(manager.hasTodos());
    }

    @Test 
    void Todoが存在しない場合falseを返す() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        assertFalse(manager.hasTodos());

    }

    

@Test 
void 締切日の再設定ができる() {
    TodoManager manager = new TodoManager();

    Todo todo = new Todo("買い物",LocalDate.of(2026,9,30));
    manager.addList(todo);

    int index = manager.getSize() - 1;
    LocalDate date = LocalDate.of(2027,10,1);

    manager.changeDeadline(index, date);

    assertEquals(LocalDate.of(2027,10,1),manager.getTodo(index).getDeadline());
    }

    @Test 
    void タイトル順を昇順に並び替えられる() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        manager.addList(new Todo("C",LocalDate.of(2026,9,30)));
        manager.addList(new Todo("B",LocalDate.of(2027,10,11)));
        manager.addList(new Todo("A",LocalDate.of(2028,12,12)));

        manager.sortByTitle(true);


        assertEquals("A",manager.getTodo(0).getTitle());
        assertEquals("B",manager.getTodo(1).getTitle());
        assertEquals("C",manager.getTodo(2).getTitle());
    }

    @Test 
    void タイトルを降順に並び替えられる() {
        TodoManager manager = new TodoManager();
         manager.clearAllTodos();

        manager.addList(new Todo("C",LocalDate.of(2026,9,30)));
        manager.addList(new Todo("B",LocalDate.of(2027,10,11)));
        manager.addList(new Todo("A",LocalDate.of(2028,12,12)));

        manager.sortByTitle(false);

        assertEquals("C",manager.getTodo(0).getTitle());
        assertEquals("B",manager.getTodo(1).getTitle());
        assertEquals("A",manager.getTodo(2).getTitle());

    }

    @Test 
    void タイトル昇順で並べ替えるとき同じタイトルがあった場合作成日昇順順に並び替えられる() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,10,0),
            LocalDate.of(2026,9,20)
        );

        Todo todo2 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,10,10,0),
            LocalDate.of(2026,9,20)
        );

        manager.addList(todo1);
        manager.addList(todo2);

        manager.sortByTitle(true);

        assertEquals(LocalDate.of(2026,9,1),manager.getTodo(0).getCreateDate());
        assertEquals(LocalDate.of(2026,9,10),manager.getTodo(1).getCreateDate());
    }

    @Test 
    void タイトル降順で並べ替える時同じタイトルがあった場合作成日降順で並び替える() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            LocalDate.of(2026,10,10)
        );

        Todo todo2 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,0,0,0),
            LocalDate.of(2026,10,10)
        );

        manager.addList(todo1);
        manager.addList(todo2);

        manager.sortByTitle(false);

        assertEquals(LocalDate.of(2026,9,10),manager.getTodo(0).getCreateDate());
        assertEquals(LocalDate.of(2026,9,1), manager.getTodo(1).getCreateDate());
    }

    @Test 
    void 作成日を昇順に並び替えられる() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,9,1,0,0,0),
            LocalDate.of(2026,9,30)    
        );
        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,8,1),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,10,1)
        );

        manager.addList(todo1);
        manager.addList(todo2);

        manager.sortByCreateDate(true);

        assertEquals(LocalDate.of(2026,8,1),manager.getTodo(0).getCreateDate());
        assertEquals(LocalDate.of(2026,8,30),manager.getTodo(1).getCreateDate());
    }

    @Test 
    void 作成日を降順に並び替えられる() {
        TodoManager manager  = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,8,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            LocalDate.of(2026,9,30)    
        );
        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,10,1)
        );

        manager.addList(todo1);
        manager.addList(todo2);

        manager.sortByCreateDate(false);

        assertEquals(LocalDate.of(2026,8,30),manager.getTodo(0).getCreateDate());
        assertEquals(LocalDate.of(2026,8,1),manager.getTodo(1).getCreateDate());
    }

    @Test 
    void 作成日昇順に並び替えた時に同じ作成日があった場合タイトル昇順に並び替える() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,9,30)
        );

        Todo todo2 = new Todo(
            "売り物",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,9,30)
        );

        manager.addList(todo1);
        manager.addList(todo2);

        manager.sortByCreateDate(true);

        assertEquals("売り物",manager.getTodo(0).getTitle());
        assertEquals("買い物",manager.getTodo(1).getTitle());
    }

    @Test 
    void 作成日降順に並び替えた時に同じ作成日があった場合タイトル降順に並び替える() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "売り物",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,9,30)
        );

        Todo todo2 = new Todo(
            "買い物",
            false,
            LocalDate.of(2026,8,30),
            LocalDateTime.of(2026,8,30,0,0,0),
            LocalDate.of(2026,9,30)
        );

        manager.addList(todo1);
        manager.addList(todo2);
        
        manager.sortByCreateDate(false);

        assertEquals("買い物",manager.getTodo(0).getTitle());
        assertEquals("売り物",manager.getTodo(1).getTitle());
    }

    @Test 
    void 締切日を昇順に並び替えらる() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            null
        );

        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,0,0,0),
            LocalDate.of(2026,10,30)
        );

        Todo todo3 = new Todo(
            "C",
            false,
            LocalDate.of(2026,10,20),
            LocalDateTime.of(2026,10,20,0,0,0),
            LocalDate.of(2026,9,30)
        );

        manager.addList(todo1);
        manager.addList(todo2);
        manager.addList(todo3);

        manager.sortByDeadline(true);

        assertEquals(LocalDate.of(2026,9,30),manager.getTodo(0).getDeadline());
        assertEquals(LocalDate.of(2026,10,30),manager.getTodo(1).getDeadline());
        assertNull(manager.getTodo(2).getDeadline());
    }

    @Test 
    void 締切日を降順に並び替えられる() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            null
        );

        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,0,0,0),
            LocalDate.of(2026,10,30)
        );

        Todo todo3 = new Todo(
            "C",
            false,
            LocalDate.of(2026,10,20),
            LocalDateTime.of(2026,10,20,0,0,0),
            LocalDate.of(2026,9,30)
        );

        

        manager.addList(todo1);
        manager.addList(todo2);
        manager.addList(todo3);

        manager.sortByDeadline(false);

        assertNull(manager.getTodo(0).getDeadline());
        assertEquals(LocalDate.of(2026,10,30),manager.getTodo(1).getDeadline());
        assertEquals(LocalDate.of(2026,9,30),manager.getTodo(2).getDeadline());  
        
    }

    @Test 
    void 締切日昇順に並びかえる時同じ締切日があった場合タイトル昇順に並び替える() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

        Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            null
        );

        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,0,0,0),
            LocalDate.of(2026,9,30)
        );

        Todo todo3 = new Todo(
            "C",
            false,
            LocalDate.of(2026,10,20),
            LocalDateTime.of(2026,10,20,0,0,0),
            LocalDate.of(2026,9,30)
        );

        manager.addList(todo1);
        manager.addList(todo2);
        manager.addList(todo3);
        
        manager.sortByDeadline(true);

        assertEquals("B",manager.getTodo(0).getTitle());
        assertEquals("C",manager.getTodo(1).getTitle());
        assertEquals("A",manager.getTodo(2).getTitle());
    }

    @Test 
    void 締切日降順に並び替えた時同じ締切日があった場合タイトル降順に並び替える() {
        TodoManager manager = new TodoManager();

        manager.clearAllTodos();

         Todo todo1 = new Todo(
            "A",
            false,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,0,0,0),
            null
        );

        Todo todo2 = new Todo(
            "B",
            false,
            LocalDate.of(2026,9,10),
            LocalDateTime.of(2026,9,10,0,0,0),
            LocalDate.of(2026,9,30)
        );

        Todo todo3 = new Todo(
            "C",
            false,
            LocalDate.of(2026,10,20),
            LocalDateTime.of(2026,10,20,0,0,0),
            LocalDate.of(2026,9,30)
        );

        manager.addList(todo1);
        manager.addList(todo2);
        manager.addList(todo3);

        manager.sortByDeadline(false);

        assertEquals("A",manager.getTodo(0).getTitle());
        assertEquals("C",manager.getTodo(1).getTitle());
        assertEquals("B",manager.getTodo(2).getTitle());
    }
}

