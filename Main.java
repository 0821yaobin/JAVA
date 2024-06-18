import javax.swing.*;

public class Main {
    private static final UserManager userManager = new UserManager();
    private static JFrame currentFrame;

    public static void main(String[] args) {
        showLoginForm();
    }

    public static void showLoginForm() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new LoginForm(userManager);
    }

    public static void showRegisterForm() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new RegisterForm(userManager);
    }

    public static void showHomePage() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new HomePage(userManager);
    }
}
