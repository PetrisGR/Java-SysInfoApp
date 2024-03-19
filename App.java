import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static final Map<String, String> colors = new HashMap<String, String>() {{
        put("reset", "\u001B[0m");
        put("black", "\u001B[30m");
        put("red", "\u001B[31m");
        put("green", "\u001B[32m");
        put("yellow", "\u001B[33m");
        put("blue", "\u001B[34m");
        put("orange", "\u001B[38;5;208m");
        put("purple", "\u001B[35m");
        put("cyan", "\u001B[36m");
        put("white", "\u001B[37m");
        put("grey", "\u001B[90m");
    }};

    public static void main(String[] args) throws Exception {
        new OS().print(); // Print the operating system information
        new Network().print(); // Print the network information
        new Storage().print(); // Print the storage information

        Scanner scannerObj = new Scanner(System.in);
        boolean run = true;

        do {
            System.out.println("\n" + colors.get("yellow") + "(?) " + colors.get("reset") + "Do you want to check a file's metadata? ("+ colors.get("green") +"y" + colors.get("reset") + "/" + colors.get("red") + "n" + colors.get("reset") + ")");

            String response = scannerObj.nextLine();

            if (response.equals("y")) {
                System.out.println(colors.get("yellow") + "\n> " + colors.get("reset") + "Enter the file path:");
                String filePath = scannerObj.nextLine();

                new FileMetadata(filePath);
            } 
            else if (response.equals("n")){
                run = false;
            }
            else {
                System.out.println(colors.get("red") + "[ERROR]: " + colors.get("reset") + "Invalid input. Please try again.");
            }
        } while (run);

        System.out.println("\n" + colors.get("green") + "[EOF] " + colors.get("reset") + "Thank you for using the system information tool!" + colors.get("reset"));

        scannerObj.close();
    }
}
