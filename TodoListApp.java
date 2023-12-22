import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TodoListApp extends JFrame {

    private DefaultListModel<String> todoListModel;
    private JList<String> todoList;
    private JTextField taskInput;

    public TodoListApp() {
        setTitle("To-Do List App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        todoListModel = new DefaultListModel<>();
        todoList = new JList<>(todoListModel);
        taskInput = new JTextField();

        JButton addButton = createResizedButton("Add Task", "add.png", 24, 24);
        JButton removeButton = createResizedButton("Remove Task", "remove.png", 24, 24);

        // Add event handlers
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        // Create layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(todoList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add labels for better understanding
        JLabel taskLabel = new JLabel("Task:");
        inputPanel.add(taskLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(taskInput, gbc);

        // Reset gridx
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(removeButton, gbc);

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private JButton createResizedButton(String text, String imagePath, int width, int height) {
        JButton button = new JButton(text);
        try {
            Image img = ImageIO.read(new File(imagePath));
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(resizedImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            todoListModel.addElement(task);
            taskInput.setText("");
        }
    }

    private void removeTask() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to remove this task?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                todoListModel.remove(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.",
                    "No Task Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListApp().setVisible(true);
            }
        });
    }
}
