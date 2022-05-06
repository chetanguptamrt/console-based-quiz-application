/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import quiz.model.User;
/**
 *
 * @author chetan
 */
public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean saveUser(User user){
         try {
            PreparedStatement ps2 = connection.prepareStatement("SELECT EXISTS (SELECT * FROM USER WHERE username = ?) as 'check'");
            ps2.setString(1, user.getUsername());
            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next()) {
                if(rs2.getInt("check")==1){
                    return false;
                }
            }
            PreparedStatement ps = connection.prepareStatement("insert into user values(?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return true;
    }
    
    public User getUser(String username, String password) {
        User user = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from user where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                user = new User(rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    
}
