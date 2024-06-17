import java.awt.*;
import javax.swing.*;

public class RegisterForm extends JFrame {
    private UserManager userManager;

    public RegisterForm(UserManager userManager) {
        this.userManager = userManager;
        this.setTitle("Register");
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
        Image editLeft = originalLeft.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon moneyIconLeft = new ImageIcon(editLeft);
        JLabel imageLabelLeft = new JLabel(moneyIconLeft);
        leftPanel.add(imageLabelLeft); 

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(150, 400));
        ImageIcon originalRight = new ImageIcon(getClass().getResource("/money.png"));
        Image editRight = originalRight.getImage().getScaledInstance(120, 270, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon moneyIconRight = new ImageIcon(editRight);
        JLabel imageLabelRight = new JLabel(moneyIconRight);
        rightPanel.add(imageLabelRight); 

        // 中间用于放置注册表单的面板
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

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(60, 150, 150, 25);
        registerButton.setBackground(Color.GREEN);
        registerButton.setFocusable(false);
        centerPanel.add(registerButton);

        JButton loginButton = new JButton("Go to Login");
        loginButton.setBounds(60, 190, 150, 25);
        loginButton.setBackground(Color.ORANGE);
        loginButton.setFocusable(false);
        centerPanel.add(loginButton);

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (userManager.register(username, password)) {
                JOptionPane.showMessageDialog(RegisterForm.this, "Registration successful!","Successful!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegisterForm.this, "Username already exists.","Invalid!", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener(e -> Main.showLoginForm());

        add(topPanel, BorderLayout.NORTH); 
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setResizable(false);
        setLocationRelativeTo(null); // 窗口居中显示
        setVisible(true);
    }
}
