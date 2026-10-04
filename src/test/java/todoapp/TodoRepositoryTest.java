package todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import todoapp.repository.TodoRepository;
import todoapp.model.Todo;


public class TodoRepositoryTest {

    private TodoRepository repository;
    private Path testFile;

    @BeforeEach 
    void setUp() throws IOException{
        testFile = Files.createTempFile("todo-test-", ".txt");

        repository = new TodoRepository(testFile);
    }
    
    @Test 
    void Todoを保存して読み込める() {

        Todo todo = new Todo(
            "買い物",
            true,
            LocalDate.of(2026,9,1),
            LocalDateTime.of(2026,9,1,10,30),
            LocalDate.of(2026,9,30)
        );

        List<Todo> todos = new ArrayList<>();
        todos.add(todo);

        repository.save(todos);

        List<Todo> loadedTodos = repository.load();

        assertEquals(1,loadedTodos.size());
        assertEquals("買い物", loadedTodos.get(0).getTitle()
        );
    }

    @Test 
    void 複数のTodoを保存読み込みができる() {

        Todo todo1 = new Todo(
            "買い物", 
            false,
            LocalDate.of(2026,9,20),
            LocalDateTime.of(2026,9,20,10,0),
            LocalDate.of(2026,9,30)
        );

        Todo todo2 = new Todo(
            "掃除",
            true,
            LocalDate.of(2026,9,21),
            LocalDateTime.of(2026,9,21,11,0),
            LocalDate.of(2026,10,5)
        );

        Todo todo3 = new Todo(
            "勉強",
            false,
            LocalDate.of(2026,9,22),
            LocalDateTime.of(2026,9,22,12,0),
            null
        );

        List<Todo> todos = List.of(todo1, todo2, todo3);

        repository.save(todos);

        List<Todo> loaded =  repository.load();

        assertEquals(3,loaded.size());

        assertEquals("買い物", loaded.get(0).getTitle());
        assertEquals("掃除", loaded.get(1).getTitle());
        assertEquals("勉強",loaded.get(2).getTitle());

        //doneの確認
        assertFalse(loaded.get(0).isDone());
        assertTrue(loaded.get(1).isDone());
        assertFalse(loaded.get(2).isDone());

        //deadlineの確認
        assertEquals(
            LocalDate.of(2026,9,30), 
            loaded.get(0).getDeadline() 
        );

        assertEquals(
            LocalDate.of(2026,10,5),
             loaded.get(1).getDeadline()
            );

        assertNull(loaded.get(2).getDeadline());
    }

    @Test 
    void データの要素が足りない場合() throws IOException {

        String invalidData = "買い物,false,2026-09-23";

        Files.writeString(testFile, invalidData);

        List<Todo> loaded = repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void タイトルが空の場合() throws IOException {
        String invalidData = ",false,2026-09-23,2026-09-23-10-10-0,2026-10-10";

        Files.writeString(testFile,invalidData);

        List<Todo> loaded = repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void doneが不正な場合() throws IOException {
        String invalidData = "買い物,,2026-09-23,2026-09-23-10-10-0,2026-10-10";

        Files.writeString(testFile,invalidData);

        List<Todo> loaded = repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void 作成日が不正な場合() throws IOException {
        String invalidData = "買い物,false,2026-09,2026-09-23-10-10-0,2026-10-10";

        Files.writeString(testFile,invalidData);

        List<Todo> loaded = repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void 作成日時が不正な場合() throws IOException {
        String invalidData = "買い物,2026-09-23,2026-09-23,2026-10-10";

        Files.writeString(testFile,invalidData);

        List<Todo> loaded =repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void 締切日が不正な場合() throws IOException {
        String invalidData = "買い物,2026-09-23,2026-09-23-10-10-10,2026";

        Files.writeString(testFile,invalidData);

        List<Todo> loaded = repository.load();

        assertEquals(0,loaded.size());
    }

    @Test 
    void 不正なデータだけ読み飛ばした後正常なデータを読み込める() throws IOException {
        String invalidData = "買い物,false,2026-09-23,2026-09,,";
        String nomalData = "掃除,true,2026-09-24,2026-09-24T10:00,2026-10-01";

        Files.writeString(testFile,
            invalidData + System.lineSeparator() + nomalData
        );

        List<Todo> loaded = repository.load();

        assertEquals(1,loaded.size());
        assertEquals("掃除",loaded.get(0).getTitle());
    }
}
