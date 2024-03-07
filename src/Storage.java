import java.io.*;
import java.text.DecimalFormat;

import javax.swing.filechooser.FileSystemView;

public class Storage {
    public static final File[] hardDrives = File.listRoots();
    
    public static void getHardDriveInfo(File disk) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        
        String diskName = disk.getPath();
        String diskDescription = fsv.getSystemTypeDescription(disk);
        long totalSpace = disk.getTotalSpace();
        long freeSpace = disk.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        
        System.out.println(
                "Disk Name: " + diskName + "\n" + 
                "Disk Description " + diskDescription + "\n" + 
                "Total Space: " + new DecimalFormat("#.##").format(totalSpace/1024.0/1024.0/1024.0) + " GB\n" + // Format to GB and round to 2 decimal places
                "Free Space: " + new DecimalFormat("#.##").format(freeSpace/1024.0/1024.0/1024.0) + " GB\n" + 
                "Used Space: " + new DecimalFormat("#.##").format(usedSpace/1024.0/1024.0/1024.0) + " GB\n"
        );
    }
}