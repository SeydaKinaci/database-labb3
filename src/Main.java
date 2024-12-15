import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {

        String url = "jdbc:sqlite:C:/Users/46769/OneDrive/Masaüstü/sqlite-tools/seydaKinaciLabb3.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("0  - Stäng av\n" +
                "1  - Visa alla filmer\n" +
                "2  - Lägga till en ny film\n" +
                "3  - Uppdatera en film\n" +
                "4  - Ta bort en film\n" +
                "5  - Visa en lista över alla val.");
    }


    private static void inputMovieDelete(){
        System.out.println("Skriv in id:t på filmen som ska tas bort: ");
        int inputId = scanner.nextInt();
        movieDelete(inputId);
        scanner.nextLine();
    }

    private static void movieSelectAll(){
        String sql = "SELECT filmer.filmNamn,\n" +
                "       filmer.visningsYear,\n" +
                "       kategori.kategoriNamn,\n" +
                "       regissor.regissorId,\n" +
                "       filmer.filmRating\n"+
                "FROM filmer\n" +
                "INNER JOIN kategori ON filmer.filmKategoriId = kategori.kategoriId\n" +
                "INNER JOIN regissor ON filmer.filmRegissorId = regissor.regissorId;";

        try {
            Connection conn = connect();
            Statement st  = conn.createStatement();
            ResultSet rst    = st.executeQuery(sql);

            while (rst.next()) {
                System.out.println(
                        rst.getString("filmNamn") +  "\t" +
                        rst.getInt("visningsYear") + "\t" +
                        rst.getInt("filmRating") + "\t" +
                        rst.getString("kategoriNamn") + "\t" +
                        rst.getInt("regissorId"))
                        ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void filmInsert() {
        System.out.println("Skriv in filmens namn: ");
        String namn = scanner.nextLine();

        System.out.println("Skriv in filmens visningsår: ");
        int year = scanner.nextInt();

        System.out.println("Skriv in filmens rating (1-7): ");
        int rating = scanner.nextInt();
        while (rating < 1 || rating > 7) {
            System.out.println("Felaktig inmatning! Vänligen ange ett nummer mellan 1 och 7\n" +
                    "\n!");
            rating = scanner.nextInt();
        }

        System.out.println("Skriv in filmens kategori-id: ");
        int kategoriId = scanner.nextInt();

        System.out.println("Skriv in regissorns id: ");
        int regissor = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO filmer (filmNamn, visningsYear, filmRating,filmKategoriId, filmRegissorId)VALUES(?,?,?,?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, namn);
            pstmt.setInt(2, year);
            pstmt.setInt(3, rating);
            pstmt.setInt(4, kategoriId);
            pstmt.setInt(5, regissor);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny film");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        private static void filmUpdate() {
            System.out.println("Skriv in id:t på filmen som ska uppdateras: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Vad vill du uppdatera?\n" +
                    "1 - Filmnamn\n" +
                    "2 - Visningsår\n" +
                    "3 - Rating\n" +
                    "4 - Kategori-id\n"+
                    "5 - Regissor-id")
            ;
            int choice = scanner.nextInt();
            scanner.nextLine();

            String sql = null;
            switch (choice) {
                case 1:
                    System.out.println("Skriv in nytt namn för filmen: ");
                    String newName = scanner.nextLine();
                    sql = "UPDATE filmer SET filmNamn = ? WHERE filmId = ?";
                    try (Connection conn = connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, newName);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Filmens namn har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Skriv in nytt visningsår: ");
                    int newYear = scanner.nextInt();
                    sql = "UPDATE filmer SET visningsYear = ? WHERE filmId = ?";
                    try (Connection conn = connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, newYear);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Visningsår har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Skriv in ny rating (1-7): ");
                    int newRating = scanner.nextInt();
                    sql = "UPDATE filmer SET filmRating = ? WHERE filmId = ?";
                    try (Connection conn = connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, newRating);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Rating har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Skriv in ny kategori-id: ");
                    int newKategoriId = scanner.nextInt();
                    sql = "UPDATE filmer SET filmKategoriId = ? WHERE filmId = ?";
                    try (Connection conn = connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, newKategoriId);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Kategori-id har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Skriv in ny regissor-id:: ");
                    int newRegissorId = scanner.nextInt();
                    sql = "UPDATE filmer SET filmRegissorId = ? WHERE filmId = ?";
                    try (Connection conn = connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, newRegissorId);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Regissor id har uppdaterats.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Ogiltigt val.");
                    break;
            }
        }


        private static void movieDelete(int id) {
        String sql = "DELETE FROM filmer WHERE filmId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort filmen");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nVälj:");
            int move = scanner.nextInt();
            scanner.nextLine();

            switch (move) {
                case 0:
                    System.out.println("\nStänger ner...");
                    quit = true;
                    break;

                case 1:
                    movieSelectAll();
                    break;

                case 2:
                    filmInsert();
                    break;

                case 3:
                    filmUpdate();
                    break;

                case 4:
                    inputMovieDelete();
                    break;

                case 5:
                    printActions();
                    break;

                    default:
                        System.out.println("Ogiltigt val.Försök igen");
                        break;
            }
        }

    }

}