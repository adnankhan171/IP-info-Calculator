import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IPCalculatorGUI {

	protected static JTextField ipAddressField;
    protected static JTextField subnetMaskField;
    protected static JTextField networkIdField;
    protected static JTextField binaryIpField;
    protected static JTextField binaryMaskField;
    protected static JTextField binaryNetworkIdField;
    protected static JTextField ipClassField;
    protected static JTextField addressTypeField;
    protected static JTextField goodIpForHostField;
    protected static JTextField reasonField;
    protected static JTextField numSubnetworksField;
    protected static JTextField numHostsField ;
    protected static JTextField rangeField ;
    protected static JTextArea networkIdArea;
    protected static JTextArea broadcastIdArea;
    protected static JScrollPane networkIdScroll;
    protected static JScrollPane broadcastIdScroll;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("IP Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main container with BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel for the four main information panels
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        // Create the four main panels
        JPanel ipInfoPanel = createIPInfoPanel();
        JPanel networkInfoPanel = createNetworkInfoPanel();
        JPanel binaryInfoPanel = createBinaryInfoPanel();
        JPanel subnetInfoPanel = createSubnetInfoPanel();
        
        // Add panels to main panel
        mainPanel.add(ipInfoPanel);
        mainPanel.add(networkInfoPanel);
        mainPanel.add(binaryInfoPanel);
        mainPanel.add(subnetInfoPanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton closeButton = new JButton("Close");
        JButton computeNowButton = new JButton("Compute Now");
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        buttonPanel.add(computeNowButton);
        
        

        // Add components to content pane
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        // Add ActionListener for close button
		closeButton.addActionListener(e -> frame.dispose());	

		// Add ActionListener for save button
		saveButton.addActionListener(e -> saveToFile(ipAddressField.getText(), subnetMaskField.getText(), networkIdField.getText()));
		
		// action Listener for compute now button
		computeNowButton.addActionListener(e -> ComputeEveryThing());
        frame.add(contentPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static JPanel createIPInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("IP Information"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        ipAddressField = new JTextField(15);
        subnetMaskField = new JTextField(15);
        networkIdField = new JTextField(15);
        networkIdField.setEditable(false);
        
        JButton resetButton = new JButton("Reset");
        JButton defaultMaskButton = new JButton("Default Mask");
        
        
        resetButton.addActionListener(e -> ipAddressField.setText(""));
        
        defaultMaskButton.addActionListener(e -> {
        	String ip = ipAddressField.getText().trim();
        	subnetMaskField.setText(getDefaultSubnetMask(ip));
    	});
        addComponent(panel, new JLabel("IP Address:"), gbc, 0, 0);
        addComponent(panel, ipAddressField, gbc, 1, 0);
        addComponent(panel, resetButton, gbc, 2, 0);
        
        addComponent(panel, new JLabel("Subnet Mask:"), gbc, 0, 1);
        addComponent(panel, subnetMaskField, gbc, 1, 1);
        addComponent(panel, defaultMaskButton, gbc, 2, 1);
        
        addComponent(panel, new JLabel("Network ID:"), gbc, 0, 2);
        addComponent(panel, networkIdField, gbc, 1, 2);
        
        
        return panel;
    }
    
    private static JPanel createNetworkInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Network Information"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        ipClassField = new JTextField(15);
        addressTypeField = new JTextField(15);
        goodIpForHostField = new JTextField(15);
        reasonField = new JTextField(15);
        
        // Make fields read-only
        ipClassField.setEditable(false);
        addressTypeField.setEditable(false);
        goodIpForHostField.setEditable(false);
        reasonField.setEditable(false);
        
        addComponent(panel, new JLabel("IP Address Class:"), gbc, 0, 0);
        addComponent(panel, ipClassField, gbc, 1, 0);
        addComponent(panel, new JLabel("Address Type:"), gbc, 0, 1);
        addComponent(panel, addressTypeField, gbc, 1, 1);
        addComponent(panel, new JLabel("Good IP for Host:"), gbc, 0, 2);
        addComponent(panel, goodIpForHostField, gbc, 1, 2);
        addComponent(panel, new JLabel("Reason:"), gbc, 0, 3);
        addComponent(panel, reasonField, gbc, 1, 3);
        
        return panel;
    }
    
    private static JPanel createBinaryInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Binary Information"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        binaryIpField = new JTextField(30);
        binaryMaskField = new JTextField(30);
        binaryNetworkIdField = new JTextField(30);
        
        // Make fields read-only
        binaryIpField.setEditable(false);
        binaryMaskField.setEditable(false);
        binaryNetworkIdField.setEditable(false);
        
        addComponent(panel, new JLabel("IP Address (Binary):"), gbc, 0, 0);
        addComponent(panel, binaryIpField, gbc, 1, 0);
        addComponent(panel, new JLabel("Mask (Binary):"), gbc, 0, 1);
        addComponent(panel, binaryMaskField, gbc, 1, 1);
        addComponent(panel, new JLabel("Network ID (Binary):"), gbc, 0, 2);
        addComponent(panel, binaryNetworkIdField, gbc, 1, 2);
        
        return panel;
    }
    
    private static JPanel createSubnetInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Subnetting Information"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        numSubnetworksField = new JTextField(15);
        numHostsField = new JTextField(15);
        rangeField = new JTextField(15);
        networkIdArea = new JTextArea(3, 20);
        broadcastIdArea = new JTextArea(3, 20);
        
        // Make text areas scrollable
        networkIdScroll = new JScrollPane(networkIdArea);
        broadcastIdScroll = new JScrollPane(broadcastIdArea);
        
        // Make fields read-only
        numSubnetworksField.setEditable(false);
        numHostsField.setEditable(false);
        rangeField.setEditable(false);
        networkIdArea.setEditable(false);
        broadcastIdArea.setEditable(false);
        
        addComponent(panel, new JLabel("# of Subnetworks:"), gbc, 0, 0);
        addComponent(panel, numSubnetworksField, gbc, 1, 0);
        addComponent(panel, new JLabel("# of Hosts:"), gbc, 0, 1);
        addComponent(panel, numHostsField, gbc, 1, 1);
        addComponent(panel, new JLabel("Range:"), gbc, 0, 2);
        addComponent(panel, rangeField, gbc, 1, 2);
        
        gbc.gridwidth = 2;
        addComponent(panel, new JLabel("Network ID's:"), gbc, 0, 3);
        gbc.gridwidth = 1;
        addComponent(panel, networkIdScroll, gbc, 0, 4, 2, 1);
        
        gbc.gridwidth = 2;
        addComponent(panel, new JLabel("Broadcast ID's:"), gbc, 0, 5);
        gbc.gridwidth = 1;
        addComponent(panel, broadcastIdScroll, gbc, 0, 6, 2, 1);
        
        return panel;
    }
    protected static void ComputeEveryThing(){
    	networkIdField.setText(calculateNetworkId(ipAddressField.getText(), subnetMaskField.getText())) ;
    	//System.out.println(ipAddressField.getText());
    	//System.out.println(subnetMaskField.getText());
    	//System.out.println(subnetMaskField.getText().getClass());
    	//System.out.println(networkIdField.getText());
    	if (ipAddressField.getText().isEmpty() || subnetMaskField.getText().isEmpty()) {
    	JOptionPane.showMessageDialog(null, "Please enter a valid IP Address and Subnet Mask.");
    	return;
		}

    	binaryIpField.setText(toBinary(ipAddressField.getText()));
    	binaryMaskField.setText(toBinary(subnetMaskField.getText()));
    	binaryNetworkIdField.setText(toBinary(networkIdField.getText()));
    	
    	ipClassField.setText(getIPClass(ipAddressField.getText()));
    	addressTypeField.setText(getAddressType(ipAddressField.getText()));
    	goodIpForHostField.setText(isGoodHostIP(ipAddressField.getText(),subnetMaskField.getText()));
    	reasonField.setText(getHostIPReason(ipAddressField.getText(),subnetMaskField.getText()));
    	
    	numSubnetworksField.setText(calculateSubnets(subnetMaskField.getText()));
    	numHostsField.setText(calculateHosts(subnetMaskField.getText()));
    	rangeField.setText(getHostRange(ipAddressField.getText(), subnetMaskField.getText()));
    	networkIdArea.setText(getNetworkID(ipAddressField.getText(), subnetMaskField.getText()));
    	broadcastIdArea.setText(getBroadcastID(ipAddressField.getText(), subnetMaskField.getText()));
    	
    	
    }
    public static void saveToFile(String ipAddress, String subnetMask, String networkId) {
        String fileName = "subnet_details.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("IP Address: " + ipAddress);
            writer.newLine();
            writer.write("Subnet Mask: " + subnetMask);
            writer.newLine();
            writer.write("Network ID: " + networkId);
            writer.newLine();
            writer.write("------------------------------");
            writer.newLine();
            System.out.println("Details saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    
    private static String calculateSubnets(String subnetMask) {
        int subnetBits = countSubnetBits(subnetMask);
        return String.valueOf((int) Math.pow(2, subnetBits));
    }

    // Function to calculate number of hosts in a subnet
    private static String calculateHosts(String subnetMask) {
        int hostBits = 32 - countSubnetBits(subnetMask);
        return String.valueOf((int) Math.pow(2, hostBits) - 2); // Subtract network and broadcast addresses
    }

    // Function to get the network ID
    private static String getNetworkID(String ipAddress, String subnetMask) {
        int[] ip = parseIP(ipAddress);
        int[] mask = parseIP(subnetMask);
        int[] network = new int[4];

        for (int i = 0; i < 4; i++) {
            network[i] = ip[i] & mask[i];
        }
        return formatIP(network);
    }

    // Function to get the broadcast ID
    private static String getBroadcastID(String ipAddress, String subnetMask) {
        int[] network = parseIP(getNetworkID(ipAddress, subnetMask));
        int[] mask = parseIP(subnetMask);
        int[] broadcast = new int[4];

        for (int i = 0; i < 4; i++) {
            broadcast[i] = network[i] | (~mask[i] & 255);
        }
        return formatIP(broadcast);
    }

    // Function to calculate usable host range
    private static String getHostRange(String ipAddress, String subnetMask) {
        String networkID = getNetworkID(ipAddress, subnetMask);
        String broadcastID = getBroadcastID(ipAddress, subnetMask);

        int[] firstHost = parseIP(networkID);
        int[] lastHost = parseIP(broadcastID);

        firstHost[3] += 1; // First usable IP
        lastHost[3] -= 1; // Last usable IP

        return formatIP(firstHost) + " - " + formatIP(lastHost);
    }

    // Helper function to count subnet bits
    private static int countSubnetBits(String subnetMask) {
        int count = 0;
        for (String octet : subnetMask.split("\\.")) {
            count += Integer.bitCount(Integer.parseInt(octet));
        }
        return count;
    }

    // Helper function to parse an IP address into an array
    private static int[] parseIP(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }

    // Helper function to format an IP address from an array
    private static String formatIP(int[] ip) {
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }
    private static String getHostIPReason(String ipAddress, String subnetMask) {
        try {
            String[] ipOctets = ipAddress.split("\\.");
            String[] maskOctets = subnetMask.split("\\.");

            int[] ip = new int[4];
            int[] mask = new int[4];

            for (int i = 0; i < 4; i++) {
                ip[i] = Integer.parseInt(ipOctets[i]);
                mask[i] = Integer.parseInt(maskOctets[i]);
            }

            // Compute Network and Broadcast Address
            int[] network = new int[4];
            int[] broadcast = new int[4];

            for (int i = 0; i < 4; i++) {
                network[i] = ip[i] & mask[i];
                broadcast[i] = network[i] | (~mask[i] & 255);
            }

            String networkAddress = String.format("%d.%d.%d.%d", network[0], network[1], network[2], network[3]);
            String broadcastAddress = String.format("%d.%d.%d.%d", broadcast[0], broadcast[1], broadcast[2], broadcast[3]);

            // Check if the IP is a Network or Broadcast Address
            if (ipAddress.equals(networkAddress)) {
                return "No - This is a Network Address.";
            }
            if (ipAddress.equals(broadcastAddress)) {
                return "No - This is a Broadcast Address.";
            }

            // Check for Reserved IP Ranges
            String ipType = getAddressType(ipAddress);
            switch (ipType) {
                case "Loopback Address":
                    return "No - This is a Loopback Address (127.x.x.x).";
                case "Link-Local Address":
                    return "No - This is a Link-Local Address (169.254.x.x).";
                case "Multicast Address":
                    return "No - This is a Multicast Address (224.x.x.x - 239.x.x.x).";
                case "Reserved Address":
                    return "No - This is a Reserved Address (240.x.x.x - 255.x.x.x).";
                case "Private IP Address":
                    return "Yes - This is a Private IP Address.";
                case "Public IP Address":
                    return "Yes - This is a Public IP Address.";
                default:
                    return "No - Unknown Address Type.";
            }

        } catch (Exception e) {
            return "No - Invalid IP or Subnet Mask Format.";
        }
    }
    private static String isGoodHostIP(String ipAddress, String subnetMask) {
        try {
            String[] ipOctets = ipAddress.split("\\.");
            String[] maskOctets = subnetMask.split("\\.");

            int[] ip = new int[4];
            int[] mask = new int[4];

            for (int i = 0; i < 4; i++) {
                ip[i] = Integer.parseInt(ipOctets[i]);
                mask[i] = Integer.parseInt(maskOctets[i]);
            }

            // Compute Network and Broadcast Address
            int[] network = new int[4];
            int[] broadcast = new int[4];

            for (int i = 0; i < 4; i++) {
                network[i] = ip[i] & mask[i];
                broadcast[i] = network[i] | (~mask[i] & 255);
            }

            String networkAddress = String.format("%d.%d.%d.%d", network[0], network[1], network[2], network[3]);
            String broadcastAddress = String.format("%d.%d.%d.%d", broadcast[0], broadcast[1], broadcast[2], broadcast[3]);

            // Check if the IP is a Network or Broadcast Address
            if (ipAddress.equals(networkAddress) || ipAddress.equals(broadcastAddress)) {
                return "No"; // Not a good host IP
            }

            // Check for Reserved IP Ranges
            String ipType = getAddressType(ipAddress);
            if (!ipType.equals("Public IP Address") && !ipType.equals("Private IP Address")) {
                return "No"; // Reserved addresses are not good for hosts
            }

            return "Yes"; // The IP is valid for a host
        } catch (Exception e) {
            return "No"; // Invalid IP or Mask Format
        }
    }
    private static String getAddressType(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int firstOctet = Integer.parseInt(octets[0]);
        int secondOctet = Integer.parseInt(octets[1]);

        // Check for Private IP Ranges
        if ((firstOctet == 10) ||
            (firstOctet == 172 && secondOctet >= 16 && secondOctet <= 31) ||
            (firstOctet == 192 && secondOctet == 168)) {
            return "Private IP Address";
        }
        
        // Check for Loopback Address
        if (firstOctet == 127) {
            return "Loopback Address";
        }

        // Check for Link-Local Address
        if (firstOctet == 169 && secondOctet == 254) {
            return "Link-Local Address";
        }

        // Check for Multicast Address
        if (firstOctet >= 224 && firstOctet <= 239) {
            return "Multicast Address";
        }

        // Check for Reserved Addresses (Experimental)
        if (firstOctet >= 240 && firstOctet <= 255) {
            return "Reserved Address";
        }

        return "Public IP Address"; // If none of the above, it's a public IP
    }
    private static String getIPClass(String ipAddress) {
        String[] octets = ipAddress.split("\\."); // Split IP by "."
        int firstOctet = Integer.parseInt(octets[0]); // Convert first octet to integer

        if (firstOctet >= 1 && firstOctet <= 126) {
            return "Class A";
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            return "Class B";
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            return "Class C";
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            return "Class D (Multicast)";
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            return "Class E (Experimental)";
        } else {
            return "Invalid IP Address";
        }
    }
    private static String toBinary(String ipAddress) {
        String[] octets = ipAddress.split("\\."); // Split by "."
        StringBuilder binaryIP = new StringBuilder();

        for (String octet : octets) {
            int value = Integer.parseInt(octet); // Convert to integer
            String binary = String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0'); // Convert to 8-bit binary
            binaryIP.append(binary).append("."); // Append with dot
        }

        return binaryIP.substring(0, binaryIP.length() - 1); // Remove trailing "."
    }

    
    private static String calculateNetworkId(String ip, String subnetMask) {
        String[] ipParts = ip.split("\\.");
        String[] maskParts = subnetMask.split("\\.");
        int[] networkIdParts = new int[4];

        // Perform bitwise AND operation
        for (int i = 0; i < 4; i++) {
            networkIdParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(maskParts[i]);
        }

        // Convert result to a valid IP format
        return networkIdParts[0] + "." + networkIdParts[1] + "." + networkIdParts[2] + "." + networkIdParts[3];
    }
    private static String getDefaultSubnetMask(String ip) {
		if (ip.isEmpty() || !ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) {
		    return "Invalid IP";
		}

		String[] octets = ip.split("\\.");
		int firstOctet;
		
		try {
		    firstOctet = Integer.parseInt(octets[0]);
		} catch (NumberFormatException e) {
		    return "Invalid IP";
		}

		if (firstOctet >= 1 && firstOctet <= 126) {
		    return "255.0.0.0";  // Class A
		} else if (firstOctet >= 128 && firstOctet <= 191) {
		    return "255.255.0.0";  // Class B
		} else if (firstOctet >= 192 && firstOctet <= 223) {
		    return "255.255.255.0";  // Class C
		} else {
		    return "Invalid IP";  // Not a valid Class A, B, or C address
		}
	}
    private static void addComponent(JPanel panel, Component component,
                                   GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }
    
    private static void addComponent(JPanel panel, Component component,
                                   GridBagConstraints gbc, int gridx, int gridy,
                                   int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        panel.add(component, gbc);
        
        // Reset gridwidth and gridheight after adding
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
    }
}
