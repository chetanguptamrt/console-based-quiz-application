/*
 Create an application for quiz. 
Every time a user logs in, he should be asked for his name, the application should display the questions randomly.
The user should be able to quit answering anytime. 
The score of the user should also be stored in the database.
 */
package quiz;

import java.util.List;
import java.util.Scanner;
import quiz.DAO.Conn;
import quiz.DAO.QuestionDAO;
import quiz.DAO.UserDAO;
import quiz.model.*;

/**
 *
 * @author chetan
 */
public class Quiz {

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            boolean check = true;
            QuestionDAO questionDAO = new QuestionDAO(Conn.getConn());
            UserDAO userDAO = new UserDAO(Conn.getConn());
            do{
                System.out.println("Quiz application");
                System.out.println("Press 1 for user registration");
                System.out.println("Press 2 for user log in");
                System.out.println("Press 3 for exit");
                System.out.print("Enter your choice - ");
                String ch = scan.nextLine();
                switch (ch) {
                    case "1":
                        System.out.println("\nUser Registration- ");
                        System.out.print("Enter Username: ");
                        String username = scan.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scan.nextLine();
                        User user = new User(name, username, "1234");
                        boolean flag = userDAO.saveUser(user);
                        if(flag)
                            System.out.println("User successfully save.\n\n");
                        else
                            System.out.println("Username already exists.\n\n");
                        break;
                    case "2":
                        System.out.println("\nUser log in- ");
                        System.out.print("Enter Username: ");
                        String username2 = scan.nextLine();
                        System.out.print("Enter Password: ");
                        String password = scan.nextLine();
                        User u = userDAO.getUser(username2, password);
                        if(u==null){
                            System.out.println("Invalid credientials.\n\n");
                        } else {
                            System.out.println("\nWelcome "+u.getName()+"!");
                            int toalQuestion = 5;
                            int score = 0;
                            int attemptQuestion = 0;
                            List<Question> questions = questionDAO.getRandomQuestions();
                            for(int i=0; i<toalQuestion; i++) {
                                System.out.println();
                                Question question = questions.get(i);
                                System.out.println("Question "+(i+1)+" - ");
                                System.out.println(question.getQuestion());
                                System.out.println("1. "+question.getOption1());
                                System.out.println("2. "+question.getOption2());
                                System.out.println("3. "+question.getOption3());
                                System.out.println("4. "+question.getOption4());
                                System.out.println("Press 5 for exit");
                                System.out.print("Enter your answer: ");
                                int ans = scan.nextInt();
                                if(ans==question.getAnswer()){
                                    score++;
                                }
                                if(ans==5) {
                                    break;
                                } else {
                                    attemptQuestion++;
                                }
                            }
                            System.out.println("\nYou score is "+score);
                            questionDAO.saveScore(u.getName(), score, toalQuestion, attemptQuestion);
                        }
                        break;
                    case "3":
                        check = false;
                        break;
                    default: 
                        System.out.println("Please enter right choice.\n\n");
                }
            } while(check);
        }
    }
    
}
