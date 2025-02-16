import javax.swing.*;
import java.awt.*;

public class IPCalculatorGUI {

	private static JButton computeNowButton;
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
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        
        

        // Add components to content pane
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        // Add ActionListener for close button
		closeButton.addActionListener(e -> frame.dispose());	

		// Add ActionListener for save button
		//saveButton.addActionListener(e -> saveToFile(ipAddressField.getText(), subnetMaskField.getText(), networkIdField.getText()));
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
        
        JTextField ipAddressField = new JTextField(15);
        JTextField subnetMaskField = new JTextField(15);
        JTextField networkIdField = new JTextField(15);
        networkIdField.setEditable(false);
        
        JButton resetButton = new JButton("Reset");
        JButton defaultMaskButton = new JButton("Default Mask");
        computeNowButton = new JButton("Compute Now");
        
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
        addComponent(panel, computeNowButton, gbc, 2, 2);
        
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
        
        JTextField ipClassField = new JTextField(15);
        JTextField addressTypeField = new JTextField(15);
        JTextField goodIpForHostField = new JTextField(15);
        JTextField reasonField = new JTextField(15);
        
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
        
        JTextField binaryIpField = new JTextField(30);
        JTextField binaryMaskField = new JTextField(30);
        JTextField binaryNetworkIdField = new JTextField(30);
        
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
        
        JTextField numSubnetworksField = new JTextField(15);
        JTextField numHostsField = new JTextField(15);
        JTextField rangeField = new JTextField(15);
        JTextArea networkIdArea = new JTextArea(3, 20);
        JTextArea broadcastIdArea = new JTextArea(3, 20);
        
        // Make text areas scrollable
        JScrollPane networkIdScroll = new JScrollPane(networkIdArea);
        JScrollPane broadcastIdScroll = new JScrollPane(broadcastIdArea);
        
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
