package Attendance.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewStudents extends JFrame {
    public ViewStudents() {
        setTitle("View Students");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(backButton, BorderLayout.SOUTH);
        add(panel);

        fetchAndDisplayStudents(textArea);

        setVisible(true);
    }

    private void fetchAndDisplayStudents(JTextArea textArea) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM studentDetails");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String id = resultSet.getString("id");
                String section = resultSet.getString("section");
                String status = resultSet.getString("status");
                textArea.append("Name: " + name + ", ID: " + id + ", Section: " + section + ", Status: " + status + "\n");
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student data.");
        }
    }
}
