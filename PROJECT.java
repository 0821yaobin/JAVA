import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PROJECT {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinanceManager frame = new FinanceManager();
            frame.setVisible(true);
        });
    }

    public static class FinanceEntry implements Serializable {
        private String description;
        private double amount;

        public FinanceEntry(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }
    }

    public static class IncomeEntry extends FinanceEntry {
        public IncomeEntry(String description, double amount) {
            super(description, amount);
        }
    }

    public static class ExpenseEntry extends FinanceEntry {
        public ExpenseEntry(String description, double amount) {
            super(description, amount);
        }
    }

    public static class FinanceManager extends JFrame implements ActionListener {
        private ArrayList<IncomeEntry> incomes;
        private ArrayList<ExpenseEntry> expenses;

        // GUI Components
        private JTextField incomeDescriptionField, incomeAmountField;
        private JTextField expenseDescriptionField, expenseAmountField;
        private JButton addIncomeButton, addExpenseButton, viewSummaryButton, printSummaryButton, clearSummaryButton;
        private JTextArea summaryArea;
        private JMenuBar menuBar;
        private JMenu fileMenu;
        private JMenuItem saveItem, loadItem, exitItem;

        public FinanceManager() {
            // Initialize lists
            incomes = new ArrayList<>();
            expenses = new ArrayList<>();

            // Set up the frame
            setTitle("Personal Finance Management");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Set up menu
            menuBar = new JMenuBar();
            fileMenu = new JMenu("File");
            saveItem = new JMenuItem("Save");
            loadItem = new JMenuItem("Load");
            exitItem = new JMenuItem("Exit");
            fileMenu.add(saveItem);
            fileMenu.add(loadItem);
            fileMenu.add(exitItem);
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);

            // Add action listeners
            saveItem.addActionListener(this);
            loadItem.addActionListener(this);
            exitItem.addActionListener(this);

            // Set up input panel for income
            JPanel incomePanel = new JPanel(new GridLayout(3, 2));
            incomePanel.add(new JLabel("Income Description:"));
            incomeDescriptionField = new JTextField();
            incomePanel.add(incomeDescriptionField);
            incomePanel.add(new JLabel("Income Amount:"));
            incomeAmountField = new JTextField();
            incomePanel.add(incomeAmountField);
            addIncomeButton = new JButton("Add Income");
            incomePanel.add(addIncomeButton);

            // Set up input panel for expenses
            JPanel expensePanel = new JPanel(new GridLayout(3, 2));
            expensePanel.add(new JLabel("Expense Description:"));
            expenseDescriptionField = new JTextField();
            expensePanel.add(expenseDescriptionField);
            expensePanel.add(new JLabel("Expense Amount:"));
            expenseAmountField = new JTextField();
            expensePanel.add(expenseAmountField);
            addExpenseButton = new JButton("Add Expense");
            expensePanel.add(addExpenseButton);

            // Set up summary panel
            JPanel summaryPanel = new JPanel(new BorderLayout());
            summaryArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(summaryArea);
            summaryPanel.add(scrollPane, BorderLayout.CENTER);
            viewSummaryButton = new JButton("View Summary");
            summaryPanel.add(viewSummaryButton, BorderLayout.NORTH);
            printSummaryButton = new JButton("Print Summary to File");
            summaryPanel.add(printSummaryButton, BorderLayout.SOUTH);
            clearSummaryButton = new JButton("Clear Summary");
            summaryPanel.add(clearSummaryButton, BorderLayout.WEST);

            // Add panels to frame
            add(incomePanel, BorderLayout.NORTH);
            add(expensePanel, BorderLayout.CENTER);
            add(summaryPanel, BorderLayout.SOUTH);

            // Add action listeners
            addIncomeButton.addActionListener(this);
            addExpenseButton.addActionListener(this);
            viewSummaryButton.addActionListener(this);
            printSummaryButton.addActionListener(this);
            clearSummaryButton.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == addIncomeButton) {
                    String description = incomeDescriptionField.getText();
                    double amount = Double.parseDouble(incomeAmountField.getText());
                    incomes.add(new IncomeEntry(description, amount));
                    JOptionPane.showMessageDialog(this, "Income added successfully!");
                    clearIncomeFields();
                } else if (e.getSource() == addExpenseButton) {
                    String description = expenseDescriptionField.getText();
                    double amount = Double.parseDouble(expenseAmountField.getText());
                    expenses.add(new ExpenseEntry(description, amount));
                    JOptionPane.showMessageDialog(this, "Expense added successfully!");
                    clearExpenseFields();
                } else if (e.getSource() == viewSummaryButton) {
                    displaySummary();
                } else if (e.getSource() == printSummaryButton) {
                    printSummaryToFile();
                } else if (e.getSource() == clearSummaryButton) {
                    clearSummary();
                } else if (e.getSource() == saveItem) {
                    saveData();
                } else if (e.getSource() == loadItem) {
                    loadData();
                } else if (e.getSource() == exitItem) {
                    System.exit(0);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while saving/loading data.", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void clearIncomeFields() {
            incomeDescriptionField.setText("");
            incomeAmountField.setText("");
        }

        private void clearExpenseFields() {
            expenseDescriptionField.setText("");
            expenseAmountField.setText("");
        }

        private void displaySummary() {
            StringBuilder summary = new StringBuilder("Income Summary:\n");
            for (IncomeEntry income : incomes) {
                summary.append("Description: ").append(income.getDescription())
                       .append(", Amount: ").append(income.getAmount())
                       .append("\n");
            }
            summary.append("\nExpense Summary:\n");
            for (ExpenseEntry expense : expenses) {
                summary.append("Description: ").append(expense.getDescription())
                       .append(", Amount: ").append(expense.getAmount())
                       .append("\n");
            }
            summary.append("\nBalance: ").append(calculateBalance()).append("\n");
            summaryArea.setText(summary.toString());
        }

        private double calculateBalance() {
            double totalIncome = incomes.stream().mapToDouble(IncomeEntry::getAmount).sum();
            double totalExpense = expenses.stream().mapToDouble(ExpenseEntry::getAmount).sum();
            return totalIncome - totalExpense;
        }

        private void saveData() throws IOException {
            try (ObjectOutputStream outIncome = new ObjectOutputStream(new FileOutputStream("incomes.bin"));
                 ObjectOutputStream outExpense = new ObjectOutputStream(new FileOutputStream("expenses.bin"))) {
                outIncome.writeObject(incomes);
                outExpense.writeObject(expenses);
                JOptionPane.showMessageDialog(this, "Data saved successfully!");
            }
        }

        private void loadData() throws IOException {
            try (ObjectInputStream inIncome = new ObjectInputStream(new FileInputStream("incomes.bin"));
                 ObjectInputStream inExpense = new ObjectInputStream(new FileInputStream("expenses.bin"))) {
                incomes = (ArrayList<IncomeEntry>) inIncome.readObject();
                expenses = (ArrayList<ExpenseEntry>) inExpense.readObject();
                JOptionPane.showMessageDialog(this, "Data loaded successfully!");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Class not found while loading data.", "Class Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void printSummaryToFile() throws IOException {
            displaySummary(); // Refresh the summary
            try (PrintWriter writer = new PrintWriter(new FileWriter("summary.txt"))) {
                writer.print(summaryArea.getText());
                JOptionPane.showMessageDialog(this, "Summary printed to summary.txt!");
            }
        }

        private void clearSummary() {
            summaryArea.setText("");
        }
    }
}