package product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * * JDBC 실습 (중복코드 제거)
 * @author SY
 * @since 2022.12.14
 *
 */

public class ProductRun {

   public static void main(String[] args) {
      
      // 사용할 객체 및 변수 셋팅
      
      // 스캐너
      Scanner sc = new Scanner(System.in);
      
      // JDBC 객체
      Connection conn = null;
      Statement stmt = null;
      ResultSet rset = null;
      
      // 일반변수 : int
      int result = 0;
      int price = 0;
      
      // 일반변수 : String
      String sql = null;
      String pname = null;
      String type = null;
      
      // 일반변수 : boolean
      boolean dml = true; 
      
      while(true) {  // -- whileLoop start
         
         // 메뉴 출력 및 선택
         System.out.println("JDBC 메뉴");
         System.out.println("1. SELECT 조회");
         System.out.println("2. INSERT 추가");
         System.out.println("3. DELETE 삭제");
         System.out.println("4. 종료");
         
         System.out.print("메뉴를 선택해주세요 > ");
         int menu = sc.nextInt();
         
         sc.nextLine(); // 버퍼에 남아있는 엔터 제거
         
         try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 클래스파일 찾기 및 등록
            
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC"); // conn객체 생성
            
            stmt = conn.createStatement(); // stmt 객체 생성
            
            if(menu == 1) { // select
               
               dml = false; // dml 아님
               sql = "SELECT * FROM PRODUCT ORDER BY PNO";
               
            }else if (menu == 2){ // insert
               
               dml = true; // dml 맞음
               type = "insert"; // 마지막 콘솔 출력을 위한 타입 지정
               
               System.out.print("상품명 입력 : ");
               pname = sc.nextLine();
               
               System.out.print("상품가격 입력 : ");
               price = sc.nextInt();
               
               sc.nextLine(); // 버퍼에 남아있는 엔터 제거
               
               sql = "INSERT INTO PRODUCT VALUES(SEQ_PNO.NEXTVAL,'" + pname + "', " + price +", SYSDATE)";
               
            }else if(menu == 3) { // DELETE
               
               dml = true; // dml 맞음
               type = "delete"; // 마지막 콘솔 출력을 위한 타입 지정
               
               System.out.print("상품명 입력 : ");
               pname = sc.nextLine();
               
               sql = "DELETE FROM PRODUCT WHERE PNAME LIKE '%" + pname + "%'" ; // 해당키워드에 해당하면 전부 삭제
               
            }else if (menu == 4) {
               
               System.out.println("프로그램을 종료합니다.");
               
               sc.close(); // 스캐너 객체 반납
               return; // 메소드 빠져나옴
               
            }
            
            // dml == true 인 경우 수행될 코드(insert, update, delete)
            if(dml) { 
               result = stmt.executeUpdate(sql);
               
               if(result > 0) {
                  conn.commit(); // 성공시 커밋
               }else {
                  conn.rollback(); // 실패시 롤백
               }
               
               System.out.println();
               
               if(result >0) {
                  System.out.println(type + " 성공!!"); // 각 타입의 성공여부
               }else {
                  System.out.println(type + " 실패!!"); // 각 타입의 실패여부
               }
               
            // dml == false 인 경우 수행될 코드 (select)   
            }else { 
               rset = stmt.executeQuery(sql);
               
               while(rset.next()) {
                  int pno = rset.getInt("PNO");
                  pname = rset.getString("PNAME");
                  price = rset.getInt("PRICE");
                  Date regdate = rset.getDate("REG_DATE");
                  
                  System.out.println(pno + ", " + pname + ", " + price + ", " + regdate);
               }
            }
         }catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (SQLException e) {
            e.printStackTrace();
         }finally {
            try { // 자원반납
               rset.close();
               stmt.close();
               conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
            
         }
      System.out.println();
      
      } // -- whileLoop end
   }
}