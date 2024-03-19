import java.io.*;
import java.text.DecimalFormat;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import javax.swing.filechooser.FileSystemView;

public class Storage extends Info {
    public static final File[] hardDrives = File.listRoots();
    private static final OperatingSystemMXBean osMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); // OperatingSystemMXBean interface: https://docs.oracle.com/en/java/javase/17/docs/api/jdk.management/com/sun/management/OperatingSystemMXBean.html
    
    public static final String RAM_capacity = new DecimalFormat("#.##").format(osMXBean.getTotalMemorySize()/1024.0/1024.0/1024.0);
    public static final String RAM_available = new DecimalFormat("#.##").format(osMXBean.getFreeMemorySize()/1024.0/1024.0/1024.0);
    public static final String RAM_used = new DecimalFormat("#.##").format((osMXBean.getTotalMemorySize() - osMXBean.getFreeMemorySize())/1024.0/1024.0/1024.0);
    
    public static void getHardDriveInfo(File disk) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        
        String diskName = disk.getPath();
        String diskDescription = fsv.getSystemTypeDescription(disk);
        long totalSpace = disk.getTotalSpace();
        long freeSpace = disk.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        
        System.out.println(
                "Disk Name: " + diskName + "\n" + 
                "Disk Description: " + diskDescription + "\n" + 
                "Total Space: " + new DecimalFormat("#.##").format(totalSpace/1024.0/1024.0/1024.0) + " GB\n" + // Format to GB and round to 2 decimal places
                "Used Space: " + new DecimalFormat("#.##").format(usedSpace/1024.0/1024.0/1024.0) + " GB\n" +
                "Free Space: " + new DecimalFormat("#.##").format(freeSpace/1024.0/1024.0/1024.0) + " GB\n"      
        );
    }

    public void print() {
        System.out.println("\n>> " + App.colors.get("orange") + "Storage Information" + App.colors.get("reset") + "\n");

        System.out.println(App.colors.get("purple") + "RAM: " + App.colors.get("reset") + Storage.RAM_capacity + " GB " + "(Available: " + Storage.RAM_available + " GB | " + "Used: " + Storage.RAM_used + " GB)" +  "\n");

        int hardDiskCount = 1;

        for (File disk : hardDrives) {
            System.out.println(App.colors.get("grey") + "--[ Disk #" + hardDiskCount + " ]--" + App.colors.get("reset"));
            getHardDriveInfo(disk);
            hardDiskCount++;
        }
    }
}