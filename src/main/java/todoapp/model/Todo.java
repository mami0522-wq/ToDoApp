package todoapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Todo {
    private String title;
    private boolean done;
    private LocalDate createdAt;
    private LocalDateTime createdTime;
    private LocalDate deadline;

    /**
     * 新しいTodoを作成します。
     * 
     * 
     * @param title タスクのタイトル
     * @param deadline 締切日(未設定の場合はnull)
     */

    public Todo(String title, LocalDate deadline) {
        this.title = title;
        this.done = false;
        this.createdAt = LocalDate.now();
        this.createdTime = LocalDateTime.now();
        this.deadline = deadline;
    }

    /**
     * 保存ファイルから読み込んだデータを元に
     * Todoオブジェクトを生成します。
     * 
     * @param title タスクのタイトル
     * @param done 進行状況
     * @param createdAt タスクの作成日
     * @param createdTime タスクの作成時間
     * @param deadline 締切日(未設定はnull)
     */
    public Todo(String title,boolean done,LocalDate createdAt,LocalDateTime createdTime,LocalDate deadline){
        this.title = title;
        this.done = done;
        this.createdAt = createdAt;
        this.createdTime = createdTime;
        this. deadline = deadline;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getCreateDate() {
        return this.createdAt;
    }

    public LocalDateTime getCreateDateTime() {
        return this.createdTime;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    /**
     * 締切日を表示用文字列として取得します。
     * 
     * @return 締切日(未設定の場合は未設定と表示)
     * 
     */
    public String getDeadlineText() {
        if(this.deadline == null) {
            return "未設定";
        }else{
            return this.deadline.toString();
        }
    }

    /**
     * Todoの情報を表示用文字列へ変換します。
     * 
     * @return 表示用文字列
     */
    @Override
    public String toString() {
        
        return getTitle() + " : " +getStatusText() + "\n" + "作成日 : " + getCreateDate() + getCreateDateTime() + "\n締切日 : " +getDeadlineText() + "\n" + remainingDaysText();
    }

    public boolean isDone() {
        return this.done;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDeadline(LocalDate deadline){
        this.deadline = deadline;
    }

    /**
     * タスクの進行状況を表示用文字列として取得します。
     * 
     * @return 「達成」または「未達成」
     */
    public String getStatusText(){
        if(this.done)
            return  "達成";
        else
            return  "未達成";
    }

    /**
     * 進行状況を切り替えます。
     * 
     * <p>
     * メソッドを実行すると「完了」⇔「未完了」と切り替わります。
     * </p>
     * 
     */
    public void toggleDone(){
        if(!this.done)
            this.done = true;
        else
            this.done = false;
    }

    /**
     * 締切日までの残り日数を取得します。
     * 
     * @return 残り日数(締切日が未設定の場合はnull)
     */
    public  Long getRemainingDays() {
        if(this.deadline == null) {
            return null;
        }

        return ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }

    /**
     * 残りの日数を表示用文字列として取得します。
     * 
     * <p>
     * 締切日に応じて
     * 「残り○日」
     * 「締切日当日」
     * 「期限の超えています。」
     * 「未設定」
     * のいずれかを返します。
     * </p>
     * 
     * @return 表示用文字列
     * 
     */
    public String remainingDaysText() {


        if(this.deadline == null) {
            return "未設定";
        }

        long days = getRemainingDays();

        if(days < 0) {
            return "期限を超えています。";
        }else if(days == 0) {
            return "締切日当日です。";
        }else{
            return "残り" + days + "日";
        }
    }
}
