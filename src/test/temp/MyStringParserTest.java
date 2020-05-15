package test.temp;

import org.junit.Test;

public class MyStringParserTest {

	@Test
	public void getId() {
		int memberId = Integer.parseInt("200. 홍길동".split("[.]")[0]);
		System.out.println(memberId);
	}
}
