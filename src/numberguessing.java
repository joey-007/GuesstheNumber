import java.sql.*;
import java.util.*;
public class numberguessing {
    public static void main(String[] args) throws Exception {
        numberguessing ng=new numberguessing();
        Scanner sc=new Scanner(System.in);


        while(true){
            System.out.println("Enter 1 for creating account");
            System.out.println("Enter 2 for playing game");
            System.out.println("Enter 3 for quit");
            int choice=sc.nextInt();
        switch (choice){
            case 1:ng.createaccount();
            break;
            case 2:ng.guess_the_number();
            break;
            case 3:
                System.out.println("See ya");
                System.exit(0);
            default:
                System.out.println("Eneter valid number");
        }
    }
    }

    public void createaccount() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = sc.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
            PreparedStatement smt = con.prepareStatement("insert into nuberscores values(?,?)");
            smt.setString(1, name);
            smt.setInt(2, 0);
            smt.executeUpdate();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void guess_the_number() throws Exception {
        Random rand = new Random();
        int random_num = rand.nextInt(100);
        System.out.println(random_num);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Guess within 5 guesses");
        System.out.println("Now Guess");
        int a = sc.nextInt();
        boolean x = false;
        int n = 5;
        int score = 0;
        for (int i = 1; i < 5; i++) {
            if (a == random_num) {
                System.out.println("Congrats its the right answer");
                x = true;
                score = (n - i + 1) * 100;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "707709@");
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from nuberscores where name='" + name + "'");
                    if (rs.next() == true) {
                        if (rs.getInt(2)-score<0) {
                            PreparedStatement smt = con.prepareStatement("update nuberscores set score=? where name=?");
                            smt.setInt(1, score);
                            smt.setString(2, name);
                            smt.executeUpdate();
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            } else if (a > random_num) {
                System.out.println("Enter a smaller number");
                a = sc.nextInt();
            } else {
                System.out.println("Enter a bigger number");
                a = sc.nextInt();
            }
        }
        if (x == false) {
            System.out.println("You've Lost");
            System.out.println("The number is" + " " + random_num);
        }

    }
}
