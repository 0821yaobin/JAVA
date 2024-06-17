import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class IncomePage extends JFrame {
    private UserManager userManager;

    public IncomePage(UserManager userManager) {
        this.userManager = userManager;
        this.setTitle("Income");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Components for income page
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50));
        JLabel titleLabel = new JLabel("Income Page");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel itemLabel = new JLabel("Item Description:");
        itemLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField itemField = new JTextField();

        JLabel amountLabel = new JLabel("Amount (RM):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField amountField = new JTextField();

        centerPanel.add(itemLabel);
        centerPanel.add(itemField);
        centerPanel.add(amountLabel);
        centerPanel.add(amountField);

        JButton addIncomeButton = new JButton("Add Income");
        addIncomeButton.setFocusable(false);
        addIncomeButton.setBackground(Color.green);
        addIncomeButton.setForeground(Color.white);
        addIncomeButton.setFont(new Font("Arial", Font.BOLD, 15));
        centerPanel.add(new JLabel());

        ImageIcon originAdd = new ImageIcon("add.png");
        Image editAdd = originAdd.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon add = new ImageIcon(editAdd);
        JLabel addlabel = new JLabel(add);
        addlabel.setHorizontalTextPosition(JLabel.RIGHT);
        addIncomeButton.add(addlabel);
        centerPanel.add(addIncomeButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setPreferredSize(new Dimension(600,35));
        backButton.setFocusable(false);
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.white);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));

        ImageIcon originhome = new ImageIcon("home.png");
        Image edithome = originhome.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 缩放图像
        ImageIcon home = new ImageIcon(edithome);
        JLabel homelabel = new JLabel(home);
        homelabel.setHorizontalTextPosition(JLabel.RIGHT);
        backButton.add(homelabel);

        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String description = itemField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    userManager.addIncome(description, amount);
                    JOptionPane.showMessageDialog(IncomePage.this, "Income added successfully!");
                    itemField.setText("");
                    amountField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(IncomePage.this, "Please enter a valid number for amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage(userManager);
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
