import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class InsertToTables {
    public static void insertValuesToFriendships(String jdbcURL, String username, String password, String csvFilePath)
    {
        int other_friend_id;
        int friend_id;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO highschool_friendships (friend_id, other_friend_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");    // Change variables and data type

                //int id = Integer.parseInt(data[0]);

                if(data.length < 2 || data[1].equals(""))
                {
                    statement.setNull(1, Types.INTEGER);
                }
                else
                {
                    friend_id = Integer.parseInt(data[1]);
                    statement.setInt(1, friend_id);
                }

                if(data.length < 3)
                {
                    statement.setNull(2, Types.INTEGER);
                }
                else
                {
                    other_friend_id = Integer.parseInt(data[2]);
                    statement.setInt(2, other_friend_id);
                }

                //statement.setInt(1, id);



                //statement.setString(4, comment);

                statement.addBatch();

                //if (count % batchSize == 0) {
                statement.executeBatch();
                //}
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();

            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertValuesToHighschool(String jdbcURL, String username, String password, String csvFilePath)
    {

        String first_name,last_name, email, gender, ip_address, has_car, car_color;
        int cm_height, age, grade,  identification_card;
        double grade_avg;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO highschool " +
                    "(first_name,last_name,email,gender,ip_address,cm_height,age,has_car,car_color,grade,grade_avg,identification_card) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;


            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null)
            {
                String[] data = lineText.split(",");    // Change variables and data type

                first_name = data[1];
                last_name = data[2];
                email = data[3];
                gender = data[4];
                ip_address = data[5];
                cm_height = Integer.parseInt(data[6]);
                age = Integer.parseInt(data[7]);
                has_car = data[8];
                car_color = data[9];
                grade = Integer.parseInt(data[10]);
                grade_avg = Double.parseDouble(data[11]);
                identification_card = Integer.parseInt(data[12]);

                statement.setString(1, first_name);
                statement.setString(2, last_name);
                statement.setString(3, email);
                statement.setString(4, gender);
                statement.setString(5, ip_address);
                statement.setInt(6, cm_height);
                statement.setInt(7, age);
                if (!car_color.equals(null))
                    statement.setString(8, "true");
                else
                    statement.setString(8, "false");

                if (has_car.equals("false")){
                    statement.setNull(9, Types.VARCHAR);
                }
                else
                {
                    statement.setString(9, car_color);
                }

                statement.setInt(10, grade);
                statement.setDouble(11, grade_avg);
                statement.setInt(12, identification_card);





                //statement.setString(4, comment);

                statement.addBatch();

                //if (count % batchSize == 0) {
                statement.executeBatch();
                //}
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        String jdbcURL = "jdbc:mysql://localhost:3306/myschema";
        String username = "root";
        String password = "Seniora21";

        String csvFilePath = "highschool_friendships.csv";
        String csvFilePath2 = "highschool.csv";


        //insertValuesToFriendships(jdbcURL, username, password, csvFilePath);
        //insertValuesToHighschool(jdbcURL, username, password, csvFilePath2);
    }
}
