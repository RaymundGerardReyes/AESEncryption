public class Search {
       // Search method
       String SECRET_KEY = "Gwapo ko"; // Define the SECRET_KEY variable
       TrieNode root = new TrieNode(); // Define the root variable
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
}
