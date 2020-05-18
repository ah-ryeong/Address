package dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import db.DBUtils;
import model.GroupType;
import model.Member;

public class MemberDao {

	private final static String TAG = "MemberDao : ";

	private MemberDao() {
	}

	private static MemberDao instance = new MemberDao();

	public static MemberDao getinstance() {
		return instance;
	}

	// DML은 return 값이 int이다. 리턴되는 값은 변경된 행의 개수이다.
	public int 추가(Member member) {
		final String SQL = "INSERT INTO member(id, name, phone, address, groupType) "
				+ "VALUES(member_seq.nextval,?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();

			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);

			// 3//. 물음표 완성
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());

			// 4. 쿼리 전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "추가 오류 : " + e.getMessage());
		} finally { // 무조건 실행
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}

	public int 삭제(int memberId) {
		final String SQL = "DELETE * FROM member WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();

			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);

			// 3//. 물음표 완성
			pstmt.setInt(1, memberId);
			// 4. 쿼리 전송 (flush + rs받기)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "전체목록오류 : " + e.getMessage());
		} finally { // 무조건 실행
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}

	public int 수정(Member member) {
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
		final String SQL = "UPDATE member SET name=?, phone=?, address=?, groupType=? WHERE id=? ";

		// 변수 선언 (finally에서 쓰기 위해서)
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
			conn = DBConnection.getConnection(); // 스트림

			// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);

			// 3. 물음표 완성
			pstmt.setString(1, member.getName()); // 1번째는 이름 불러오기
			pstmt.setString(2, member.getPhone()); // 2번째는 번호 불러오기
			pstmt.setString(3, member.getAddress()); // 3번째는 주소 불러오기
			pstmt.setString(4, member.getGroupType().toString()); // 4번째는 그룹불러오기
			pstmt.setInt(5, member.getId()); // 4번째는 그룹불러오기

			// 4. 쿼리전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;

		} catch (Exception e) {
			System.out.println("추가오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
		}

		// 무조건 실행됨
		finally {
			DBUtils.close(conn, pstmt);
		}

		return -1;
	}

	// DQL 은 return값이 ResultSet == Cursor
	public Member 상세보기(int id) {
		final String SQL = "SELECT * FROM member WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;

		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();

			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);

			// 3//. 물음표 완성
			pstmt.setInt(1, id);
			// 4. 쿼리 전송 (flush + rs받기)
			rs = pstmt.executeQuery();
			if (rs.next()) { // return 값이 true, false
				member = Member.builder().id(rs.getInt("id")).name(rs.getString("name")).phone(rs.getString("phone"))
						.address(rs.getString("address")).groupType(GroupType.valueOf(rs.getString("groupType")))
						.build();
			}
			return member;
		} catch (Exception e) {
			System.out.println(TAG + "전체목록오류 : " + e.getMessage());
		} finally { // 무조건 실행
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	public List<Member> 전체목록() {
		final String SQL = "SELECT * FROM member ORDER BY id";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<Member>();
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();

			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);

			// 3//. 물음표 완성

			// 4. 쿼리 전송 (flush + rs받기)
			rs = pstmt.executeQuery();
			while (rs.next()) { // return 값이 true, false
				members.add(Member.builder().id(rs.getInt("id")).name(rs.getString("name")).phone(rs.getString("phone"))
						.address(rs.getString("address")).groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println(TAG + "전체목록오류 : " + e.getMessage());
		} finally { // 무조건 실행
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	public List<Member> 그룹목록(GroupType groupType) {
		final String SQL = "SELECT * FROM member WHERE groupType = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<Member>();
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();

			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);

			// 3//. 물음표 완성
			pstmt.setString(1, groupType.toString());

			// 4. 쿼리 전송 (flush + rs받기)
			rs = pstmt.executeQuery();
			while (rs.next()) { // return 값이 true, false
				members.add(Member.builder().id(rs.getInt("id")).name(rs.getString("name")).phone(rs.getString("phone"))
						.address(rs.getString("address")).groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println(TAG + "그룹목록오류 : " + e.getMessage());
		} finally { // 무조건 실행
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}
}
