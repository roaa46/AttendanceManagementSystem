package Attendance.Management.System;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchStudent extends JFrame {
    private JTextField idField;

    public SearchStudent() {
        setTitle("Search Student");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(15);

        panel.add(new JLabel("ID:"));
        panel.add(idField);

        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudentInDB();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(searchButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    private void searchStudentInDB() {
        String id = idField.getText();

        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be an integer.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");
            String query = "SELECT name, section, status FROM studentDetails WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String section = resultSet.getString("section");
                String status = resultSet.getString("status");
                JOptionPane.showMessageDialog(this, "Name: " + name + ", Section: " + section + ", Status: " + status);
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.");
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching student.");
        }
    }
}
