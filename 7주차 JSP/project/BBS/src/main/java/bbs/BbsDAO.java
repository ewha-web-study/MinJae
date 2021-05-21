package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	private Connection conn; //데이터베이스에 접근하도록 하는 객체
	private PreparedStatement pstmt;
	private ResultSet rs; 
	
	public BbsDAO() {
		try {
			//String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbURL = "jdbc:mysql://localhost:3306/bbs?useUnicode=true&characterEncoding=UTF-8";
			String dbID = "root"; 
			String dbPassword = "1234"; 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
		}catch (Exception e) {
			e.printStackTrace();
	
		}
	}
	
	public String getDate() { //게시판 글 작성시 현재 서버 시간 기록
		String SQL = "SELECT NOW()"; //현재 시간 가져오는 쿼리문
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			rs = pstmt.executeQuery(); 
			if (rs.next()) {
				return rs.getString(1);
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류 
		
	}
	
	public int getNext() { //게시글 번호
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC()"; // 쿼리문
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			rs = pstmt.executeQuery(); 
			if (rs.next()) {
				return rs.getInt(1)+1; 
				}
			return 1; //첫번째 게시글인 경우
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) //하나의 게시글 데이터베이스에 삽입
	{
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?,?)"; // 쿼리문
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			rs = pstmt.executeQuery(); 
			//return pstmt.executeUpdate(); ->위에서 INSERT 구문은 executeUdate로 작동
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 
	}
	
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10"; // 쿼리문
		ArrayList<Bbs> list = new ArrayList<Bbs>(); 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, getNext() - (pageNumber  - 1)*10);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Bbs bbs = new Bbs(); 
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list; //데이터베이스 오류 
	}
	
	public boolean nextPage(int pageNumber) {//페이징 처리 위한 함수
		
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 bbsAvailable = 1"; // 쿼리문
		ArrayList<Bbs> list = new ArrayList<Bbs>(); 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, getNext() - (pageNumber  - 1)*10);
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				return true; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, bbsID); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				Bbs bbs = new Bbs(); 
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
		return null; 
	}
}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle =?, bbsContent = ? WHERE bbsID ?"; // 쿼리문
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate(); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류 
	}
	
	}
	
	
	
	
	
	
	
	
	
	

