package ClassManager;
import java.sql.SQLException;

public class Project {

	public static void main(String[] args) {
		try {
			System.out.println("Starting program");
			SQLConnection connection = new SQLConnection();
			LoginView loginScreen = new LoginView(connection);
		}
		catch(ClassNotFoundException e) {
			System.out.println("Class not recognized");
		}
		catch(SQLException f) {
			System.out.println("Something went wrong with the connection");
		}
	}
}
