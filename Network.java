import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Enumeration;
import org.json.simple.*;

public class Network extends Info {
    public boolean isOnline = true;
    public String public_ip = "Unknown";
    public String local_ip = "Unknown";
    public String mac_address = "Unknown";
    public String subnet_mask = "Unknown";
    public String isp = "N/A";
    public String location = "N/A";

    public Network() {
        SetupNetworkData();
    }

    public static Network getNetworkData() {
        Network network = new Network();

        return network;
    }

    private void SetupNetworkData() {
        try {
            // Get all network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();

                // Skip loopback and inactive interfaces
                if (ni.isLoopback() || !ni.isUp()) {
                    continue;
                }

                // Get all IP addresses associated with the network interface
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    // Skip loopback, link-local, and multicast addresses
                    if (address.isLoopbackAddress() || address.isLinkLocalAddress() || address.isMulticastAddress()) {
                        continue;
                    }

                    // Check if it's an IPv4 address (because it might get IPv6 address as well)
                    if (address instanceof Inet4Address) {
                        // Get the local IP address
                        local_ip = address.getHostAddress();

                        // Get the public IP address via Amazon AWS
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://checkip.amazonaws.com/"))
                                .build();

                        try {
                            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                            public_ip = response.body().trim();
                        }
                        catch (ConnectException e) {
                            isOnline = false;
                            public_ip = "N/A";
                        } 
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Get IP's Geolocation information via ipapi.co
                        if (isOnline) {
                            String urlString = "https://ipapi.co/" + public_ip + "/json/";
                            HttpClient client2 = HttpClient.newHttpClient();
                            HttpRequest request2 = HttpRequest.newBuilder()
                                .uri(URI.create(urlString))
                                .build();

                            try {
                                HttpResponse<String> response = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                                JSONObject responseJSON = (JSONObject) JSONValue.parse(response.body());

                                isp = (String) responseJSON.get("org");
                                location = (String) responseJSON.get("region") + ", " + (String) responseJSON.get("country_name");
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Get the hardware (MAC) address
                        byte[] hardwareAddress = ni.getHardwareAddress();

                        // Convert the hardware address to hexadecimal format
                        String[] hexadecimal = new String[hardwareAddress.length];
                        for (int i = 0; i < hardwareAddress.length; i++) {
                            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
                        }

                        // Format the MAC address
                        mac_address = String.join("-", hexadecimal);

                        // Get the subnet mask
                        subnet_mask = getSubnetMask(ni);

                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helpful Source: https://stackoverflow.com/a/1221517
    private String getSubnetMask(NetworkInterface networkInterface) throws Exception {
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

        while (addresses.hasMoreElements()) {
            InetAddress address = addresses.nextElement();
            if (address instanceof Inet4Address) {
                NetworkInterface ni = NetworkInterface.getByInetAddress(address);

                short prefixLength = ni.getInterfaceAddresses().get(0).getNetworkPrefixLength();
                int subnetMask = 0xffffffff << (32 - prefixLength);

                byte[] subnetMaskBytes = new byte[]{
                        (byte) (subnetMask >>> 24),
                        (byte) (subnetMask >> 16 & 0xff),
                        (byte) (subnetMask >> 8 & 0xff),
                        (byte) (subnetMask & 0xff)
                };
                return InetAddress.getByAddress(subnetMaskBytes).getHostAddress();
            }
        }
        return "Unknown";
    }

    public void print() {
        System.out.println("\n>> " + App.colors.get("yellow") + "Network Information" + App.colors.get("reset") + "\n");

        Network network = getNetworkData();        

        System.out.println(
            "Status: " + (network.isOnline ? App.colors.get("green") + "Online" + App.colors.get("reset") : App.colors.get("red") + "Offline" + App.colors.get("reset")) 
            + "\nPublic IP: " + network.public_ip
            + "\nISP: " + network.isp
            + "\nLocation: " + network.location
            + "\nLocal IP: " + network.local_ip
            + "\nMAC Address: " + network.mac_address
            + "\nSubnet Mask: " + network.subnet_mask
        );
    }
}
