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
            "Public IP: " + network.public_ip
            + "\nLocal IP: " + network.local_ip
            + "\nMAC Address: " + network.mac_address
            + "\nSubnet Mask: " + network.subnet_mask
        );

        System.out.println("\n>> " + colors.get("green") + "RAM Information" + colors.get("reset") + "\n");

        RAM ram = new RAM();

        System.out.println(
            "Capacity: " + ram.capacity + " GB"
            + "\nAvailable: " + ram.available + " GB"
            + "\nUsed: " + ram.used + " GB");

        System.out.println("\n>> " + colors.get("purple") + "Storage Information" + colors.get("reset") + "\n");

        int hardDiskCount = 1;

        for (File disk : Storage.hardDrives) {
            System.out.println(colors.get("grey") + "--[ Disk #" + hardDiskCount + " ]--" + colors.get("reset"));
            Storage.getHardDriveInfo(disk);
            hardDiskCount++;
        }
    }
}
