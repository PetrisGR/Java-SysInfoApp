import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import com.sun.management.OperatingSystemMXBean;

public class RAM {
    public String capacity;
    public String available;
    public String used;

    public RAM() {
        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); // OperatingSystemMXBean interface: https://docs.oracle.com/en/java/javase/17/docs/api/jdk.management/com/sun/management/OperatingSystemMXBean.html

        capacity = new DecimalFormat("#.##").format(osMXBean.getTotalMemorySize()/1024.0/1024.0/1024.0);
        available = new DecimalFormat("#.##").format(osMXBean.getFreeMemorySize()/1024.0/1024.0/1024.0);
        used = new DecimalFormat("#.##").format((osMXBean.getTotalMemorySize() - osMXBean.getFreeMemorySize())/1024.0/1024.0/1024.0);
    }
}
