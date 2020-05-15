package model;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {
	
	private int id; // PK 회원 아이디 아니고 number
	private String name; // 이름
	private String phone; // 전화번호 (연산할 일이 없기 때문에 String)으로 만든다.
	private String address; // 주소
	// 그룹 : 친구, 회사, 학교, 가족
	private GroupType groupType;
	
	// 사용할 생성자
	public Member(String name, String phone, String address, GroupType group) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = group;
	}
	
	//더미데이터 생성자
	@Builder
	public Member(int id, String name, String phone, String address, GroupType groupType) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = groupType;
	}
	
	@Override
	public String toString() {
		return id + ". " + name;
	}
	
	
	
}
