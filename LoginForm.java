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
        topPanel.setPreferredSize(new Dimension(600, 50)); // 设置面板大小
        JLabel systemLabel = new JLabel("Personal Finance Manager"); // 创建系统名称标签
        systemLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // 设置字体和大小
        topPanel.add(systemLabel); // 将系统名称标签添加到顶部面板中

        // 左侧和右侧用于插入Logo的面板
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 400));
        ImageIcon originalLeft = new ImageIcon(getClass().getResource("/money.png"));
        Image EditLeft = originalLeft.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon moneyIconLeft = new ImageIcon(EditLeft);
        JLabel imageLabelLeft = new JLabel(moneyIconLeft);
        leftPanel.add(imageLabelLeft); 

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(150, 400));
        ImageIcon originalRight = new ImageIcon(getClass().getResource("/money.png"));
        Image EditRight = originalRight.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon moneyIconRight = new ImageIcon(EditRight);
        JLabel imageLabelRight = new JLabel(moneyIconRight);
        rightPanel.add(imageLabelRight); 

        // 中间用于放置登录表单的面板
        JPanel centerPanel = new JPanel(null); // 使用绝对定位
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
        loginButton.setBackground(Color.green);
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
