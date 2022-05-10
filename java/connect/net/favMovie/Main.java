package net.favMovie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Main {

    
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:../../db/favMovie.db" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        
        String url = "jdbc:sqlite:../../db/favMovie.db";
       
        
        String sql ="CREATE TABLE IF NOT EXISTS movies (\n"
                + "	Movie_id integer PRIMARY KEY,\n"
                + "	Movie_name text NOT NULL,\n"
                + "	Actor_name text NOT NULL,\n"
                + "	Actress_name text NOT NULL,\n"
                + "	YearofRelease text NOT NULL,\n"
                + "	Director_name text NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        
        String url = "jdbc:sqlite:../../db/favMovie.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

   
    public void insert(int id,String movie,String actor, String actress,String year,String Director) {
        String sql = "INSERT INTO movies(Movie_id,Movie_name,Actor_name,Actress_name,YearofRelease,Director_name) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	  pstmt.setInt(1, id);
	            pstmt.setString(2, movie);
	            pstmt.setString(3, actor);
	            pstmt.setString(4, actress);
	            pstmt.setString(5, year);
	            pstmt.setString(6, Director);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectAll(){
        String sql = "SELECT Movie_id,Movie_name,Actor_name,Actress_name,YearofRelease,Director_name FROM movies";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            
            while (rs.next()) {
                System.out.println(rs.getInt("Movie_id") +  "\t" + 
                        rs.getString("Movie_name") + "\t" +
                        rs.getString("Actor_name") + "\t" +
                        rs.getString("Actress_name") + "\t" +
                        rs.getString("YearofRelease") + "\t" +
                        rs.getString("Director_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public void ActMovies(String name){
        String sql = "SELECT Movie_id,Movie_name,Actor_name,Actress_name,YearofRelease,Director_name FROM movies \n"
   			 +"WHERE Actor_name=?;";
 
 try (Connection conn = this.connect();
      PreparedStatement ppstmt  = conn.prepareStatement(sql)){
     
   
     ppstmt.setString(1,name);
     //
     ResultSet rrs  = ppstmt.executeQuery();
     
     
     while (rrs.next()) {
         System.out.println(rrs.getInt("Movie_id") +  "\t" + 
                 rrs.getString("Movie_name") + "\t" +
                 rrs.getString("Actor_name") + "\t" +
                 rrs.getString("Actress_name") + "\t" +
                 rrs.getString("YearofRelease") + "\t" +
                 rrs.getString("Director_name"));
     }
 } catch (SQLException e) {
     System.out.println(e.getMessage());
 }
}

    public static void main(String[] args) {
        createNewDatabase("favMovie.db");
        createNewTable();
        Main fav  = new Main();
        fav.insert(1,"KGF","Yash","Sreenidhi Shetty","2018","Prashanth Neel");
        fav.insert(2,"Ugramm","Sri Murali","Haripriya","2014","Prashanth Neel");
        fav.insert(3,"KGF2","Yash","Sreenidhi Shetty","2021","Prashanth Neel");
        fav.insert(4,"Spider-Man","Tobey-Maguire","Kristen-Dunst","2000","Sam-Raimi");
        fav.insert(5,"Google","Yash","Krithi-Karabandha","2013","Pawan-Wodyer");
        fav.insert(6,"Twilight","Robert Pattinson","Kristen Stewart","2008","Catherine Hardwickie");
        fav.insert(7,"Fifty Shades Darker","Jamie Dornan","Dakota Johnson","2017","James Foley");
        System.out.println("---- MY FAVOURITE MOVIES----");
        System.out.println("Movie_id | Movie_name | Actor_name | Actress_name | YearofRelease | Director_name");
        Main app = new Main();
        app.selectAll();
        System.out.println();
        System.out.println("----Yash Movies----");
        System.out.println("Movie_id | Movie_name | Actor_name | Actress_name | YearofRelease | Director_name");
        Main yash= new Main();
        yash.ActMovies("Yash");
    }
}