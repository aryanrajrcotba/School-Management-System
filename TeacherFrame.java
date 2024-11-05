import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class TeacherFrame extends JFrame {
    JTextField idField, nameField, subjectField;
    JTable teacherTable;
    DefaultTableModel tableModel;

    public TeacherFrame() {
        setTitle("Teacher Management");
        setSize(600, 400);
        setLayout(null);

        JLabel idLabel = new JLabel("Teacher ID (Optional):");
        JLabel nameLabel = new JLabel("Name:");
        JLabel subjectLabel = new JLabel("Subject:");

        idField = new JTextField();
        nameField = new JTextField();
        subjectField = new JTextField();

        JButton addButton = new JButton("Add Teacher");

        idLabel.setBounds(30, 20, 150, 20);
        nameLabel.setBounds(30, 50, 100, 20);
        subjectLabel.setBounds(30, 80, 100, 20);

        idField.setBounds(180, 20, 150, 20);
        nameField.setBounds(180, 50, 150, 20);
        subjectField.setBounds(180, 80, 150, 20);

        addButton.setBounds(120, 120, 150, 30);

        addButton.addActionListener(e -> {
            addTeacher();
            loadTeachers();
        });

        // Set up table to show teachers
        String[] columnNames = {"Teacher ID", "Name", "Subject"};
        tableModel = new DefaultTableModel(columnNames, 0);
        teacherTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setBounds(30, 160, 500, 150);

        add(idLabel);
        add(nameLabel);
        add(subjectLabel);
        add(idField);
        add(nameField);
        add(subjectField);
        add(addButton);
        add(scrollPane);

        setVisible(true);
        loadTeachers();
    }

    private void addTeacher() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String subject = subjectField.getText().trim();

        String sql = idText.isEmpty()
                ? "INSERT INTO teachers (name, subject) VALUES (?, ?)"
                : "INSERT INTO teachers (teacher_id, name, subject) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            if (idText.isEmpty()) {
                pst.setString(1, name);
                pst.setString(2, subject);
            } else {
                int teacherId = Integer.parseInt(idText);
                pst.setInt(1, teacherId);
                pst.setString(2, name);
                pst.setString(3, subject);
            }

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Teacher added successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding teacher.");
        }
    }

    private void loadTeachers() {
        tableModel.setRowCount(0); // Clear existing rows

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM teachers")) {

            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String name = rs.getString("name");
                String subject = rs.getString("subject");

                tableModel.addRow(new Object[]{id, name, subject});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
