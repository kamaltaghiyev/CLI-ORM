package Service.Impl;

import DBUtils.Provider;
import Models.Profession;
import Service.Services.ProfessionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfessionServiceImpl implements ProfessionService {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Scanner scannerName = new Scanner(System.in);
    @Override
    public void addProfession() throws SQLException {
        System.out.println("Enter Profession name:\n");
        String name = scannerName.nextLine();
        Profession profession = new Profession(name);
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("insert into profession(name) values (?)");
            preparedStatement.setString(1, profession.getProfession());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            connection.close();
            preparedStatement.close();
        }
    }

    @Override
    public void selectAllProfession() throws SQLException {
        try {
            connection = Provider.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM profession");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Profession: " + resultSet.getString("name"));
            }
            System.out.println("\n");
        } catch (Exception e){
            System.out.println(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void updateProfession() throws SQLException {
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE profession SET name = ? WHERE id = ?");
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
    public void removeProfession() throws SQLException {
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM profession WHERE id = ?");
            String id;
            System.out.print("Which profession you would like to DELETE: ");
            id = scannerName.nextLine();
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
