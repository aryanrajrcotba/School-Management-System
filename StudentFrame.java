import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class StudentFrame extends JFrame {
    JTextField idField, nameField, ageField, gradeField;
    JTable studentTable;
    DefaultTableModel tableModel;

    public StudentFrame() {
        setTitle("Student Management");
        setSize(600, 400);
        setLayout(null);

        JLabel idLabel = new JLabel("Student ID (Optional):");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel gradeLabel = new JLabel("Grade:");

        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        gradeField = new JTextField();

        JButton addButton = new JButton("Add Student");

        idLabel.setBounds(30, 20, 150, 20);
        nameLabel.setBounds(30, 50, 100, 20);
        ageLabel.setBounds(30, 80, 100, 20);
        gradeLabel.setBounds(30, 110, 100, 20);

        idField.setBounds(180, 20, 150, 20);
        nameField.setBounds(180, 50, 150, 20);
        ageField.setBounds(180, 80, 150, 20);
        gradeField.setBounds(180, 110, 150, 20);

        addButton.setBounds(120, 160, 150, 30);

        addButton.addActionListener(e -> {
            addStudent();
            loadStudents();
        });

        // Set up table to show students
        String[] columnNames = {"Student ID", "Name", "Age", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(30, 200, 500, 150);

        add(idLabel);
        add(nameLabel);
        add(ageLabel);
        add(gradeLabel);
        add(idField);
        add(nameField);
        add(ageField);
        add(gradeField);
        add(addButton);
        add(scrollPane);

        setVisible(true);
        loadStudents();
    }

    private void addStudent() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String grade = gradeField.getText().trim();

        String sql = idText.isEmpty()
                ? "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)"
                : "INSERT INTO students (student_id, name, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            if (idText.isEmpty()) {
                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, grade);
            } else {
                int studentId = Integer.parseInt(idText);
                pst.setInt(1, studentId);
                pst.setString(2, name);
                pst.setInt(3, age);
                pst.setString(4, grade);
            }

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student.");
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0); // Clear existing rows

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String grade = rs.getString("grade");

                tableModel.addRow(new Object[]{id, name, age, grade});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
