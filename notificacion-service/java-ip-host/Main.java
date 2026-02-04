import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLocalHost();

            String hostName = local.getHostName();
            if (hostName == null || hostName.isBlank()) {
                hostName = System.getenv("COMPUTERNAME"); // Windows
            }

            String primaryIp = local.getHostAddress();
            if (primaryIp == null || primaryIp.equals("127.0.0.1")) {
                // Buscar una IPv4 activa no loopback si la "local" devuelve 127.0.0.1
                primaryIp = findFirstActiveIPv4();
            }

            System.out.println("Host (nombre del equipo): " + (hostName != null ? hostName : "desconocido"));
            System.out.println("IP principal: " + (primaryIp != null ? primaryIp : "no encontrada"));

            List<String> ipv4s = listActiveIPv4s();
            if (!ipv4s.isEmpty()) {
                System.out.println("IPs IPv4 activas:");
                for (String ip : ipv4s) {
                    System.out.println(" - " + ip);
                }
            } else {
                System.out.println("No se encontraron IPs IPv4 activas.");
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo información de red: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String findFirstActiveIPv4() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addrs = ni.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static List<String> listActiveIPv4s() {
        List<String> result = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addrs = ni.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        result.add(addr.getHostAddress() + " (" + ni.getName() + ")");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error listando IPs IPv4: " + e.getMessage());
        }
        return result;
    }
}
