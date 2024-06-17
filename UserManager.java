import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {
    private HashMap<String, String> users = new HashMap<>();
    private List<IncomeEntry> incomes = new ArrayList<>();
    private List<ExpenseEntry> expenses = new ArrayList<>();
    private static final String FILE_PATH = "users.dat";

    public UserManager() {
        loadUsers();
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false; // 用户名已存在
        }
        users.put(username, password);
        saveUsers();
        return true;
    }

    public boolean login(String username, String password) {
        if (!users.containsKey(username)) {
            return false; // 用户名不存在
        }
        return users.get(username).equals(password);
    }

    public void addIncome(String description, double amount) {
        IncomeEntry income = new IncomeEntry(description, amount);
        incomes.add(income);
        // Optionally, you can perform additional logic here, such as updating databases or files.
    }

    public void addExpenditure(String description, double amount) {
        ExpenseEntry expense = new ExpenseEntry(description, amount);
        expenses.add(expense);
        // Optionally, you can perform additional logic here, such as updating databases or files.
    }

    public List<IncomeEntry> getIncomes() {
        return incomes;
    }

    public List<ExpenseEntry> getExpenses() {
        return expenses;
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // 文件不存在，忽略
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Nested classes for IncomeEntry and ExpenseEntry
    public static class IncomeEntry {
        private String description;
        private double amount;

        public IncomeEntry(String description, double amount) {
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

    public static class ExpenseEntry {
        private String description;
        private double amount;

        public ExpenseEntry(String description, double amount) {
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
}
