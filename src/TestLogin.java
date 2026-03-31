import model.User;
import ui.TeamUI;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("Starting TestLogin");
        try {
            User user = new User("it_team", "123", "IT");
            new TeamUI(user);
            System.out.println("TeamUI initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
