import java.io.*;
import java.util.*;

public class FileHandler {

    public static void encryptFileContents(String inputFileName, String outputFileName, String secretKey) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String encryptedUsername = EncryptionUtil.encrypt(fields[0].trim(), secretKey);
                    String encryptedEmail = EncryptionUtil.encrypt(fields[1].trim(), secretKey);
                    String encryptedPassword = EncryptionUtil.encrypt(fields[2].trim(), secretKey);
                    writer.println(encryptedUsername + "," + encryptedEmail + "," + encryptedPassword);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            System.out.println("File contents encrypted successfully.");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Encryption error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void decryptFileContents(String inputFileName, String outputFileName, String secretKey) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String decryptedUsername = EncryptionUtil.decrypt(fields[0].trim(), secretKey);
                    String decryptedEmail = EncryptionUtil.decrypt(fields[1].trim(), secretKey);
                    String decryptedPassword = EncryptionUtil.decrypt(fields[2].trim(), secretKey);
                    writer.println(decryptedUsername + "," + decryptedEmail + "," + decryptedPassword);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            System.out.println("File contents decrypted successfully.");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Decryption error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveToFile(TrieNode root, String fileName, String secretKey) {
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }
            saveHelper(root, "", writer, secretKey);
            System.out.println("Accounts saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving accounts to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveHelper(TrieNode node, String prefix, PrintWriter writer, String secretKey) {
        if (node.isEndOfWord) {
            writer.println("Username: " + EncryptionUtil.decrypt(prefix, secretKey) + ", Email: " + EncryptionUtil.decrypt(node.email, secretKey) + ", Password: " + EncryptionUtil.decrypt(node.encryptedPassword, secretKey));
        }
        for (char ch : node.children.keySet()) {
            saveHelper(node.children.get(ch), prefix + ch, writer, secretKey);
        }
    }
}
