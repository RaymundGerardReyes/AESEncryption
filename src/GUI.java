import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import path.to.FileHandler;
import path.to.EncryptionUtil;
import path.to.Autocomplete;

public class GUI implements AccountListDisplay {

    private JFrame frame;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField prefixField;
    private JTextArea displayArea;

    private TrieNode root;
    private Set<String> accounts;
    private static final String SECRET_KEY = "ThisIsASecretKey";
    private Autocomplete autocomplete;

    public GUI() {
        root = new TrieNode();
        accounts = new HashSet<>();
        autocomplete = new Autocomplete(root);
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Social Media Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(); // Use JPasswordField instead of JTextField for password input
        passwordField.setEchoChar('*'); // Set the echo character to '*'
        inputPanel.add(passwordField);
    
        panel.add(inputPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
        panel.add(buttonPanel);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> addAccount(usernameField.getText(), emailField.getText(), passwordField.getText()));
        buttonPanel.add(createAccountButton);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(e -> encryptFileDialog());
        buttonPanel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(e -> decryptFileDialog());
        buttonPanel.add(decryptButton);

        JButton searchAccountButton = new JButton("Search Account");
        searchAccountButton.addActionListener(e -> searchAccount());
        buttonPanel.add(searchAccountButton);

        JButton saveAccountButton = new JButton("Save Account");
        saveAccountButton.addActionListener(e -> saveAccountToFile());
        buttonPanel.add(saveAccountButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> deleteAccountDialog());
        buttonPanel.add(deleteAccountButton);

        JButton viewAccountsButton = new JButton("View Accounts");
        viewAccountsButton.addActionListener(e -> viewAccounts());
        buttonPanel.add(viewAccountsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);
        frame.setVisible(true);
    }

    private void encryptFileDialog() {
        String inputFileName = JOptionPane.showInputDialog(frame, "Enter input file name:");
        String outputFileName = JOptionPane.showInputDialog(frame, "Enter output file name:");
        if (inputFileName != null && outputFileName != null) {
            FileHandler.encryptFileContents(inputFileName, outputFileName, SECRET_KEY);
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptFileDialog() {
        String inputFileName = JOptionPane.showInputDialog(frame, "Enter input file name:");
        String outputFileName = JOptionPane.showInputDialog(frame, "Enter output file name:");
        if (inputFileName != null && outputFileName != null) {
            FileHandler.decryptFileContents(inputFileName, outputFileName, SECRET_KEY);
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAccountToFile() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name to save accounts:");
        if (fileName != null) {
            FileHandler.saveToFile(root, fileName, SECRET_KEY);
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAccountDialog() {
        String usernameToDelete = JOptionPane.showInputDialog(frame, "Enter username of the account to delete:");
        if (usernameToDelete != null) {
            deleteAccount(usernameToDelete);
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAccount(String username) {
        String encryptedUsername = EncryptionUtil.encrypt(username, SECRET_KEY);
        TrieNode current = root;
        TrieNode parent = null;
        char parentChar = 0;
        for (char ch : encryptedUsername.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                JOptionPane.showMessageDialog(frame, "Account not found.");
                return;
            }
            parent = current;
            parentChar = ch;
            current = current.children.get(ch);
        }
        if (current.isEndOfWord) {
            current.isEndOfWord = false;
            if (parent != null) {
                parent.children.remove(parentChar);
            }
            JOptionPane.showMessageDialog(frame, "Account deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Account not found.");
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchAccount() {
        String prefix = JOptionPane.showInputDialog(frame, "Enter username prefix to search:");
        displayArea.setText("");
        if (prefix != null && !prefix.isEmpty()) {
            autocomplete.autocomplete(prefix);
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid prefix.");
        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewAccounts() {
        StringBuilder accountList = new StringBuilder();
        for (String account : accounts) {
            accountList.append(account).append("\n");
        }
        displayList("List of Accounts", accounts.toArray(new String[0]));
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAccount(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.");
            return;
        }
        String encryptedUsername = EncryptionUtil.encrypt(username, SECRET_KEY);
        String encryptedEmail = EncryptionUtil.encrypt(email, SECRET_KEY);
        String encryptedPassword = EncryptionUtil.encrypt(password, SECRET_KEY);

        TrieNode current = root;
        for (char ch : encryptedUsername.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.email = encryptedEmail;
        current.encryptedPassword = encryptedPassword;
        accounts.add(username);

        JOptionPane.showMessageDialog(frame, "Account added successfully.");
   
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayList(String header, String[] accounts) {
        StringBuilder listBuilder = new StringBuilder(header + "\n");
        for (String account : accounts) {
            listBuilder.append(account).append("\n");
        }
        displayArea.setText(listBuilder.toString());
   
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Dark Nimbus theme
            // or
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Dark Metal theme
            UIManager.put("nimbusBase", new Color(51, 51, 51)); // Adjust color values for Matte Black
            UIManager.put("nimbusBlueGrey", new Color(169, 169, 169)); // Adjust color values for Matte Black
            UIManager.put("control", new Color(128, 128, 128)); // Adjust color values for Matte Black
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}