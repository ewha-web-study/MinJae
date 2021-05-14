package user;

//ctrl + shift + o 누르면 자동 생성 가능
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
		private Connection conn; //데이터베이스에 접근하도록 하는 객체
		private PreparedStatement pstmt;
		private ResultSet rs; 
		
		public UserDAO() {
			try {
				String dbURL = "jdbc:mysql://localhost:3306/BBS";
				String dbID = "root"; 
				String dbPassword = "1234"; 
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
			}catch (Exception e) {
				e.printStackTrace();
		
			}
		}
		
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; 
			try {
				pstmt = conn.prepareStatement(SQL); 
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString(1).equals(userPassword))
						return 1; //로그인 성공
					else
						return 0; //비밀번호 불일치
				}
				return -1; //아이디가 없음
			}catch(Exception e) {
				e.printStackTrace(); 
			}
			return -2; //데이터베이스 오류
			
		}
}
