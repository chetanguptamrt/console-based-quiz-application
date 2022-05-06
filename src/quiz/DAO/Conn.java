/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chetan
 */
public class Conn {
    
    private static Connection connection;
    
    public static Connection getConn(){
        if(connection==null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "Champ123@");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
    
}
