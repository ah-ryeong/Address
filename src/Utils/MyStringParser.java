package Utils;

public class MyStringParser {
	
	public static int getId(String selectedList) {
		return Integer.parseInt(selectedList.split("\\.")[0]);
		// .은 파싱이 안됨 , \\혹은 [] 필요
	}
}
