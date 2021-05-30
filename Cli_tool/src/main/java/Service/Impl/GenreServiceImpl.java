package Service.Impl;

import DBUtils.Provider;
import Models.Genre;
import Service.Services.GenreService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GenreServiceImpl implements GenreService {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Scanner scannerName = new Scanner(System.in);
    @Override
    public void addGenre() throws SQLException {
        System.out.println("Enter genre name:\n");
        String name = scannerName.nextLine();
        Genre genre = new Genre(name);
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("insert into genre(name) values (?)");
            preparedStatement.setString(1,genre.getName());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            connection.close();
            preparedStatement.close();
        }
    }

    @Override
    public void selectAllGenres() throws SQLException {
        try {
            connection = Provider.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM genre");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
            }
            System.out.println("\n");
        } catch (Exception e){
            System.out.println(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void updateGenre() throws SQLException {
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE genre SET name = ? WHERE id = ?");
            String forUpdate, id;
            System.out.print("Which genre you would like to UPDATE: ");
            id = scannerName.nextLine();
            System.out.print("Would you like to update 'name' field [Y/N]: ");
            forUpdate = scannerName.nextLine();
            if (forUpdate.equals("N"))
                return;
            System.out.print("Enter new name: ");
            forUpdate = scannerName.nextLine();
            preparedStatement.setString(1, forUpdate);
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            connection.close();
            preparedStatement.close();
        }
    }

    @Override
    public void removeGenre() throws SQLException {
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM genre WHERE id = ?");
            String id;
            System.out.print("Which genre you would like to DELETE: ");
            id = scannerName.nextLine();
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM movie_genre_relation WHERE genre_id = ?");
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
