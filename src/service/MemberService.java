package service;

import java.util.List;

import dao.MemberDao;
import model.GroupType;
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
	
	public Member 상세보기(int memberId) {
		return memberDao.상세보기(memberId);
	}
	
	public int 삭제(int memberId) {
		return memberDao.삭제(memberId);
	}
	
	public int 수정(Member member) {
		return memberDao.수정(member);
	}
	
	public List<Member> 그룹목록(GroupType groupType) {
		return memberDao.그룹목록(groupType);
	}
}
