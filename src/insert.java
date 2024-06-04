class insert {
    private TrieNode root;
    private static final String SECRET_KEY = "ThisIsASecretKey";
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
}