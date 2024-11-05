import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AppointmentFrame extends JFrame {
    private JTextField studentIdField, teacherIdField, dateField;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    public AppointmentFrame() {
        setTitle("Appointment Management");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel studentIdLabel = new JLabel("Student ID:");
        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");

        studentIdField = new JTextField();
        teacherIdField = new JTextField();
        dateField = new JTextField();

        JButton addButton = new JButton("Book Appointment");

        studentIdLabel.setBounds(30, 20, 100, 20);
        teacherIdLabel.setBounds(30, 50, 100, 20);
        dateLabel.setBounds(30, 80, 150, 20);

        studentIdField.setBounds(180, 20, 150, 20);
        teacherIdField.setBounds(180, 50, 150, 20);
        dateField.setBounds(180, 80, 150, 20);

        addButton.setBounds(120, 120, 150, 30);

        addButton.addActionListener(e -> {
            bookAppointment();
            loadAppointments();
        });

        // Set up table to show appointments
        String[] columnNames = {"Student ID", "Teacher ID", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBounds(30, 160, 500, 150);

        add(studentIdLabel);
        add(teacherIdLabel);
        add(dateLabel);
        add(studentIdField);
        add(teacherIdField);
        add(dateField);
        add(addButton);
        add(scrollPane);

        setVisible(true);
        loadAppointments();
    }

    private void bookAppointment() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText().trim());
            int teacherId = Integer.parseInt(teacherIdField.getText().trim());
            String date = dateField.getText().trim();

            String sql = "INSERT INTO appointments (student_id, teacher_id, date) VALUES (?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, studentId);
                pst.setInt(2, teacherId);
                pst.setString(3, date);

                int affectedRows = pst.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to book appointment. Please check student and teacher IDs.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error booking appointment: " + ex.getMessage());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for student and teacher IDs.");
        }
    }

    private void loadAppointments() {
        tableModel.setRowCount(0); // Clear existing rows

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM appointments")) {

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int teacherId = rs.getInt("teacher_id");
                String date = rs.getString("date");

                tableModel.addRow(new Object[]{studentId, teacherId, date});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
