public class OS extends Info {
    public static final String name = System.getProperty("os.name");
    public static final String version = System.getProperty("os.version");
    public static final String architecture = System.getProperty("os.arch");

    private String manufacturer = "Unknown";

    public OS() {
        manufacturer = this.getManufacturerName();
    }

    public static String getManufacturer() {
        return new OS().manufacturer;
    }

    private String getManufacturerName() {
        String os_name = OS.name.toLowerCase();

        if (os_name.contains("windows")) {
            return "Microsoft Corporation";
        } else if (os_name.contains("mac")) {
            return "Apple Inc.";
        } else if (os_name.contains("linux")) {
            return "GNU/Linux";
        } else {
            return "Unknown";
        }
    } 

    public void print() {
        System.out.println(">> " + App.colors.get("blue") + "Operating System Information" + App.colors.get("reset") + "\n");

        System.out.println(
            "Manufacturer: " + OS.getManufacturer()
            + "\nName: " + OS.name
            + "\nVersion: " + OS.version
            + "\nArchitecture: " + OS.architecture
        );
    }
}
