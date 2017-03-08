/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tourismmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static sun.security.jgss.GSSUtil.login;


/**
 *
 * @author anupam
 */
public class TourismManagement extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        login l=new login();
        l.setVisible(true);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
            Statement st = con.createStatement();
            st.executeUpdate("create database if not exists tm;");
            con.close();
            
            Connection ncon = DriverManager.getConnection("jdbc:mysql://localhost:3306/tm",
                    "root","root");
            Statement stmt = ncon.createStatement();
            
            //creating table address
            String sql = "create table if not exists address (a_id int auto_increment,"
                    + " country varchar(30), state varchar(30), district varchar (30),"
                    + " taluka varchar(30), city varchar(30), primary key (a_id));";
            
            stmt.executeUpdate(sql);    

            //crating person table
            sql = "create table if not exists person (h_id int auto_increment, h_firstname"
                    + " varchar(30), h_lastname varchar(30), dob date, a_id int, h_email "
                    + "varchar(30), h_street varchar(30), h_landmark varchar(30), "
                    + "h_pincode int (6), h_house_no varchar (30), primary key(h_id), "
                    + "foreign key (a_id) references address(a_id));";

            stmt.executeUpdate(sql);
            

            //creating branch table
            
            sql = "create table if not exists branch (b_name varchar(30), b_street varchar(30)"
                    + ", b_landmark varchar(30), b_pincode int (6), a_id int, primary key (b_name), "
                    + "foreign key (a_id) references address(a_id));";
            
            stmt.executeUpdate(sql);
            
           //creating employee table 
            
            sql = "create table if not exists employee (e_id int auto_increment, h_id int, "
                    + "e_qualification varchar(30), e_join_date date, e_post varchar(30), "
                    + "salary int, e_works_for int, b_name varchar(30), password varchar (30), primary key(e_id), "
                    + "foreign key (h_id) references person(h_id), foreign key (e_works_for) "
                    + "references employee(e_id), foreign key (b_name) references branch(b_name));";
           
            stmt.executeUpdate(sql);
            
            sql = "select a_id from address;";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            //System.out.println(rs.next());
            
            //System.out.println(Integer.parseInt(rs.getString(1)));
            
            if(!rs.next()) {
                //insert address
                
                sql = "insert into address (country, state, district, taluka, city)"
                        + "values('India' , 'Maharashtra' , 'Pune', 'Haveli', 'Shivajinagar' );";
                
                stmt.executeUpdate(sql);
                
                sql = "insert into person (h_firstname, h_lastname, dob, a_id, h_email "
                    + ", h_street, h_landmark, "
                    + "h_pincode, h_house_no) values('Ashok' "
                        + ", 'Kumar' , '1980-01-01', 1, 'ashokkumar@gmail.com', "
                        + "'JM Road', 'Sancheti', 413005, 'h_3000');";
                
                stmt.executeUpdate(sql);
               
                sql  = "insert into branch (b_name, b_street, b_landmark, b_pincode, a_id) "
                        + "values('HeadOffice_Pune' , 'JM Road' , 'Sancheti', 413005 ,1);";
                
                stmt.executeUpdate(sql);
                
                sql= "insert into employee (h_id, e_qualification, e_join_date, e_post, "
                        + "salary, e_works_for, b_name, password) values(1 , 'B.tech', '2000-01-01', "
                        + "'admin', 100000, 1, 'HeadOffice_Pune', 'admin');";
                
                stmt.executeUpdate(sql);            
            }
            
            
            
            
        con.close();
        } 
        catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Sorry Please Check username and Password of db");
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Sorry Please Check username and Password of db");
        }
    }
    
}
