import java.awt.*;
import javax.swing.*;

public class HomePage extends JFrame {

    private UserManager userManager;

    public HomePage(UserManager userManager) {
        this.userManager = userManager;
        this.setTitle("Home Page");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50));
        JLabel systemLabel = new JLabel("Personal Finance Manger");
        systemLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        topPanel.add(systemLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));
        
        JButton expendButton = createStyledButton("Expend", Color.BLUE);
        JButton incomeButton = createStyledButton("Income", Color.BLUE);
        JButton billButton = createStyledButton("Bill", Color.ORANGE);
        JButton helpButton = createStyledButton("Help", Color.ORANGE);
        JButton logoutButton = createStyledButton("Logout", Color.RED);

        expendButton.addActionListener(e -> {
            dispose();
            new ExpendPage(userManager);
        });

        incomeButton.addActionListener(e -> {
            dispose();
            new IncomePage(userManager);
        });

        billButton.addActionListener(e -> {
            dispose();
            new BillPage(userManager);
        });

        logoutButton.addActionListener(e -> Main.showLoginForm());
        ImageIcon originLog = new ImageIcon("logout.png");
        Image editLog = originLog.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon logout = new ImageIcon(editLog);
        JLabel logoutlabel = new JLabel(logout);
        logoutlabel.setHorizontalTextPosition(JLabel.RIGHT);
        logoutButton.add(logoutlabel);

        helpButton.addActionListener(e -> showHelpDialog());

        centerPanel.add(expendButton);
        centerPanel.add(incomeButton);
        centerPanel.add(billButton);
        centerPanel.add(helpButton);
        centerPanel.add(new JLabel());
        centerPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showHelpDialog() {
        JDialog helpDialog = new JDialog(this, "Help", true);
        helpDialog.setSize(400, 300);
        helpDialog.setLayout(new BorderLayout());

        JTextArea helpTextArea = new JTextArea();
        helpTextArea.setEditable(false);
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        helpTextArea.setText(
            "Income Button:\n" +
            "Click this button to go to the Income page, where you can add details about your income, such as source and amount.\n\n" +
            "Expenditure Button:\n" +
            "Click this button to go to the Expenditure page, where you can add details about your expenses, such as description and amount.\n\n" +
            "Bill Button:\n" +
            "Click this button to view a summary of your total income, total expenditure, and the remaining balance.\n\n"
        );

        helpDialog.add(new JScrollPane(helpTextArea), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(600,25));
        closeButton.setFocusable(false);
        closeButton.setBackground(Color.red);
        closeButton.setForeground(Color.white);
        closeButton.setFont(new Font("Arial", Font.BOLD, 15));

        closeButton.addActionListener(e -> helpDialog.dispose());
        helpDialog.add(closeButton, BorderLayout.SOUTH);

        helpDialog.setLocationRelativeTo(this);
        helpDialog.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15)); 
        return button;
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        new HomePage(userManager);
    }
}
