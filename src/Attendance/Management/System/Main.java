import Attendance.Management.System.AddStudent;
import Attendance.Management.System.ViewStudents;
import Attendance.Management.System.DeleteStudent;
import Attendance.Management.System.SearchStudent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Attendance Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        JButton addStudentButton = new JButton("Add Student");
        JButton viewStudentsButton = new JButton("View Students");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton searchStudentButton = new JButton("Search Student");

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
            }
        });

        viewStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewStudents();
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteStudent();
            }
        });

        searchStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchStudent();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(addStudentButton);
        panel.add(viewStudentsButton);
        panel.add(deleteStudentButton);
        panel.add(searchStudentButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
