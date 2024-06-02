import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    String encryptedPassword;
    String email;

    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
        encryptedPassword = "";
        email = "";
    }
}
