package com.example.demo.games;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    /*public static void main(String[] args) {
        getConnection();

        Game game = new Game();

        game.setName("mario");
        game.setDeveloper(" ");
        game.setReleaseDate(" ");
        save(game);
    }*/

    public static Connection getConnection(){

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/employee";
        String user = "postgres";
        String password = "postgres";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save(Game game) {
        int status = 0;
        try {
            Connection connection = GameRepository.getConnection();

            PreparedStatement ps = connection.prepareStatement("insert into games(name,developer,release_date," +
                    "genre, available) values(?,?,?,?,?)");
            ps.setString(1, game.getName());
            ps.setString(2, game.getDeveloper());
            ps.setDate(3,game.getReleaseDate());
            ps.setString(4, game.getGenre());
            ps.setBoolean(5, game.isAvailable());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Game game) {
        int status = 0;
        try {
            Connection connection = GameRepository.getConnection();

            PreparedStatement ps = connection.prepareStatement("update games set name=?, developer=?," +
                    "release_date=?, genre=?, available=? where id=?");
            ps.setString(1, game.getName());
            ps.setString(2, game.getDeveloper());
            ps.setDate(3, game.getReleaseDate());
            ps.setString(4, game.getGenre());
            ps.setBoolean(5, game.isAvailable());
            ps.setInt(6, game.getId());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {
        int status = 0;
        try {
            Connection connection = GameRepository.getConnection();

            PreparedStatement ps = connection.prepareStatement("delete from games where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static Game getGameById(int id) {
        Game game = new Game();
        try {
            Connection connection = GameRepository.getConnection();

            PreparedStatement ps = connection.prepareStatement("select * from games where id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                game.setId(rs.getInt(1));
                game.setName(rs.getString(2));
                game.setDeveloper(rs.getString(3));
                game.setReleaseDate(rs.getDate(4));
                game.setGenre(rs.getString(5));
                game.setAvailable(rs.getBoolean(6));
            }
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return game;
    }

    public static List<Game> getAllGames() {
        List<Game> listGames = new ArrayList<>();
        try {
            Connection connection = GameRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from games");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt(1));
                game.setName(rs.getString(2));
                game.setDeveloper(rs.getString(3));
                game.setReleaseDate(rs.getDate(4));
                game.setGenre(rs.getString(5));
                game.setAvailable(rs.getBoolean(6));
                listGames.add(game);
            }
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return listGames;
    }
}
