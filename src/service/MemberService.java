package service;

import java.util.List;

import dao.MemberDao;
import model.Member;

public class MemberService {
	
	private MemberService() {}
	private static MemberService instance = new MemberService();
	public static MemberService getInstance() {
		return instance;
	}
	
	private MemberDao memberDao = MemberDao.getinstance();
	
	public int 주소록추가(Member member) {	
		// 3. DAO에 접근해서 추가 함수를 호출한다(Member)
		return memberDao.추가(member);
	}
	
	public List<Member> 전체목록() {
		return memberDao.전체목록();
	}
}
