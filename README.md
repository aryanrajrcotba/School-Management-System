# School Management System

This is a Java-based School Management System that allows you to manage student and teacher details, enter marks for students, and book appointments with teachers. The application uses Java Swing for the user interface and MySQL for database management.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)

## Features
- **Student Management**: Add, view, and manage student details.
- **Teacher Management**: Add, view, and manage teacher details.
- **Marks Entry**: Enter and display subject-wise marks for each student.
- **Appointment Booking**: Book and display appointments between students and teachers.
- **Data Persistence**: All data is stored in a MySQL database for persistent access.

## Technologies Used
- **Java**: Programming language used for application development.
- **Java Swing**: Framework for building the graphical user interface (GUI).
- **MySQL**: Database management system for storing student, teacher, marks, and appointment data.

## Set up MySQL:
Ensure you have MySQL installed on your machine.
Create a database for the project.

### Configure Database Connection: Update the DBConnection.java file with your MySQL credentials.

## Create Database Tables:
Run the SQL commands provided in the SQL Table Creation section to create the necessary tables in your MySQL database.

## Usage
#### Run the Application:
Compile and run the main application class (MainFrame.java).
Navigate through the GUI to manage students, teachers, marks, and appointments.
Add Student/Teacher: Use the respective frames to add student and teacher details.
Enter Marks: Use the Marks Management frame to enter marks for students. Enter the student ID, subject, and marks, then click "Add Marks."
Book Appointment: Use the Appointment Management frame to book appointments. Enter the student ID, teacher ID, and appointment date, then click "Book Appointment."
View Entries: The application will display entered marks and appointments in tables after each entry.

