import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private TrieNode root;
    private static final String SECRET_KEY = "ThisIsASecretKey";

    // Constructor
    public Main() {
        root = new TrieNode();
    }
    
    // Getter for root
    public TrieNode getRoot() {
        return root;
    }

    // Insert method
    public void insert(String username, String email, String password) {
        String encryptedUsername = EncryptionUtil.encrypt(username, SECRET_KEY);
        TrieNode current = root;
        for (char ch : encryptedUsername.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.encryptedPassword = EncryptionUtil.encrypt(password, SECRET_KEY);
        current.email = EncryptionUtil.encrypt(email, SECRET_KEY);
    }

    // Search method
    public boolean search(String username) {
        String encryptedUsername = EncryptionUtil.encrypt(username, SECRET_KEY);
        TrieNode current = root;
        for (char ch : encryptedUsername.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return current.isEndOfWord;
    }

    // Delete method
    public boolean delete(String username) {
        String encryptedUsername = EncryptionUtil.encrypt(username, SECRET_KEY);
        TrieNode current = root;
        Deque<TrieNode> stack = new ArrayDeque<>();
        // Traverse down to the node containing the username
        for (char ch : encryptedUsername.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return false; // Account not found
            }
            stack.push(current);
            current = current.children.get(ch);
        }
        if (!current.isEndOfWord) {
            return false; // Account not found
        }
        // Mark the node as not end of word
        current.isEndOfWord = false;
        // Check if the node has no children and delete it along with its parent nodes if they have no other children
        while (!stack.isEmpty()) {
            TrieNode node = stack.pop();
            char ch = encryptedUsername.charAt(stack.size());
            if (node.children.get(ch).children.isEmpty() && !node.children.get(ch).isEndOfWord) {
                node.children.remove(ch);
            } else {
                break;
            }
        }
        // Update the file after deleting the account
        FileHandler.saveToFile(root, "PasswordLists.txt", SECRET_KEY);
        return true; // Account deleted successfully
    }



    // Main method
    public static void main(String[] args) {
        Main passwordManager = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(" ");
            System.out.println("----------------------- Social Media Manager -----------------------");
            System.out.println(" ");
            System.out.println(" |   1. Create an account     |   2. Encrypt the account    |");
            System.out.println(" |   3. Search an Account     |   4. Decrypt the account    |");
            System.out.println(" |   5. Save the account      |   6. Delete selection       |");
            System.out.println(" |   7. Exit                  |   8. View list of Accounts  |");
            System.out.println("                                                           ");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character

                switch (choice) {
                    case 1: // Create an account
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter email address: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        passwordManager.insert(username, email, password);
                        System.out.println("Account created successfully.");
                        break;
                    case 2: // Encrypt the account
                        FileHandler.encryptFileContents("PasswordLists.txt", "Encrypted_PasswordLists.txt", SECRET_KEY);
                        break;
                    case 3: // Search an Account
                        System.out.print("Enter username to search: ");
                        String usernameToSearch = scanner.nextLine();
                        if (passwordManager.search(usernameToSearch)) {
                            System.out.println("Account found.");
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 4: // Decrypt the account
                        FileHandler.decryptFileContents("Encrypted_PasswordLists.txt", "Decrypted_PasswordLists.txt", SECRET_KEY);
                        break;
                    case 5: // Save the account
                        FileHandler.saveToFile(passwordManager.getRoot(), "PasswordLists.txt", SECRET_KEY);
                        break;
                    case 6: // Delete an account
                        System.out.print("Enter username of the account to delete: ");
                        String usernameToDelete = scanner.nextLine();
                        if (passwordManager.delete(usernameToDelete)) {
                            System.out.println("Account deleted successfully.");
                        } else {
                            System.out.println("Account with username " + usernameToDelete + " not found.");
                        }
                        break;
                    case 7: // Exit
                        System.out.println("Exiting... Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;
                    case 8: // View List of Accounts
                        System.out.println(" ");
                        System.out.println("----------------------- View List of Accounts -----------------------");
                        System.out.println(" ");
                        try (BufferedReader reader = new BufferedReader(new FileReader("PasswordLists.txt"))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (FileNotFoundException e) {
                            System.err.println("File not found: " + e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.next(); // Consume invalid input to prevent infinite loop
            }
        }
    }
}
