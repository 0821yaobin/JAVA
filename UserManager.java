import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {
    private HashMap<String, String> users = new HashMap<>();
    private List<IncomeEntry> incomes = new ArrayList<>();
    private List<ExpenseEntry> expenses = new ArrayList<>();
    private static final String USERS_FILE_PATH = "users.dat";

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
        if (users.get(username).equals(password)) {
            loadUserData(username);
            return true;
        }
        return false;
    }

    public void addIncome(String description, double amount) {
        IncomeEntry income = new IncomeEntry(description, amount);
        incomes.add(income);
        saveUserData();
    }

    public void addExpenditure(String description, double amount) {
        ExpenseEntry expense = new ExpenseEntry(description, amount);
        expenses.add(expense);
        saveUserData();
    }

    public List<IncomeEntry> getIncomes() {
        return incomes;
    }

    public List<ExpenseEntry> getExpenses() {
        return expenses;
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE_PATH))) {
            users = (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // 文件不存在，忽略
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getUserDataFilePath(String username) {
        return username + "_data.dat";
    }

    private void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUserDataFilePath(LoggedInUser.getUsername())))) {
            oos.writeObject(incomes);
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUserData(String username) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getUserDataFilePath(username)))) {
            incomes = (List<IncomeEntry>) ois.readObject();
            expenses = (List<ExpenseEntry>) ois.readObject();
        } catch (FileNotFoundException e) {
            incomes = new ArrayList<>();
            expenses = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class IncomeEntry implements Serializable {
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

    public static class ExpenseEntry implements Serializable {
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

class LoggedInUser {
    private static String username;

    public static void setUsername(String username) {
        LoggedInUser.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
