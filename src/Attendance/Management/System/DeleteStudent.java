package Attendance.Management.System;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteStudent extends JFrame {
    private JTextField idField;

    public DeleteStudent() {
        setTitle("Delete Student");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        idField = new JTextField(15);

        panel.add(new JLabel("ID:"));
        panel.add(idField);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudentFromDB();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(deleteButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    private void deleteStudentFromDB() {
        String id = idField.getText();

        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be an integer.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");
            String query = "DELETE FROM studentDetails WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student.");
        }
    }
}
