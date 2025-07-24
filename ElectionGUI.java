import java.sql.*;
import java.util.Scanner;


public class ElectionApp {
    static final String URL = "jdbc:mysql://localhost:3306/election_db";
    static final String USER = "root";
    static final String PASSWORD = "9309";

    private final Scanner scanner = new Scanner(System.in);
    private final Connection con;

    public ElectionApp() throws SQLException {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void run() {
        while (true) {
            System.out.println("\n--- ELECTION SYSTEM ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Vote");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminLogin();
                    break;
                case "2":
                    vote();
                    break;
                case "3":
                    System.out.println("Thanks for voting...");

                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    private void adminLogin() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        if (user.equals("root") && pass.equals("root")) {
            adminPanel();
        } else {
            System.out.println("Incorrect credentials.");
        }
    }

    private void adminPanel() {
        while (true) {
            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. Add Candidate");
            System.out.println("2. View Candidates");
            System.out.println("3. Edit Candidate");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addCandidate();
                    break;
                case "2":
                    showCandidates();
                    break;
                case "3":
                    editCandidate();
                    break;
                case "4":
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    private void addCandidate() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Address: ");
            String address = scanner.nextLine();

            if (name == null || name.trim().isEmpty() || address == null || address.trim().isEmpty() || age < 18) {
                System.out.println("Invalid input.");
                return;
            }

            String sql = "INSERT INTO candidates (name, age, address, votes) VALUES (?, ?, ?, 0)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setString(3, address);
                ps.executeUpdate();
                System.out.println("Candidate added.");
            }
        } catch (Exception e) {
            System.out.println("Failed to add candidate: " + e.getMessage());
        }
    }

    private void showCandidates() {
        String query = "SELECT * FROM candidates";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            System.out.printf("%-5s %-15s %-5s %-15s %-5s%n", "ID", "Name", "Age", "Address", "Votes");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-5d %-15s %-5d%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getInt("votes"));
            }
        } catch (Exception e) {
            System.out.println("Error displaying candidates: " + e.getMessage());
        }
    }

    private void editCandidate() {
        try {
            System.out.print("Enter Candidate ID to edit: ");
            int id = Integer.parseInt(scanner.nextLine());

            String selectQuery = "SELECT * FROM candidates WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("Candidate not found.");
                    return;
                }

                System.out.print("New Name (" + rs.getString("name") + "): ");
                String name = scanner.nextLine();
                System.out.print("New Age (" + rs.getInt("age") + "): ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.print("New Address (" + rs.getString("address") + "): ");
                String address = scanner.nextLine();

                String updateQuery = "UPDATE candidates SET name=?, age=?, address=? WHERE id=?";
                try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                    updatePs.setString(1, name);
                    updatePs.setInt(2, age);
                    updatePs.setString(3, address);
                    updatePs.setInt(4, id);
                    updatePs.executeUpdate();
                    System.out.println("Candidate updated.");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to edit candidate: " + e.getMessage());
        }
    }

    private void vote() {
        try {
            System.out.print("Enter your Voter ID: ");
            String voterId = scanner.nextLine();

            String checkVoter = "SELECT * FROM voters WHERE voter_id = ?";
            try (PreparedStatement ps = con.prepareStatement(checkVoter)) {
                ps.setString(1, voterId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("You have already voted.");
                    return;
                }
            }

            showCandidates();
            System.out.print("Enter Candidate ID to vote: ");
            int cid = Integer.parseInt(scanner.nextLine());

            String voteSql = "UPDATE candidates SET votes = votes + 1 WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(voteSql)) {
                ps.setInt(1, cid);
                if (ps.executeUpdate() == 0) {
                    System.out.println("Invalid candidate ID.");
                    return;
                }
            }

            String insertVoter = "INSERT INTO voters (voter_id) VALUES (?)";
            try (PreparedStatement ps = con.prepareStatement(insertVoter)) {
                ps.setString(1, voterId);
                ps.executeUpdate();
            }

            System.out.println("Your vote has been recorded. Thank you!");
        } catch (Exception e) {
            System.out.println("Voting failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new ElectionApp().run();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }
}
