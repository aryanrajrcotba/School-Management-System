import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("School Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton studentButton = new JButton("Manage Students");
        JButton teacherButton = new JButton("Manage Teachers");
        JButton marksButton = new JButton("Enter Marks");
        JButton appointmentButton = new JButton("Book Appointment");

        studentButton.setBounds(50, 50, 200, 30);
        teacherButton.setBounds(50, 100, 200, 30);
        marksButton.setBounds(50, 150, 200, 30);
        appointmentButton.setBounds(50, 200, 200, 30);

        studentButton.addActionListener(e -> new StudentFrame());
        teacherButton.addActionListener(e -> new TeacherFrame());
        marksButton.addActionListener(e -> new MarksFrame());
        appointmentButton.addActionListener(e -> new AppointmentFrame());

        add(studentButton);
        add(teacherButton);
        add(marksButton);
        add(appointmentButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
