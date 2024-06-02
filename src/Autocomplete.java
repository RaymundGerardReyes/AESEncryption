import java.util.*;

public class Autocomplete {
    private TrieNode root;
    private static final String SECRET_KEY = "ThisIsASecretKey";

    public Autocomplete(TrieNode root) {
        this.root = root;
    }

    // Autocomplete method
    public void autocomplete(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                System.out.println("No accounts found with the given prefix.");
                return;
            }
            current = current.children.get(ch);
        }
        autocompleteHelper(current, prefix);
    }

    // Autocomplete helper method
    private void autocompleteHelper(TrieNode node, String prefix) {
        if (node == null || node.children.isEmpty()) {
            return;
        }
        if (node.isEndOfWord) {
            System.out.println("Username: " + EncryptionUtil.decrypt(prefix, SECRET_KEY) + ", Email: " + EncryptionUtil.decrypt(node.email, SECRET_KEY) + ", Password: " + EncryptionUtil.decrypt(node.encryptedPassword, SECRET_KEY));
        }
        for (char ch : node.children.keySet()) {
            autocompleteHelper(node.children.get(ch), prefix + ch);
        }
    }
}
