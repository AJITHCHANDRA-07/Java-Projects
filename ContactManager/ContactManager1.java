import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ContactManager1 extends JFrame {
    private ArrayList<Contact> contactList = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> contactJList = new JList<>(listModel);

    public ContactManager1() {
        setTitle("Contact Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add Contact Panel
        JPanel addPanel = new JPanel(new GridLayout(5, 2));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();
        JButton addButton = new JButton("Add Contact");

        addPanel.add(new JLabel("Name:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("Phone:"));
        addPanel.add(phoneField);
        addPanel.add(new JLabel("Email:"));
        addPanel.add(emailField);
        addPanel.add(new JLabel("Address:"));
        addPanel.add(addressField);
        addPanel.add(addButton);

        // Contact List Panel
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(contactJList), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton updateButton = new JButton("Update Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton searchButton = new JButton("Search Contact");

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);

        // Add panels to frame
        add(addPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Contact Action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            Contact contact = new Contact(name, phone, email, address);
            contactList.add(contact);
            listModel.addElement(name);
            clearFields(nameField, phoneField, emailField, addressField);
        });

        // Update Contact Action
        updateButton.addActionListener(e -> {
            String selectedName = contactJList.getSelectedValue();
            if (selectedName != null) {
                for (Contact contact : contactList) {
                    if (contact.getName().equals(selectedName)) {
                        contact.setPhoneNumber(phoneField.getText());
                        contact.setEmail(emailField.getText());
                        contact.setAddress(addressField.getText());
                        listModel.set(contactList.indexOf(contact), contact.getName());
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to update.");
            }
        });

        // Delete Contact Action
        deleteButton.addActionListener(e -> {
            String selectedName = contactJList.getSelectedValue();
            if (selectedName != null) {
                contactList.removeIf(contact -> contact.getName().equals(selectedName));
                listModel.removeElement(selectedName);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
            }
        });

        // Search Contact Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = JOptionPane.showInputDialog("Enter name or phone number to search:");
                boolean found = false;
                for (Contact contact : contactList) {
                    if (contact.getName().equals(searchTerm) || contact.getPhoneNumber().equals(searchTerm)) {
                        nameField.setText((String) contact.getName());
                        phoneField.setText((String) contact.getPhoneNumber());
                        emailField.setText(contact.getEmail());
                        addressField.setText(contact.getAddress());
                        found = true;
                        break;
                    }
                }   if (!found) {
                    JOptionPane.showMessageDialog(ContactManager1.this, "No contact found with the given name or phone number.");
                }
            }
        });

        setVisible(true);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager1::new);
    }
}
