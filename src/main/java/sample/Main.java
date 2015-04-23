package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("GameGui");
        initRootLayout();
    }

    public void initRootLayout(){
        try{
            //load root layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/sample.fxml"));
            AnchorPane rootLayout = loader.load();

            //show the scene containing root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //give the controller access to the main app
            Controller controller = loader.getController();
            controller.setMain(this);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //создаем таблицу
        crate();

        //внесем данные
        insert("Петя", 18);

        //запускаем графический интерфейс
        launch(args);
    }

    public Stage getPrimaryStage(){
         return primaryStage;
    }

    private static void crate() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Company " +
                    "(id        INTEGER     PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " name      TEXT        NOT NULL," +
                    " age       INTEGER     NOT NULL);";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert (String name, int age){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO Company (name,age)" +
                    "VALUES ('" + name + "', " + age + ");";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
