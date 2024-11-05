import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MarksFrame extends JFrame {
    private JTextField studentIdField, subjectField, marksField;
    private JTable marksTable;
    private DefaultTableModel tableModel;

    public MarksFrame() {
        setTitle("Marks Management");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel studentIdLabel = new JLabel("Student ID:");
        JLabel subjectLabel = new JLabel("Subject:");
        JLabel marksLabel = new JLabel("Marks:");

        studentIdField = new JTextField();
        subjectField = new JTextField();
        marksField = new JTextField();

        JButton addButton = new JButton("Add Marks");

        studentIdLabel.setBounds(30, 20, 100, 20);
        subjectLabel.setBounds(30, 50, 100, 20);
        marksLabel.setBounds(30, 80, 100, 20);

        studentIdField.setBounds(180, 20, 150, 20);
        subjectField.setBounds(180, 50, 150, 20);
        marksField.setBounds(180, 80, 150, 20);

        addButton.setBounds(120, 120, 150, 30);

        addButton.addActionListener(e -> {
            addMarks();
            loadMarks();
        });

        // Set up table to show marks
        String[] columnNames = {"Student ID", "Subject", "Marks"};
        tableModel = new DefaultTableModel(columnNames, 0);
        marksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(marksTable);
        scrollPane.setBounds(30, 160, 500, 150);

        add(studentIdLabel);
        add(subjectLabel);
        add(marksLabel);
        add(studentIdField);
        add(subjectField);
        add(marksField);
        add(addButton);
        add(scrollPane);

        setVisible(true);
        loadMarks();
    }

    private void addMarks() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText().trim());
            String subject = subjectField.getText().trim();
            int marks = Integer.parseInt(marksField.getText().trim());

            String sql = "INSERT INTO marks (student_id, subject, marks) VALUES (?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, studentId);
                pst.setString(2, subject);
                pst.setInt(3, marks);

                int affectedRows = pst.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Marks added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add marks. Please check the student ID.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding marks: " + ex.getMessage());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for student ID and marks.");
        }
    }

    private void loadMarks() {
        tableModel.setRowCount(0); // Clear existing rows

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM marks")) {

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String subject = rs.getString("subject");
                int marks = rs.getInt("marks");

                tableModel.addRow(new Object[]{studentId, subject, marks});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
