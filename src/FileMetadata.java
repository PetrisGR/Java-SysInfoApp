import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.Date;

public class FileMetadata extends Info {
    // Basic Information
    public String full_name;
    public String name;
    public String type;
    public String size;

    // Attributes
    public boolean isDirectory;
    public boolean isReadOnly;
    public boolean isHidden;

    // Timestamps
    public String created;
    public String lastOpened;
    public String lastModified;

    public FileMetadata() {}

    public FileMetadata(String path) throws IOException {
        File file = new File(path);

        if (file.exists()) {
            Path filePath = file.toPath();

            BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);

            this.full_name = file.getName();

            int lastDotIndex = this.full_name.lastIndexOf(".");

            this.name = lastDotIndex != -1 ? this.full_name.substring(0, lastDotIndex) : this.full_name;
            this.type = lastDotIndex != -1 ? this.full_name.substring(lastDotIndex + 1) : "None";
            this.size = new DecimalFormat("#.##").format(file.length()/1024.0) + " KB";

            this.isDirectory = file.isDirectory();
            this.isReadOnly = !file.canWrite();
            this.isHidden = file.isHidden();

            this.created = new Date(attr.creationTime().toMillis()).toString();
            this.lastOpened = new Date(attr.lastAccessTime().toMillis()).toString();
            this.lastModified = new Date(attr.lastModifiedTime().toMillis()).toString();

            this.print();
        }
        else {
            System.out.println(App.colors.get("red") + "[ERROR]: " + App.colors.get("reset") + "File not found (Invalid File Path).");
        }
    }

    public void print() {
        System.out.println("\n>> " + App.colors.get("red") + "File Metadata Results (" + App.colors.get("reset") + this.full_name + App.colors.get("red") + ")" + App.colors.get("reset") + "\n");

        System.out.println(App.colors.get("grey") + "Full Name: " + App.colors.get("reset") + this.full_name);
        System.out.println(App.colors.get("grey") + "Name: " + App.colors.get("reset") + this.name);
        System.out.println(App.colors.get("grey") + "Type: " + App.colors.get("reset") + this.type);
        System.out.println(App.colors.get("grey") + "Size: " + App.colors.get("reset") + this.size);
        System.out.println(App.colors.get("grey") + "Is Directory: " + App.colors.get("reset") + this.isDirectory);
        System.out.println(App.colors.get("grey") + "Is Read-Only: " + App.colors.get("reset") + this.isReadOnly);
        System.out.println(App.colors.get("grey") + "Is Hidden: " + App.colors.get("reset") + this.isHidden);
        System.out.println(App.colors.get("grey") + "Created: " + App.colors.get("reset") + this.created);
        System.out.println(App.colors.get("grey") + "Last Opened: " + App.colors.get("reset") + this.lastOpened);
        System.out.println(App.colors.get("grey") + "Last Modified: " + App.colors.get("reset") + this.lastModified);
    }
}
