import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    private static int checkInputLimit(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(in.nextLine());
                if (choice < min || choice > max) {
                    throw new NumberFormatException();
                }
                return choice;
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input. Please re-input a number between " + min + " and " + max + ".");
            }
        }
    }

    private static int countWordInFile(String fileSource, String word) {
        int count = 0;
        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (FileReader fr = new FileReader(fileSource);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            // Sử dụng regex để đếm chính xác từ, không bị ảnh hưởng bởi dấu câu
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    count++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + fileSource);
        } catch (IOException ex) {
            System.err.println("Error reading file: " + fileSource);
        }
        return count;
    }

    private static void getFileNameContainsWordInDirectory(String source, String word) {
        File folder = new File(source);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("The directory does not exist or is empty.");
            return;
        }

        boolean found = false;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                int wordCount = countWordInFile(file.getAbsolutePath(), word);
                if (wordCount > 0) {
                    System.out.println("File name: " + file.getName() + " (contains " + wordCount + " occurrences of the word)");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No file contains the word \"" + word + "\" in the directory.");
        }
    }

    public static void wordProgram() {
        String path;
        String word;
        while (true) {
            System.out.println("1. Count Word In File");
            System.out.println("2. Find File By Word");
            System.out.println("3. Exit");
            int choice = checkInputLimit(1, 3);
            switch (choice) {
                case 1:
                    System.out.print("Enter File Path: ");
                    path = in.nextLine();
                    System.out.print("Enter Word: ");
                    word = in.nextLine();
                    int count = countWordInFile(path, word);
                    System.out.println("Count: " + count);
                    break;
                case 2:
                    System.out.print("Enter Directory Path: ");
                    path = in.nextLine();
                    System.out.print("Enter Word: ");
                    word = in.nextLine();
                    getFileNameContainsWordInDirectory(path, word);
                    break;
                case 3:
                    return;
            }
        }
    }

    public static void main(String[] args) {
        wordProgram();
    }
}
