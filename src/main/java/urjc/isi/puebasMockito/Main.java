package urjc.isi.puebasMockito;


import static spark.Spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.net.URISyntaxException;

public class Main {
  


  public static String doWork() throws ClassNotFoundException, URISyntaxException {
    String result = new String("Hola Mundo");

    return result;
    }

  public static void main(String[] args) throws ClassNotFoundException {
        port(getHerokuAssignedPort());

        // spark server
        get("/hello", (req, res) -> doWork());

        }
  static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    } 
}
