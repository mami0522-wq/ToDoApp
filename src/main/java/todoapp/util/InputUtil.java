package todoapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;

public class InputUtil{

    public  static  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static final int CANCEL = -1;

     /**
      * キャンセル可能な文字列入力します。
      * 
      * <p>
      * "cancel"が入力された場合は
      * nullを返して入力を中断します。
      * </p>
      * 
      * @param message 入力を促すメッセージ
      * @return 入力された文字列。 キャンセル時はnull
      */
    public static String inputStrCancel(String message) {
        while(true) {
            try{
                System.out.println( message + "を入力して下さい。: cancelでメニュー戻ります。");

                String input = br.readLine();
                if(input == null || input.isBlank())
                    continue;
                if("cancel".equals(input))
                    return null;
                return input;
            }catch(IOException e){
                System.out.println("入力が不正です。");
            }
        }
    }

    /**
     * キャンセル可能な整数入力します。
     * 
     * <p>
     * 0がにゅうりょくされた場合は入力を中断します。
     * </p>
     * @param message 　入力を促すメッセージ
     * @param min　入力堪能な最小値
     * @param max 入力可能な最大値
     * @return 入力された整数。キャンセル時は00
     */
    public static int inputIntCancel(String message, int min, int max) {
        while(true) {
            try{
                System.out.println( message  + "を入力して下さい。: 0でメニューに戻ります。");
                String input = br.readLine();
                if((input == null || input.isBlank()))
                    continue;

                int num = Integer.parseInt(input);
                if(num == 0) 
                    return 0;
                
                if(num < min || max < num){
                    System.out.println("範囲外です。");
                    continue;
                }
                return num;
            }catch(NumberFormatException e) {
                System.out.println("値が不正です。");
            }catch(IOException e){
                System.out.println("入力が不正です。");
            }
        }
    }

    /**
     * 入力できる値の最小値、最大値が設定されている整数入力します。
     * 
     * @param message 入力を促すメッセージ
     * @param min 入力可能な最小値
     * @param max 入力可能な最大値
     * @return 入力された整数
     */
    public static int inputInt(String message,int min,int max){

        while(true){

            System.out.println(message +  min +  "~" + max + "を入力して下さい。");

            try{
                String input = br.readLine();

                int num = Integer.parseInt(input);
                if(num == 0)
                    return 0;

                if(num < min || num > max){
                    System.out.println("範囲外です。");
                    continue;
                }

                return num;

            }catch(NumberFormatException e){
                System.out.println("整数を入力して下さい。");
                continue;
            }catch(IOException e){
                System.out.println("エラーが発生しました。");
                continue;
            }
        }
    }


    /**
     * 入力できる値の最小値が設定されている整数入力します。
     * 
     * @param message 入力を促すメッセージ
     * @param min 入力可能な最小値
     * @return 入力された整数
     */
    public static int inputInt(String message,int min) {
        System.out.println(message + "を入力して下さい。");
        while(true){
            try{
                String input = br.readLine();
                
                int num = Integer.parseInt(input);
                if(num == 0)
                    return num;
                if(num < min){
                    System.out.println("範囲外です。");
                    continue;
                }
                return num;
            }catch(NumberFormatException e){
                System.out.println("整数を入力して下さい。");
            }catch(IOException e) {
                System.out.println("入力が不正です。");
            }
        }
    }


  /**
   * 締切日の入力します。
   * 
   * <p>
   * 年・月・日を個別に入力して締切日を作成します。
   * 過去の日付や存在しない日付は受け付けません。
   * 入力をキャンセルした場合はnullを返します。
   * </p>
   * 
   * @return 入力された締切日。キャンセル時はnull。
   */
  public static LocalDate inputLocalDate()  {
    System.out.println("締切日を設定して下さい。");
    
    while(true){
        try{
             LocalDate deadline;
            int year = inputInt("締切日の年",LocalDate.now().getYear());
            if(year == 0)
                return null;         
            int month = inputInt("締切日の月", 1,12);
            if(month == 0) 
                return null;
            int day = inputInt("締切日の日",1,31);
            if(day == 0)
                return null;
            deadline = LocalDate.of(year,month,day);
            if(deadline.isBefore(LocalDate.now())){
                System.out.println("過去の日付は設定できません。");
                continue;
            }
            return deadline;
            
            }catch(DateTimeException e){
                System.out.println("存在しない日付です。");
            }


        }
    }

    /**
     * 2つの選択肢を表示し、ユーザーに選択させます。
     * 
     * <p>
     * 1番目の選択肢が選ばれた場合はtrue,
     * 2番目の選択肢が選ばれた場合はfalseを返します。
     * </p>
     * 
     * @param select1 1番目の選択肢
     * @param select2 2番目の選択肢
     * @return
     * ture: 1番目の選択肢を選択した場合
     * fasle: 2番目の選択肢を選択した場合
     */
    public static boolean selectTwo(String select1, String select2) {

        while(true){
            
            System.out.println("1 : " + select1);
            System.out.println("2 : " + select2);

            int select = inputInt("選択肢",1,2);


            if(select == 1){
                return true;
            }else{
                return false;
            }
    }
        
    }
}
