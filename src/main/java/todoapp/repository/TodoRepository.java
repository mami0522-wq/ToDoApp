package todoapp.repository;

import todoapp.model.Todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TodoRepository{


	private final Path filePath;

	public TodoRepository() {
		this(Path.of("todo.txt"));
	}

	public TodoRepository(Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Todo一覧を保存ファイル(todo.txt)へ保存します。
	 * 
	 * <p>
	 * Todo一覧をcsv形式へ変換し、
	 * 保存ファイルへ書き込みます。
	 * 締切日が見設定の場合は空文字として保存します。
	 * </p>
	 * 
	 * 
	 * @param todos 保存するTodo一覧
	 */

	public void save(List<Todo> todos) {
		System.out.println("save version A");

		List<String> lines = new ArrayList<>();


		for(Todo todo : todos) {
			String line = todo.getTitle() + "," + todo.isDone() + ","  + todo.getCreateDate() + "," + todo.getCreateDateTime() + "," + 
			(todo.getDeadline() == null
				?""
				:todo.getDeadline()
			);

			lines.add(line);	
		}

		try{
			Files.write(filePath,lines);
		}catch(IOException e) {
			System.out.println("保存失敗");
		}
	}

	/**
	 * 保存ファイルからTodo一覧を読み込みます。
	 * 
	 * <p>
	 * CSV形式のデータを読み込み、
	 * 各項目を検証してTodoオブジェクトへ変換します。
	 * </p>
	 * 
	 * <p>
	 * 不正なデータはbackupフォルダへ退避し、
	 * 正常なデータのみ保存ファイルへ再保存します。
	 * </p>
	 * 
	 * @return 読み込んだTodo一覧
	 */

	public List<Todo> load() {
		System.out.println("load(ver2.0)開始");
		System.out.println("読み込みファイル: " + filePath.toAbsolutePath());
		System.out.println("読み込みファイル: " + filePath.toAbsolutePath().normalize());

		List<Todo> todos = new ArrayList<>();
		List<String> backupLines = new ArrayList<>();
		int num = 1;

		try{
			if(Files.exists(filePath)){
				List<String> lines = Files.readAllLines(filePath);

				for(String line : lines){

					try{
						todos.add(parseTodo(line));
					}catch(Exception e){
						backupLines.add(backupStr(num,line,e.getMessage(),getStackTraceAsString(e)));
					}

					num ++;
				}
			}
		}catch(IOException e){
			System.out.println("読込失敗");
		}

		try{
			Files.createDirectories(Path.of("backup"));
		}catch(IOException e) {
			System.out.println("生成できません。");
		}


		if(!backupLines.isEmpty()) {
			save(todos);
			try{
				Files.write(Path.of("backup", datefomatter() + ".txt"),backupLines,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
			}catch(IOException e){
				System.out.println("読み込み失敗");
				e.printStackTrace();
			}
		}
		return todos;
	}	

	//保存データチェック
	private static Todo parseTodo(String line) {

		if(line == null) {
			throw new IllegalArgumentException("データがありません。");
		}

		line = line.trim();

		if(line.isBlank()) {
			throw new IllegalArgumentException("データが空です。");
		}

		String[] data = line.split(",");

		//項目数チェック
		checkLength(data, 4);

		String title = parseTitle(data[0]);
		boolean done = pareDone(data[1]);
		LocalDate createDate = parseCreateDate(data[2]);
		LocalDateTime createDateTime = parseCreatDatTime(data[3]);
		LocalDate deadLine = null;
		if(data.length >= 5){
			deadLine = parseDeadLine(data[4]);
		}

		return new Todo(title,done,createDate,createDateTime,deadLine);
	}

	//タイトルをチェック
	private static String parseTitle(String value) {

		if(value == null){
			throw new IllegalArgumentException("タイトルがありません。");
		}

		value = value.trim();

		if(value.isBlank()){
			throw new IllegalArgumentException("タイトルが空です。");
		}


		return value;

	}

	//進行状況のチェック
	private static boolean pareDone(String value) {
		value = value.trim();

		if(!value.equals("true") && !value.equals("false")) {
			throw new IllegalArgumentException("完了が不正です。");
		}

		return Boolean.parseBoolean(value);
	}

	//作成日のチェック
	private static  LocalDate parseCreateDate(String value) {
		if(value == null) {
			throw new IllegalArgumentException("作成日がありません。");
		}
	

		value = value.trim();

		if(value.isEmpty()) {
			throw new IllegalArgumentException("作成日が空です。");
		}

		try{
			return LocalDate.parse(value);
		}catch(DateTimeParseException e) {
			throw new  IllegalArgumentException("作成日の形式が不正です。");
		}	
	}

	//作成時間のチェック
	private static LocalDateTime parseCreatDatTime(String value) {
		if(value == null){
			throw new IllegalArgumentException("作成時間がありません。");
		}

		value = value.trim();

		if(value.isEmpty()){
			throw new IllegalArgumentException("作成時間が空です");
		}

		try{
			return LocalDateTime.parse(value);
		}catch(DateTimeParseException e){
			throw new IllegalArgumentException("作成時間の形式が不正です。");
		}
	}

	//締切日をチェックする
	private static LocalDate parseDeadLine(String value) {
		if(value == null)
			return null;

		value = value.trim();

		if(value.isEmpty())
			return null;

		try{
			return LocalDate.parse(value);
		}catch(DateTimeParseException e){
			throw new IllegalArgumentException("締切日の形式が不正です。");
		}
	}

	//保存データの長さが適当か確認
	private static void checkLength(String[] data,int length) {
		
		if(data.length < length) {
			throw new IllegalArgumentException("データ不足です。");
		}
	}

	//壊れたデータの保存
	private static String backupStr(int num,String line,String e_message,String e_stackTrace) {

		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分ss秒");

		
		String breakDay = LocalDateTime.now().format(fomatter);

		System.out.println("不正なデータを呼び飛ばしました。");
		System.out.println(e_stackTrace);

		return   breakDay +  "\n\n" 
		+ "データ :" + line + "\n" 
		+ num + "行目 : " + e_message + 
		"\n" + "---------------------------------------------------------------------------";
	}

	//スタックトレースを文字列に変換
	 private static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

	private static String datefomatter() {
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		
		return LocalDateTime.now().format(fomatter);
	}
}