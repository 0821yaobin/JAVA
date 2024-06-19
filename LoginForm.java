import java.awt.*;
import javax.swing.*;

public class LoginForm extends JFrame {
    private UserManager userManager;

    public LoginForm(UserManager userManager) {
        this.userManager = userManager;
        this.setTitle("Login");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50));
        JLabel systemLabel = new JLabel("Personal Finance Manager");
        systemLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        topPanel.add(systemLabel);

        // 左侧和右侧用于插入Logo的面板
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 400));
        ImageIcon originalLeft = new ImageIcon(getClass().getResource("/money.png"));
        Image editLeft = originalLeft.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH);
        ImageIcon moneyIconLeft = new ImageIcon(editLeft);
        JLabel imageLabelLeft = new JLabel(moneyIconLeft);
        leftPanel.add(imageLabelLeft); 

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(150, 400));
        ImageIcon originalRight = new ImageIcon(getClass().getResource("/money.png"));
        Image editRight = originalRight.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH);
        ImageIcon moneyIconRight = new ImageIcon(editRight);
        JLabel imageLabelRight = new JLabel(moneyIconRight);
        rightPanel.add(imageLabelRight); 

        // 中间用于放置登录表单的面板
        JPanel centerPanel = new JPanel(null);
        centerPanel.setPreferredSize(new Dimension(300, 400));

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 50, 80, 25);
        centerPanel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 50, 165, 25);
        centerPanel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 80, 80, 25);
        centerPanel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 80, 165, 25);
        centerPanel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(60, 150, 150, 25);
        loginButton.setBackground(Color.GREEN);
        loginButton.setFocusable(false);
        centerPanel.add(loginButton);

        JButton registerButton = new JButton("Go to Register");
        registerButton.setBounds(60, 190, 150, 25);
        registerButton.setBackground(Color.ORANGE);
        registerButton.setFocusable(false);
        centerPanel.add(registerButton);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.length() < 8) {
                JOptionPane.showMessageDialog(LoginForm.this, "Username must be at least 8 characters long.", "Invalid!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(LoginForm.this, "Password must be at least 8 characters long.", "Invalid!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userManager.login(username, password)) {
                LoggedInUser.setUsername(username);
                JOptionPane.showMessageDialog(LoginForm.this, "Login successful!", "Successful!", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 关闭登录窗口
                Main.showHomePage(); // 打开主页窗口
            } else {
                JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password.", "Invalid!", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> Main.showRegisterForm());

        add(topPanel, BorderLayout.NORTH); 
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中显示
        setVisible(true);
    }
}
