import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BillPage extends JFrame {
    private UserManager userManager;

    public BillPage(UserManager userManager) {
        this.userManager = userManager;
        this.setTitle("Bill");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Components for bill page
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50));
        JLabel titleLabel = new JLabel("Bill Page");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        topPanel.add(titleLabel);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 400));
        ImageIcon origin = new ImageIcon(getClass().getResource("/bill.png"));
        Image Edit = origin.getImage().getScaledInstance(180, 270, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon logo = new ImageIcon(Edit);
        JLabel leftLabel = new JLabel(logo);
        leftPanel.add(leftLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(350, 400));
        rightPanel.setLayout(new GridLayout(4, 1)); // Changed to 4 rows to add the back button
        
        // Calculate and display total income, total expenditure, and balance
        double totalIncome = calculateTotalIncome();
        double totalExpenditure = calculateTotalExpenditure();
        double balance = totalIncome - totalExpenditure;

        JLabel incomeLabel = new JLabel("Total Income: RM" + totalIncome);
        incomeLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel expenditureLabel = new JLabel("Total Expenditure: RM" + totalExpenditure);
        expenditureLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel balanceLabel = new JLabel("Balance: RM" + balance);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 15));

        rightPanel.add(incomeLabel);
        rightPanel.add(expenditureLabel);
        rightPanel.add(balanceLabel);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setFocusable(false);
        backButton.setPreferredSize(new Dimension(40, 20)); 
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.white);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));

        ImageIcon originhome = new ImageIcon("home.png");
        Image edithome = originhome.getImage().getScaledInstance(35, 40, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon home = new ImageIcon(edithome);
        JLabel homelabel = new JLabel(home);
        homelabel.setHorizontalTextPosition(JLabel.RIGHT);
        backButton.add(homelabel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage(userManager);
            }
        });

        rightPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private double calculateTotalIncome() {
        // Calculate the total income by summing all income entries
        double totalIncome = 0.0;
        for (UserManager.IncomeEntry incomeEntry : userManager.getIncomes()) {
            totalIncome += incomeEntry.getAmount();
        }
        return totalIncome;
    }

    private double calculateTotalExpenditure() {
        // Calculate the total expenditure by summing all expenditure entries
        double totalExpenditure = 0.0;
        for (UserManager.ExpenseEntry expenseEntry : userManager.getExpenses()) {
            totalExpenditure += expenseEntry.getAmount();
        }
        return totalExpenditure;
    }
}
