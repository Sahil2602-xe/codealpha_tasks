import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField nameField, marksField;
    private JTextArea displayArea;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Student Name:"));
        nameField = new JTextField(15);
        add(nameField);

        add(new JLabel("Marks:"));
        marksField = new JTextField(5);
        add(marksField);

        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Show Report");

        add(addButton);
        add(reportButton);

        displayArea = new JTextArea(12, 30);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        // Add Student Action
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int marks = Integer.parseInt(marksField.getText());

                students.add(new Student(name, marks));
                displayArea.append("Added: " + name + " (" + marks + ")\n");

                nameField.setText("");
                marksField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        // Report Action
        reportButton.addActionListener(e -> {
            if (students.isEmpty()) {
                displayArea.setText("No students added.\n");
                return;
            }

            int total = 0;
            int highest = students.get(0).marks;
            int lowest = students.get(0).marks;

            displayArea.setText("=== Student Report ===\n");

            for (Student s : students) {
                displayArea.append(s.name + " : " + s.marks + "\n");
                total += s.marks;
                if (s.marks > highest) highest = s.marks;
                if (s.marks < lowest) lowest = s.marks;
            }

            double average = (double) total / students.size();

            displayArea.append("\nAverage: " + average);
            displayArea.append("\nHighest: " + highest);
            displayArea.append("\nLowest: " + lowest);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}