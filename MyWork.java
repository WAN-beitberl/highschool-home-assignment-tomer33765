import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MyWork {
    public static Scanner reader = new Scanner(System.in);
    public static void Menu(String jdbcURL, String username, String password)
    {
        int indexNum;
        System.out.println("Hi Sima! Enter option number:");
        indexNum = reader.nextInt();

        while(indexNum != 8)
        {
            switch(indexNum)
            {
                case 1:
                    option1(jdbcURL, username, password);
                    break;
                case 2:
                    option2(jdbcURL, username, password);
                    break;
                case 3:
                    option3(jdbcURL, username, password);
                    break;
                case 4:
                    option4(jdbcURL, username, password);
                    break;
                case 5:
                    System.out.println("Enter ID:");
                    int stID;
                    stID = reader.nextInt();
                    option5(stID, jdbcURL, username, password);
                    break;
                case 6:
                    option6(jdbcURL, username, password);
                    break;
                case 7:
                    int id_card;
                    System.out.println("Enter ID:");
                    id_card = reader.nextInt();
                    selectAvg(id_card, jdbcURL, username, password);
                    break;
                default:
                    break;
            }
            System.out.println("Enter again:");
            indexNum = reader.nextInt();
        }
    }

    public static void option1(String jdbcURL, String username, String password)
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("School average = "+avg);
            }
        }
        catch (SQLException e){
                System.out.println(e);
        }
    }

    public static void option2(String jdbcURL, String username, String password)
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool " +
                         "where gender = \"Male\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("Boys average = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void option3(String jdbcURL, String username, String password)
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool " +
                    "where gender = \"Female\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("Girls average = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void option4(String jdbcURL, String username, String password)
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(cm_height) from highschool " +
                    "where cm_height >= 200 group by car_color "+
                    "having car_color = \"Purple\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(cm_height)");
                System.out.println("Average height of those equal or above 200 cm that have a purple car = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void option5(int stud_id, String jdbcURL, String username, String password)
    {
        Connection con = null;
        int stud2 = 0;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select friend_id, other_friend_id from highschool_friendships " +
                    "where friend_id = "+stud_id+" or other_friend_id = "+stud_id;
            PreparedStatement p = connection.prepareStatement(select);
            ResultSet rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                int id = rs.getInt("friend_id");
                int id2 = rs.getInt("other_friend_id");
                if(id == stud_id && id2 != 0 && id2 != stud_id) {
                    stud2 = id2;
                }
                else if (id != 0 && id != stud_id)
                {
                    stud2 = id;
                }
                if(stud2 != 0)
                {
                    System.out.println(stud2);
                    String select2 = "select friend_id, other_friend_id from highschool_friendships " +
                            "where friend_id = "+stud2+" or other_friend_id = "+stud2;
                    PreparedStatement innerP = connection.prepareStatement(select2);
                    ResultSet innerRes = innerP.executeQuery();

                    // Condition check
                    while (innerRes.next()) {
                        int id3 = innerRes.getInt("friend_id");
                        int id4 = innerRes.getInt("other_friend_id");
                        if(id3 == stud2 && id4 != 0 && id4 != stud_id) {
                            System.out.println(id4);
                        }
                        else if(id3 != 0 && id3 != stud_id && id3 != stud2) System.out.println(id3);
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void option6(String jdbcURL, String username, String password)
    {
        int popular = 0;
        int unpopular = 0;
        int normal = 0;
        int[] friendships = new int[1001];

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        String query = "SELECT friend_id, other_friend_id FROM highschool_friendships" +
                " WHERE friend_id != 0 AND other_friend_id != 0";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int friend1 = resultSet.getInt("friend_id");
                int friend2 = resultSet.getInt("other_friend_id");

                friendships[friend1]++;
                friendships[friend2]++;
            }

            for(int i = 1; i <1001; i++) {
                if (friendships[i] == 0)
                    unpopular++;
                else if (friendships[i] == 1)
                    normal++;
                else
                    popular++;
            }
            System.out.println("Percent of popular students: " + (float)popular/10);
            System.out.println("Percent of unpopular students: " + (float)unpopular/10);
            System.out.println("Percent of normal students: " + (float)normal/10);

        }
        catch (SQLException e){
            System.out.println(e);
        }

    }


    public static void selectAvg(int id_card, String jdbcURL, String username, String password)// 7
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select id, grade_avg from my_view where id = "+id_card;
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            //System.out.println("identification_card\t\taverage");


            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("grade_avg");
                System.out.println("Average = "+avg);

                //System.out.println(id + "\t\t" + avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }


    public static void main(String[] args)
    {
        String jdbcURL = "jdbc:mysql://localhost:3306/myschema";
        String username = "root";
        String password = "Seniora21";

        Menu(jdbcURL, username, password);
    }
}
