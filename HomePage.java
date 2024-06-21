import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        JLabel systemLabel = new JLabel("Personal Finance Manager");
        systemLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        topPanel.add(systemLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton expendButton = createStyledButton("Expend", Color.BLUE);
        JButton incomeButton = createStyledButton("Income", Color.BLUE);
        JButton billButton = createStyledButton("Bill", Color.ORANGE);
        JButton helpButton = createStyledButton("Help", Color.ORANGE);
        JButton logoutButton = createStyledButton("Logout", Color.RED);
        JButton reportButton = createStyledButton("Report", Color.GREEN);

        ImageIcon oriLogout = new ImageIcon("logout.png");
        Image editLogout = oriLogout.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon Logout = new ImageIcon(editLogout);
        JLabel logoutLabel = new JLabel(Logout);
        logoutLabel.setHorizontalTextPosition(JLabel.RIGHT);
        logoutButton.add(logoutLabel);

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

        logoutButton.addActionListener(e -> {
            dispose();
            Main.showLoginForm();
        });

        helpButton.addActionListener(e -> showHelpDialog());

        reportButton.addActionListener(e -> showReportDialog());

        centerPanel.add(expendButton);
        centerPanel.add(incomeButton);
        centerPanel.add(billButton);
        centerPanel.add(helpButton);
        centerPanel.add(reportButton);
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

        JTextPane helpTextPane = new JTextPane();
        helpTextPane.setEditable(false);
        helpTextPane.setContentType("text/html");

        String helpText = "<html>"
                + "<body style='font-family: Arial;'>"
                + "<b>Income Button:</b><br>"
                + "Click this button to go to the Income page, where you can add details about your income, such as source and amount.<br><br>"
                + "<b>Expenditure Button:</b><br>"
                + "Click this button to go to the Expenditure page, where you can add details about your expenses, such as description and amount.<br><br>"
                + "<b>Bill Button:</b><br>"
                + "Click this button to view a summary of your total income, total expenditure, and the remaining balance.<br>"
                + "</body>"
                + "</html>";

        helpTextPane.setText(helpText);
        helpDialog.add(new JScrollPane(helpTextPane), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(600, 25));
        closeButton.setFocusable(false);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 15));

        closeButton.addActionListener(e -> helpDialog.dispose());
        helpDialog.add(closeButton, BorderLayout.SOUTH);

        helpDialog.setLocationRelativeTo(this);
        helpDialog.setVisible(true);
    }

    private void showReportDialog() {
        JDialog reportDialog = new JDialog(this, "Report", true);
        reportDialog.setSize(600, 400);
        reportDialog.setLayout(new BorderLayout());

        // Create the table to display expenses and incomes
        String[] columnNames = {"Type", "Description", "Amount"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Add expenses to the table
        List<UserManager.ExpenseEntry> expenses = userManager.getExpenses();
        for (UserManager.ExpenseEntry expense : expenses) {
            model.addRow(new Object[]{"Expenditure", expense.getDescription(), expense.getAmount()});
        }

        // Add incomes to the table
        List<UserManager.IncomeEntry> incomes = userManager.getIncomes();
        for (UserManager.IncomeEntry income : incomes) {
            model.addRow(new Object[]{"Income", income.getDescription(), income.getAmount()});
        }

        // Calculate and display the balance
        double totalIncome = incomes.stream().mapToDouble(UserManager.IncomeEntry::getAmount).sum();
        double totalExpenditure = expenses.stream().mapToDouble(UserManager.ExpenseEntry::getAmount).sum();
        double balance = totalIncome - totalExpenditure;
        JLabel balanceLabel = new JLabel("Balance (RM): " + balance);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        reportDialog.add(new JScrollPane(table), BorderLayout.CENTER);
        reportDialog.add(balanceLabel, BorderLayout.NORTH);

        JButton printButton = new JButton("Print");
        printButton.setBackground(Color.BLUE);
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Arial", Font.BOLD, 15));
        printButton.addActionListener(e -> printReport(table, balance));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);

        reportDialog.add(buttonPanel, BorderLayout.SOUTH);

        reportDialog.setLocationRelativeTo(this);
        reportDialog.setVisible(true);
    }

    private void printReport(JTable table, double balance) {
        try {
            boolean complete = table.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Printing Complete", "Printing Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Printing Canceled", "Printing Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Printing Failed: " + e.getMessage(), "Printing Result", JOptionPane.ERROR_MESSAGE);
        }
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
