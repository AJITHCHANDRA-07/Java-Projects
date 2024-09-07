import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

public class ATMInterface extends JFrame {
    private BankAccount account;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JTextArea messageArea;

    public ATMInterface() {
        account = new BankAccount(1000); // Initial balance

        setTitle("ATM Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel promptLabel = new JLabel("Enter amount:");
        promptLabel.setBounds(50, 50, 100, 20);
        add(promptLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 50, 100, 20);
        add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(50, 100, 100, 20);
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(160, 100, 100, 20);
        add(withdrawButton);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(270, 100, 120, 20);
        add(balanceButton);

        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        balanceLabel.setBounds(50, 150, 200, 20);
        add(balanceLabel);

        messageArea = new JTextArea();
        messageArea.setBounds(50, 180, 300, 60);
        messageArea.setEditable(false);
        add(messageArea);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBalance();
            }
        });
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.deposit(amount);
            messageArea.setText("Deposited: $" + amount);
            updateBalance();
        } catch (NumberFormatException ex) {
            messageArea.setText("Please enter a valid amount.");
        }
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                messageArea.setText("Withdrew: $" + amount);
            } else {
                messageArea.setText("Insufficient balance or invalid amount.");
            }
            updateBalance();
        } catch (NumberFormatException ex) {
            messageArea.setText("Please enter a valid amount.");
        }
    }

    private void handleCheckBalance() {
        updateBalance();
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + account.getBalance());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMInterface atm = new ATMInterface();
            atm.setVisible(true);
        });
    }
}
