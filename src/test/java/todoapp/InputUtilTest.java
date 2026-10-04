package todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import todoapp.util.InputUtil;

public class InputUtilTest {
    
    @Test 
    void inputStrCancel_通常の文字列を入力できる() throws IOException {

        InputUtil.br = new BufferedReader(new StringReader("買い物\n"));

        String result = InputUtil.inputStrCancel("タイトル");

        assertEquals("買い物",result);
    }

    @Test 
    void inputStrCancel_空白入力の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("  \n買い物\n"));

        String result= InputUtil.inputStrCancel("タイトル");

        assertEquals("買い物",result);
    }

    @Test 
    void inputStrCancel_cancel入力でnullが返す() {

        InputUtil.br = new BufferedReader(new StringReader("cancel"));

        String result = InputUtil.inputStrCancel("タイトル");

        assertNull(result);
    }

    @Test 
    void inputStrCancel_空入力の場合再入力をできる() {

        InputUtil.br = new BufferedReader(new StringReader("\n買い物\n"));

        String result = InputUtil.inputStrCancel("タイトル");

        assertEquals("買い物",result);
    }

    @Test
    void inputIntCancel_範囲指定あり_数字を正しく入力できる() {

        InputUtil.br = new BufferedReader(new StringReader("5"));

        int result = InputUtil.inputIntCancel("締切日",0,10);

        assertEquals(5, result);
    }

    @Test 
    void inputIntCancel_範囲指定あり_空白入力の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("  \n5\n"));

        int result = InputUtil.inputIntCancel("締切日",0,10);

        assertEquals(5,result);
    }

    @Test 
    void inputIntCancel_空入力の場合再入力をできる() {

        InputUtil.br = new BufferedReader(new StringReader("\n5\n"));

        int result = InputUtil.inputIntCancel("締切日",0,10);

        assertEquals(5,result);
    }

    @Test 
    void inputIntCancel_範囲指定あり_0入力すると0を返す() {

        InputUtil.br = new BufferedReader(new StringReader("0"));

        int result = InputUtil.inputIntCancel("締切日",1,10);

        assertEquals(0,result);
    }

    @Test 
    void inputIntCancel_範囲指定あり_最小値より小さい場合再入力できる() {

        InputUtil.br = new BufferedReader(new  StringReader("-1\n5"));

        int result = InputUtil.inputIntCancel("締切日",0,20);

        assertEquals(5,result);
    }

    @Test 
    void inputIntCancel_範囲指定あり_最大値より大きい場合再入力ができる() {

        InputUtil.br = new BufferedReader(new StringReader("20\n5\n"));

        int result = InputUtil.inputIntCancel("タスク",0,10);

        assertEquals(5,result);

    }

    @Test 
    void inputIntCancel_範囲指定あり_文字列を入力すると再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("買い物\n5\n"));

        int result = InputUtil.inputIntCancel("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_最小値のみ指定_正常な整数を入力できる() {

        InputUtil.br = new BufferedReader(new StringReader("5"));


        int result = InputUtil.inputInt("締切日",1);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_最小値のみ指定_0入力すると0を返す() {

        InputUtil.br =  new BufferedReader(new StringReader("0\n"));

        int result = InputUtil.inputInt("締切日",1);

        assertEquals(0,result);
    }

    @Test 
    void  inputInt_最小値のみ指定_空入力の場合再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("\n5\n"));

        int result = InputUtil.inputInt("締切日",1);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_最小値のみ指定_空文字の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("   \n5\n"));

        int result = InputUtil.inputInt("締切日",1);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_最小値のみ指定_最小値未満の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("-5\n5\n"));

        int result = InputUtil.inputInt("締切日",0);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_最小値のみ指定_文字列の場合再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("買い物\n5\n"));

        int result = InputUtil.inputInt("締切日",0);

        assertEquals(5,result);
    }

    @Test
    void inputInt_範囲指定あり_正常な整数を入力できる() {

        InputUtil.br = new BufferedReader(new StringReader("5"));

        int result = InputUtil.inputInt("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_範囲指定あり_空入力の場合は入力を受け付ける() {

        InputUtil.br = new  BufferedReader(new  StringReader("\n5\n"));

        int result = InputUtil.inputInt("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_範囲指定あり_空白入力の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("      \n5\n"));

        int result = InputUtil.inputInt("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_範囲指定あり_範囲外の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("20\n5"));

        int result = InputUtil.inputInt("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void inputInt_範囲指定あり_文字列の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("買い物\n5\n"));

        int result = InputUtil.inputInt("タスク",1,10);

        assertEquals(5,result);
    }

    @Test 
    void 年月日が正しく入力できる() {
        
        InputUtil.br = new BufferedReader(new StringReader("2026\n10\n10\n"));

        LocalDate result = InputUtil.inputLocalDate();

        assertEquals(LocalDate.of(2026,10,10),result);
    }

    @Test 
    void iputLocaDate_年に0を入力するとnullを返す() {

        InputUtil.br = new BufferedReader(new StringReader("0\n10\n10\n"));

        LocalDate result = InputUtil.inputLocalDate();

        assertNull(result);
    }

    @Test 
    void LocalDate_月に0を入力するとnullを返す() {

        InputUtil.br = new BufferedReader(new StringReader("2026\n0\n10\n"));

        LocalDate result = InputUtil.inputLocalDate();

        assertNull(result);
    }

    @Test 
    void inputLocalDate_日に0を入力するとnullを返す() {

        InputUtil.br = new BufferedReader(new StringReader("2026\n10\n0\n"));

        LocalDate result = InputUtil.inputLocalDate();

        assertNull(result);
    }

    @Test 
    void inputLocalDate_存在しない日付の場合は再入力を受け付ける() {

        InputUtil.br = new BufferedReader(new StringReader("2026\n10\n40\n2026\n10\n10\n"));

        LocalDate result = InputUtil.inputLocalDate();

        assertEquals(LocalDate.of(2026,10,10),result);
    }

    @Test
    void inputLocalDate_過去の日付の場合再入力を受け付ける() {
        
        InputUtil.br = new BufferedReader(new StringReader("1999\n10\n10\n2026\n10\n10"));

        LocalDate result = InputUtil.inputLocalDate();

        assertEquals(LocalDate.of(2026,10,10),result);
    }

    @Test 
    void selectTwo_1を選択するとtrueが返ってくる() {

        InputUtil.br = new BufferedReader(new StringReader("1"));

        boolean result = InputUtil.selectTwo("はい", "いいえ");

        assertTrue(result);
    }

    @Test 
    void selectTwo_2を選択するとfalseが返ってくる() {

        InputUtil.br = new BufferedReader(new StringReader("2"));

        boolean result = InputUtil.selectTwo("はい","いいえ");

        assertFalse(result);
    }

}