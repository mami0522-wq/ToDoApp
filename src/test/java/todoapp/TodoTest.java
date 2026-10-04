package todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import todoapp.model.Todo;

public class TodoTest{
        @Test
    void タイトルを正しく取得できる() {
        Todo todo = new Todo("買い物", LocalDate.of(2026, 8, 31));

        assertEquals("買い物", todo.getTitle());
    }

    @Test
    void 新規Todoは未達成になっている() {
        Todo todo = new Todo("買い物", LocalDate.of(2026, 8, 31));

        assertFalse(todo.isDone());
    }

    @Test
    void toggleDoneで達成状態を切り替えられる() {
        Todo todo = new Todo("買い物", LocalDate.of(2026, 8, 31));

        assertFalse(todo.isDone());

        todo.toggleDone();

        assertTrue(todo.isDone());

        todo.toggleDone();

        assertFalse(todo.isDone());
    }

    @Test
    void 未達成の場合は未達成と表示される() {
        Todo todo = new Todo("買い物", LocalDate.of(2026, 8, 31));

        assertEquals("未達成", todo.getStatusText());
    }

    @Test
    void 達成の場合は達成と表示される() {
        Todo todo = new Todo("買い物", LocalDate.of(2026, 8, 31));

        todo.setDone(true);

        assertEquals("達成", todo.getStatusText());
    }

    @Test
    void 締切日が未設定の場合は未設定と表示される() {
        Todo todo = new Todo("買い物", null);

        assertEquals("未設定", todo.getDeadlineText());
    }
  
    @Test
    void Todoのタイトルを再設定できる() {
    Todo todo = new Todo("買い物",LocalDate.of(2028,1,1));

    todo.setTitle("売り物");

    assertEquals("売り物",todo.getTitle());
        
    }
    @Test
    void Todoの達成を変更できる() {
        Todo todo = new Todo("買い物",LocalDate.of(2028,1,1));

        todo.setDone(true);

        assertTrue(todo.isDone());

        todo.setDone(false);

        assertFalse(todo.isDone());
    }

    @Test
    void 締切日を変更できる() {
        Todo todo = new Todo("買い物",LocalDate.of(2028,1,1));

        LocalDate deadline = LocalDate.of(2027,01,11);

        todo.setDeadline(deadline);

        assertEquals(deadline,todo.getDeadline());
    }

    @Test
    void 締切日が未設定なら残り日数はnull() {
        Todo todo = new Todo("買い物",LocalDate.of(2028,1,1));

        todo.setDeadline(null);

        assertNull(todo.getRemainingDays());
    }

    @Test
    void 締切日が今日なら残り日数は0() {
        Todo todo = new Todo("買い物",LocalDate.now());

        assertEquals(0L,todo.getRemainingDays());
    }

    @Test
    void 締切日が明日なら残り日数は1() {
        Todo todo = new Todo("買い物",LocalDate.now().plusDays(1));

        assertEquals(1L,todo.getRemainingDays());
    }

    @Test
    void 締切日が昨日なら残り日数はマイナスになる() {
        Todo todo = new Todo("買い物",LocalDate.now().minusDays(1));

        assertEquals(-1L,todo.getRemainingDays());
    }

    @Test
    void 締切日を過ぎている場合は期限超過と表示される() {
        Todo todo = new Todo("買い物",LocalDate.now().minusDays(1));

        assertEquals("期限を超えています。",todo.remainingDaysText());
    }

    @Test
    void 締切日当日は当日として表示される() {
        Todo todo = new Todo("買い物",LocalDate.now());

        assertEquals("締切日当日です。",todo.remainingDaysText());
    }

    @Test
    void 締切日が未来なら残り日数が表示される() {
        Todo todo = new Todo("買い物",LocalDate.now().plusDays(3));

        assertEquals("残り3日",todo.remainingDaysText());
    }

}