import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ElectionGUI extends JFrame {
    static final String URL = "jdbc:mysql://localhost:3306/election_db";
    static final String USER = "root";
    static final String PASSWORD = "9309";
    private Connection con;

    public ElectionGUI() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            showMainMenu();
        } catch (SQLException e) {
            showError("Database connection failed.");
            e.printStackTrace();
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return button;
    }

    private void showMainMenu() {
        JFrame frame = new JFrame("Election System - Main Menu");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel welcomeLabel = new JLabel("Welcome to the Election System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton adminBtn = createStyledButton("Admin Login");
        JButton userBtn = createStyledButton("User Login");
        JButton exitBtn = createStyledButton("Exit");

        adminBtn.addActionListener(e -> {
            frame.dispose();
            showAdminLogin();
        });

        userBtn.addActionListener(e -> {
            frame.dispose();
            showUserPanel();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(adminBtn);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(userBtn);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(exitBtn);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void showAdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16 ));
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton loginBtn = createStyledButton("Login");
        JButton backBtn = createStyledButton("Back");

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (username.equals("root") && password.equals("root")) {
                frame.dispose();
                showAdminPanel();
            } else {
                showError("Invalid admin credentials!");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            showMainMenu();
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userLabel);
        mainPanel.add(userField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passLabel);
        mainPanel.add(passField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(backBtn);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(500, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(240, 248, 255));

        JLabel label = new JLabel("Admin Panel");
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addCandidateBtn = createStyledButton("Add Candidate");
        JButton viewCandidatesBtn = createStyledButton("View Candidates");
        JButton modifyCandidateBtn = createStyledButton("Modify Candidate");
        JButton backBtn = createStyledButton("Logout");

        addCandidateBtn.addActionListener(e -> {
            frame.dispose();
            showAddCandidateForm();
        });

        viewCandidatesBtn.addActionListener(e -> {
            frame.dispose();
            showCandidateDetails();
        });

        modifyCandidateBtn.addActionListener(e -> {
            frame.dispose();
            showModifyCandidateForm();
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            showMainMenu();
        });

        panel.add(label);
        panel.add(Box.createVerticalStrut(30));
        panel.add(addCandidateBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(viewCandidatesBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(modifyCandidateBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(backBtn);

        frame.add(panel);
        frame.setVisible(true);
    }
        private void showAddCandidateForm() {
            JFrame frame = new JFrame("Add Candidate");
            frame.setSize(450, 450);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(240, 248, 255));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

            JLabel title = new JLabel("Add New Candidate");
            title.setFont(new Font("Arial", Font.BOLD, 22));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel ageLabel = new JLabel("Age:");
            JTextField ageField = new JTextField();
            ageField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel addressLabel = new JLabel("Address:");
            JTextField addressField = new JTextField();
            addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));

             nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
             ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
             addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
             
            JButton addBtn = createStyledButton("Add Candidate");
            JButton cancelBtn = createStyledButton("Cancel");

            addBtn.addActionListener(e -> {
                try {
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    String address = addressField.getText().trim();

                    if (name.isEmpty() || address.isEmpty()) {
                        showError("Please fill all fields.");
                        return;
                    }

                    if (age < 18) {
                        showError("Candidate must be at least 18 years old.");
                        return;
                    }

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO candidates (name, age, address, votes) VALUES (?, ?, ?, 0)");
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setString(3, address);
                    stmt.executeUpdate();

                    showMessage("Candidate added successfully.");
                    frame.dispose();
                    showAdminPanel();
                } catch (Exception ex) {
                    showError("Invalid input. Please enter valid data.");
                }
            });

            cancelBtn.addActionListener(e -> {
                frame.dispose();
                showAdminPanel();
            });

            panel.add(title);
            panel.add(Box.createVerticalStrut(20));
            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(Box.createVerticalStrut(10));
            panel.add(ageLabel);
            panel.add(ageField);
            panel.add(Box.createVerticalStrut(10));
            panel.add(addressLabel);
            panel.add(addressField);
            panel.add(Box.createVerticalStrut(20));
            panel.add(addBtn);
            panel.add(Box.createVerticalStrut(10));
            panel.add(cancelBtn);

            frame.add(panel);
            frame.setVisible(true);
        }


    private void showModifyCandidateForm() {
        JFrame frame = new JFrame("Modify Candidate");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Modify Candidate Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField();
        idField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        idField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton loadBtn = createStyledButton("Load Candidate");
        JButton backBtn = createStyledButton("Back");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Enter Candidate ID to Modify:"));
        formPanel.add(idField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(loadBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backBtn);

        loadBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM candidates WHERE id = ?");
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String address = rs.getString("address");

                    JTextField nameField = new JTextField(name);
                    JTextField ageField = new JTextField(String.valueOf(age));
                    JTextField addressField = new JTextField(address);

                    nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    ageField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                    JButton updateBtn = createStyledButton("Update Candidate");

                    updateBtn.addActionListener(ev -> {
                        try {
                            String newName = nameField.getText();
                            int newAge = Integer.parseInt(ageField.getText());
                            String newAddress = addressField.getText();

                            if (newName.isEmpty() || newAddress.isEmpty()) {
                                showError("Fields cannot be empty.");
                                return;
                            }

                            if (newAge < 18) {
                                showError("Candidate age must be at least 18.");
                                return;
                            }

                            PreparedStatement update = con.prepareStatement("UPDATE candidates SET name = ?, age = ?, address = ? WHERE id = ?");
                            update.setString(1, newName);
                            update.setInt(2, newAge);
                            update.setString(3, newAddress);
                            update.setInt(4, id);
                            update.executeUpdate();

                            showMessage("Candidate updated successfully.");
                            frame.dispose();
                            showAdminPanel();
                        } catch (Exception ex) {
                            showError("Invalid input or update failed.");
                        }
                    });

                   JLabel nameLabel = new JLabel("Name:");
                    nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JLabel ageLabel = new JLabel("Age:");
                    ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JLabel addressLabel = new JLabel("Address:");
                    addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    ageField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                    addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                    formPanel.removeAll();
                    formPanel.add(nameLabel);
                    formPanel.add(nameField);
                    formPanel.add(Box.createVerticalStrut(10));
                    formPanel.add(ageLabel);
                    formPanel.add(ageField);
                    formPanel.add(Box.createVerticalStrut(10));
                    formPanel.add(addressLabel);
                    formPanel.add(addressField);
                    formPanel.add(Box.createVerticalStrut(20));
                    formPanel.add(updateBtn);
                    formPanel.add(Box.createVerticalStrut(10));
                    formPanel.add(backBtn);

                    formPanel.revalidate();
                    formPanel.repaint();
                } else {
                    showError("Candidate not found.");
                }
            } catch (Exception ex) {
                showError("Invalid candidate ID.");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            showAdminPanel();
        });

        frame.add(Box.createVerticalStrut(20));
        frame.add(title);
        frame.add(Box.createVerticalStrut(10));
        frame.add(formPanel);
        frame.setVisible(true);
    }

    private void showCandidateDetails() {
        JFrame frame = new JFrame("Candidates");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");

            StringBuilder sb = new StringBuilder();
            sb.append("ID\tName\tAge\tAddress\tVotes\n");
            while (rs.next()) {
                sb.append(rs.getInt("id")).append("\t")
                  .append(rs.getString("name")).append("\t")
                  .append(rs.getInt("age")).append("\t")
                  .append(rs.getString("address")).append("\t")
                  .append(rs.getInt("votes")).append("\n");
            }
            area.setText(sb.toString());
        } catch (SQLException e) {
            showError("Failed to load candidate details.");
        }

        frame.add(new JScrollPane(area), BorderLayout.CENTER);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            frame.dispose();
            showAdminPanel();
        });
        frame.add(back, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showUserPanel() {
        JFrame frame = new JFrame("User Voting Panel");
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Enter Voter ID:");
        JTextField voterIdField = new JTextField(20);
        voterIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        voterIdField.setPreferredSize(new Dimension(300, 30));
        topPanel.add(label);
        topPanel.add(voterIdField);

        JPanel candidatePanel = new JPanel();
        candidatePanel.setLayout(new BoxLayout(candidatePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(candidatePanel);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM candidates");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                JButton voteBtn = createStyledButton("Vote for " + name);
                voteBtn.addActionListener(e -> {
                    String voterId = voterIdField.getText().trim();
                    if (voterId.isEmpty()) {
                        showError("Please enter Voter ID.");
                        return;
                    }

                    try {
                        PreparedStatement check = con.prepareStatement("SELECT * FROM voters WHERE voter_id = ?");
                        check.setString(1, voterId);
                        ResultSet voterCheck = check.executeQuery();

                        if (voterCheck.next()) {
                            showError("You have already voted.");
                            return;
                        }

                        PreparedStatement vote = con.prepareStatement("UPDATE candidates SET votes = votes + 1 WHERE id = ?");
                        vote.setInt(1, id);
                        vote.executeUpdate();

                        PreparedStatement insertVoter = con.prepareStatement("INSERT INTO voters (voter_id) VALUES (?)");
                        insertVoter.setString(1, voterId);
                        insertVoter.executeUpdate();

                        showMessage("Vote cast successfully for " + name + ".");
                        frame.dispose();
                        showMainMenu();
                    } catch (SQLException ex) {
                        showError("Voting failed.");
                    }
                });

                candidatePanel.add(Box.createVerticalStrut(10));
                candidatePanel.add(voteBtn);
            }
        } catch (SQLException ex) {
            showError("Failed to load candidates.");
        }

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            frame.dispose();
            showMainMenu();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backBtn);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new ElectionGUI();
    }
}
