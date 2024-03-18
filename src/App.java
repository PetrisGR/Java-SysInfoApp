import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(">> " + colors.get("blue") + "Operating System Information" + colors.get("reset") + "\n");

        System.out.println(
            "Manufacturer: " + OS.getManufacturer()
            + "\nName: " + OS.name
            + "\nVersion: " + OS.version
            + "\nArchitecture: " + OS.architecture
        );

        System.out.println("\n>> " + colors.get("yellow") + "Network Information" + colors.get("reset") + "\n");

        Network network = Network.getNetworkData();        

        System.out.println(
            "Status: " + (network.isOnline ? colors.get("green") + "Online" + colors.get("reset") : colors.get("red") + "Offline" + colors.get("reset")) 
            + "\nPublic IP: " + network.public_ip
            + "\nISP: " + network.isp
            + "\nLocation: " + network.location
            + "\nLocal IP: " + network.local_ip
            + "\nMAC Address: " + network.mac_address
            + "\nSubnet Mask: " + network.subnet_mask
        );

        System.out.println("\n>> " + colors.get("orange") + "Storage Information" + colors.get("reset") + "\n");

        System.out.println(colors.get("purple") + "RAM: " + colors.get("reset") + Storage.RAM_capacity + " GB " + "(Available: " + Storage.RAM_available + " GB | " + "Used: " + Storage.RAM_used + " GB)" +  "\n");

        int hardDiskCount = 1;

        for (File disk : Storage.hardDrives) {
            System.out.println(colors.get("grey") + "--[ Disk #" + hardDiskCount + " ]--" + colors.get("reset"));
            Storage.getHardDriveInfo(disk);
            hardDiskCount++;
        }
    }
}
