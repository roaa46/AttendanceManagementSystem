# Attendance Management System

This is a Java-based Attendance Management System designed to manage student records and their attendance status. The system provides functionalities to add, view, delete, search students, and also to add and view student reports.

## Features
- **Add Student:** Add new students to the system along with their details like name, ID, section, and attendance status.
- **View Students:** View and print the list of all students along with their details.
- **Delete Student:** Remove a student from the system using their ID.
- **Search Student:** Search for a student using their ID and view their details.
- **Student Report:** Add, view and print reports for individual students.

## Installation
1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone or download the project files from the repository.
3. Compile the Java files using `javac Main.java` command.
4. Run the application using `java Main` command.

## Usage
- Upon running the application, the main menu will appear.
- From the main menu, you can choose different options to perform various tasks related to student management.
- Each option leads to a different functionality:
    - **Add Student:** Allows you to add new students to the system.
    - **View Students:** Displays the list of all students with their details.
    - **Delete Student:** Lets you remove a student from the system.
    - **Search Student:** Enables you to search for a student using their ID.
    - **Student Report:** Provides functionality to add and view reports for individual students.
    - **Exit:** Closes the application.

## SQL script to create the database

```sql
-- Create database if not exists
CREATE DATABASE IF NOT EXISTS students;

-- Use the database
USE students;

-- Create studentDetails table
CREATE TABLE IF NOT EXISTS studentdetails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    section VARCHAR(10) NOT NULL,
    status ENUM('Present', 'Absent') NOT NULL,
    report VARCHAR(255) NOT NULL
);

```
