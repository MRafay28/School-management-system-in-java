
import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import student.Student;

public class Score {

    Connection conn = (Connection) MyConnection.getConnection();
    PreparedStatement ps;

    public int max() {//getting max student id in database and increment by 1 and dispplay in UI
        int id = 0;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id + 1;
    }

    public boolean getDetails(int sid, int semesterNo) {
        try {
            ps = conn.prepareStatement("select * from course where student_id =? and semester =?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//                 Dashboard D=new Dashboard();
                Dashboard.jTextField13.setText(String.valueOf(rs.getInt(2))); //2 is written becuase  pay id ha simple wali 2 pay student id ha
                Dashboard.jTextField17.setText(String.valueOf(rs.getInt(3))); //3 pay semster no
                Dashboard.jTextCourse1.setText(String.valueOf(rs.getString(4)));
                Dashboard.jTextCourse2.setText(String.valueOf(rs.getString(5)));
                Dashboard.jTextCourse3.setText(String.valueOf(rs.getString(6)));
                Dashboard.jTextCourse4.setText(String.valueOf(rs.getString(7)));
                Dashboard.jTextCourse5.setText(String.valueOf(rs.getString(8)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student ID or semester no does not exists");
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    //check score id  is already taken or not 

    public boolean isIDExists(int id) {
        try {
            ps = conn.prepareStatement("select * from score where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    //check whether the student id  or semester no exist or not
    public boolean isSIDSemsterNoExists(int sid, int semesterNo) {
        try {
            ps = conn.prepareStatement("select * from score where student_id=? and semester =?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    //insert score  into score  table
    public void insert(int id, int sid, int semesterNo, String course1, String course2, String course3, String course4, String course5, double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "insert into score values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semesterNo);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, average);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score  added successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //get all values from score  table
    public void getScoreValue(JTable table,String searchValue){
        String sql="select * from score where concat(id,student_id,semester)like ? order by id desc";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs=ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()){
            row=new Object[14];
            row[0]=rs.getInt(1);
            row[1]=rs.getInt(2);
            row[2]=rs.getInt(3);
            row[3]=rs.getString(4);
            row[4]=rs.getDouble(5);
            row[5]=rs.getString(6);
            row[6]=rs.getDouble(7);
            row[7]=rs.getString(8);
            row[8]=rs.getDouble(9);
            row[9]=rs.getString(10);
            row[10]=rs.getDouble(11);
            row[11]=rs.getString(12);
            row[12]=rs.getDouble(13);
            row[13]=rs.getDouble(14);
            model.addRow(row);
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //update score record 
    
    
    public void updateStudentRecord(int id,double score1,double score2,double score3,double score4,double score5,double average){
        String sql="update score set score1=?,score2=?,score3=?,score4=?,score5=?,average=? where id=?";
        try {
            ps=conn.prepareStatement(sql);
           
            ps.setDouble(1, score1);
           ps.setDouble(2, score2);
           ps.setDouble(3, score3);
           ps.setDouble(4, score4);
           ps.setDouble(5, score5);
           ps.setDouble(6, average);
             ps.setInt(7, id);
            if(ps.executeUpdate()>0){
                 JOptionPane.showMessageDialog(null, "Score  update successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }

}
