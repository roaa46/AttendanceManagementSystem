package Attendance.Management.System;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddStudent extends JFrame {
    private JTextField nameField, idField, sectionField;
    private JComboBox<String> statusBox;

    public AddStudent() {
        setTitle("Add Student");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nameField = new JTextField(15);
        idField = new JTextField(15);
        sectionField = new JTextField(15);

        String[] statuses = { "Present", "Absent" };
        statusBox = new JComboBox<>(statuses);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Section:"));
        panel.add(sectionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudentToDB();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(addButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    private void addStudentToDB() {
        String name = nameField.getText();
        String id = idField.getText();
        String section = sectionField.getText();
        String status = (String) statusBox.getSelectedItem();

        if (!name.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Invalid input, Please try again.");
            return;
        }

        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid input, Please try again.");
            return;
        }

        if (!section.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid input, Please try again.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");
            String query = "INSERT INTO studentDetails (name, id, section, status) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, section);
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();
            connection.close();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student.");
        }
    }
}
