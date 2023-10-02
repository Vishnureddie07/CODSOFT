import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAtm extends JFrame {
    private BankAccount bankAccount;
    private JTextField balanceTextField;
    public MyAtm() {
        setTitle("My ATM Machine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        JLabel balanceLabel = new JLabel("Balance:");
        balanceTextField = new JTextField();
        balanceTextField.setEditable(false);
        JLabel actionLabel = new JLabel("Select Your Action:");

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        panel.add(balanceLabel);
        panel.add(balanceTextField);
        panel.add(actionLabel);
        panel.add(new JLabel("")); 
        panel.add(checkBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        add(panel);
        bankAccount = new BankAccount(0.0); 
        updateBalanceTextField();

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositFunds();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawFunds();
            }
        });
    }

    private void updateBalanceTextField() {
        double balance = bankAccount.getBalance();
        balanceTextField.setText("RS." + balance);
    }

    private void checkBalance() {
        double balance = bankAccount.getBalance();
        JOptionPane.showMessageDialog(this, "Your balance is: RS." + balance, "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void depositFunds() {
        String input = JOptionPane.showInputDialog(this, "Enter the amount to deposit:", "Deposit Funds", JOptionPane.PLAIN_MESSAGE);

        try {
            double depositAmount = Double.parseDouble(input);
            if (depositAmount > 0) {
                bankAccount.deposit(depositAmount); 
                updateBalanceTextField();
                JOptionPane.showMessageDialog(this, "Deposit successful. Your new balance is: RS." + bankAccount.getBalance(), "Deposit", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid deposit amount. Please enter a positive value.", "Deposit", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Deposit", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdrawFunds() {
        String input = JOptionPane.showInputDialog(this, "Enter the amount to withdraw:", "Withdraw Funds", JOptionPane.PLAIN_MESSAGE);

        try {
            double withdrawalAmount = Double.parseDouble(input);
            if (withdrawalAmount > 0) {
                if (withdrawalAmount <= bankAccount.getBalance()) {
                    boolean withdrawalSuccess = bankAccount.withdraw(withdrawalAmount); 
                    if (withdrawalSuccess) {
                        updateBalanceTextField();
                        JOptionPane.showMessageDialog(this, "Withdrawal successful. Your new balance is: RS." + bankAccount.getBalance(), "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Withdrawal failed. Please try again.", "Withdraw", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds. You cannot withdraw more than your balance.", "Withdraw", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid withdrawal amount. Please enter a positive value.", "Withdraw", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Withdraw", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyAtm atm = new MyAtm();
                atm.setVisible(true);
            }
        });
    }
}

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
