import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


/// AddStudent class
class AddStudent extends JFrame {
    private JTextField nameField, idField, sectionField;
    private JComboBox<String> statusBox;

    public AddStudent() {
        setTitle("Add Student");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 80, 25);
        add(nameLabel);

        nameField = new JTextField(15);
        nameField.setBounds(120, 30, 150, 25);
        add(nameField);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 60, 80, 25);
        add(idLabel);

        idField = new JTextField(15);
        idField.setBounds(120, 60, 150, 25);
        add(idField);

        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setBounds(30, 90, 80, 25);
        add(sectionLabel);

        sectionField = new JTextField(15);
        sectionField.setBounds(120, 90, 150, 25);
        add(sectionField);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(30, 120, 80, 25);
        add(statusLabel);

        String[] statuses = {"Present", "Absent"};
        statusBox = new JComboBox<>(statuses);
        statusBox.setBounds(120, 120, 150, 25);
        add(statusBox);

        JButton addButton = new JButton("Add Student");
        addButton.setBounds(110, 190, 120, 25);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudentToDB();
            }
        });
        add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(70, 230, 80, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });
        add(backButton);

        JButton ClearButton = new JButton("Clear");
        ClearButton.setBounds(170, 230, 80, 25);
        ClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                idField.setText("");
                sectionField.setText("");
            }
        });
        add(ClearButton);

        setLocationRelativeTo(null);
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

/*---------------------------------------------------------------------*/

/// DeleteStudent class
class DeleteStudent extends JFrame {
    JTextField idField;
    JLabel label;

    public DeleteStudent() {
        setTitle("Delete Student");

        idField = new JTextField(15);
        label = new JLabel("ID:");

        label.setBounds(50, 50, 100, 30);
        idField.setBounds(150, 50, 150, 30);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudentFromDB();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Go back to the main menu
                new Main();
                dispose();
            }
        });

        deleteButton.setBounds(50, 200, 150, 30);
        backButton.setBounds(210, 200, 100, 30);

        setLayout(null);
        add(label);
        add(idField);
        add(deleteButton);
        add(backButton);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

/*---------------------------------------------------------------------*/

/// SearchStudent class

class SearchStudent extends JFrame {
    private JTextField idField;

    public SearchStudent() {
        setTitle("Search Student");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 30, 80, 25);
        add(idLabel);

        idField = new JTextField(15);
        idField.setBounds(100, 30, 160, 25);
        add(idField);

        JButton searchButton = new JButton("Search Student");
        searchButton.setBounds(30, 70, 150, 25);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudentInDB();
            }
        });

        add(searchButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(190, 70, 70, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });

        add(backButton);
        setLocationRelativeTo(null);
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
                JOptionPane.showMessageDialog(this, "Student with ID: " + id + " his / her name: " + name + ", section: " + section + " and status: " + status);
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

/*---------------------------------------------------------------------*/

/// ViewStudents class

class ViewStudents extends JFrame {
    public ViewStudents() {
        setTitle("View Students");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 360, 250);
        add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(80, 270, 100, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });
        add(backButton);

        JButton PrintButton = new JButton("Print");
        PrintButton.setBounds(220, 270, 100, 30);
        PrintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.print();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ViewStudents.this, "Error printing student data.");
                }
            }
        });
        add(PrintButton);

        fetchAndDisplayStudents(textArea);
        setLocationRelativeTo(null);
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

/*---------------------------------------------------------------------*/

class StudentReport extends JFrame {
    public StudentReport() {
        setTitle("Student Report");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JButton addStudentReportButton = new JButton("Add Student Report");
        JButton viewStudentsReportsButton = new JButton("View Students Reports");
        JButton backButton = new JButton("Back");

        addStudentReportButton.setBounds(100, 50, 200, 30);
        viewStudentsReportsButton.setBounds(100, 100, 200, 30);
        backButton.setBounds(100, 150, 200, 30);

        addStudentReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudentReport();
                dispose();
            }
        });
        add(addStudentReportButton);

        viewStudentsReportsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewStudentReports();
                dispose();
            }
        });
        add(viewStudentsReportsButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
/*---------------------------------------------------------------------*/
class AddStudentReport extends JFrame {
    private JTextField idField;
    private JTextArea textArea;

    public AddStudentReport() {
        setTitle("Add Student Report");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 30, 80, 25);
        add(idLabel);

        idField = new JTextField(15);
        idField.setBounds(120, 30, 150, 25);
        add(idField);

        JLabel tesxtLabel = new JLabel("Insert report of student with this ID");
        tesxtLabel.setBounds(30, 60, 300, 25);
        add(tesxtLabel);

        textArea = new JTextArea();
        textArea.setBounds(30, 90, 300, 125);
        add(textArea);

        JButton addButton = new JButton("Add Student Report");
        addButton.setBounds(110, 230, 150, 25);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudentReportToDB();
            }
        });
        add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(70, 270, 80, 25);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });
        add(backButton);

        JButton ClearButton = new JButton("Clear");
        ClearButton.setBounds(220, 270, 80, 25);
        ClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                textArea.setText("");
            }
        });
        add(ClearButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addStudentReportToDB() {
        String id = idField.getText();
        String report = textArea.getText();

        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid input, Please try again.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");

            // Check if student with given ID exists
            String checkQuery = "SELECT * FROM studentdetails WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, id);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // If student exists, insert the report
                String insertQuery = "UPDATE studentdetails SET report = ? WHERE id = ?";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, report);
                insertStatement.setString(2, id);

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Student report added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add student report.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.");
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student report.");
        }
    }
}

/*---------------------------------------------------------------------*/
class ViewStudentReports extends JFrame {
    public ViewStudentReports() {
        setTitle("View Student Reports");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 360, 250);
        add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(80, 270, 100, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main();
                dispose();
            }
        });
        add(backButton);

        JButton printButton = new JButton("Print");
        printButton.setBounds(220, 270, 100, 30);
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.print();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ViewStudentReports.this, "Error printing student data.");
                }
            }
        });
        add(printButton);

        fetchAndDisplayStudentReports(textArea);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchAndDisplayStudentReports(JTextArea textArea) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/students", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM studentdetails");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String report = resultSet.getString("report");
                textArea.append("ID: " + id + ", Report: " + report + "\n");
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student reports.");
        }
    }
}

/*---------------------------------------------------------------------*/
public class Main {
    Main() {
        JFrame frame = new JFrame("Attendance Management System");

        JButton addStudentButton = new JButton("Add Student");
        JButton viewStudentsButton = new JButton("View Students");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton searchStudentButton = new JButton("Search Student");
        JButton studentReportButton = new JButton("Students Reports");
        JButton exitButton = new JButton("Exit");

        addStudentButton.setBounds(100, 50, 200, 30);
        viewStudentsButton.setBounds(100, 100, 200, 30);
        deleteStudentButton.setBounds(100, 150, 200, 30);
        searchStudentButton.setBounds(100, 200, 200, 30);
        studentReportButton.setBounds(100, 250, 200, 30);
        exitButton.setBounds(100, 300, 200, 30);

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
                frame.dispose();
            }
        });

        viewStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewStudents();
                frame.dispose();
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteStudent();
                frame.dispose();
            }
        });

        searchStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchStudent();
                frame.dispose();
            }
        });

        studentReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentReport();
                frame.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(addStudentButton);
        frame.add(viewStudentsButton);
        frame.add(deleteStudentButton);
        frame.add(searchStudentButton);
        frame.add(studentReportButton);
        frame.add(exitButton);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}