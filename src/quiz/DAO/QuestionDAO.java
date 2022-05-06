/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.DAO;

import java.util.List;
import quiz.model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author chetan
 */
public class QuestionDAO {
    
    private Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Question> getRandomQuestions() {
        List<Question> questions = new ArrayList<Question>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from questions");
            while(rs.next()) {
                Question question = new Question();
                question.setQuestion(rs.getString("question"));
                question.setOption1(rs.getString("option_1"));
                question.setOption2(rs.getString("option_2"));
                question.setOption3(rs.getString("option_3"));
                question.setOption4(rs.getString("option_4"));
                question.setAnswer(rs.getInt("answer"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int arr[] = new int[5];
        int pos = 0;
        while(true){
            int random = (int)(Math.random()*(5)+1);
            boolean flag = true;
            for(int i=0; i<arr.length && i<pos; i++){
                if(arr[i]==random){
                    flag = false;
                }
            }
            if(flag){
                arr[pos] = random;
                pos++;
            }
            
            if(pos==5) break;
        }
        List<Question> list = new ArrayList<>();
        for(int i=0; i<arr.length; i++){
            list.add(questions.get(arr[i]-1));
        }
        return list;
    }
    
    public void saveScore(String name, int score, int totalQuestion, int attemptQuestion) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into score values(?,?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, score);
            ps.setInt(3, totalQuestion);
            ps.setInt(4, attemptQuestion);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
