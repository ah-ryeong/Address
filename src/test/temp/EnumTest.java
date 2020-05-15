package test.temp;

import org.junit.Test;

import model.GroupType;
import model.Member;

public class EnumTest {

	@Test
	public void MyEnumTest() {
		Member m = new Member(1, "홍길동", "0102222", "서울", GroupType.가족);
		System.out.println(GroupType.가족.toString());
	}
}
