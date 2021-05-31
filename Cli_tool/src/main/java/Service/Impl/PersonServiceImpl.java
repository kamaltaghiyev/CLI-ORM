package Service.Impl;

import DBUtils.Provider;
import Models.Person;
import Service.Services.PersonService;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PersonServiceImpl implements PersonService {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Scanner scanner = new Scanner(System.in);
    @Override
    public void addPerson() throws SQLException, ParseException {
        System.out.println("Enter person name\n");
        String name = scanner.nextLine();
        if (name.matches("([a-zA-Z]+)")) {
            System.out.println("Enter surname\n");
            String surname = scanner.nextLine();
            if (surname.matches("([a-zA-Z]+)")) {
                System.out.println("Enter birthday(dd.MM.yyyy)");
                String scannedBirthday = scanner.nextLine();
                if (scannedBirthday.matches("[0-9]{2}.[0-9]{2}.[0-9]{4}")) {
                    java.util.Date birthday =  new SimpleDateFormat("dd.MM.yyyy").parse(scannedBirthday);
                    Person person = new Person(name, surname, birthday);
                    try {
                        connection = Provider.getConnection();
                        preparedStatement = connection.prepareStatement("insert into person(name,surname,birthday) values (?,?,?)");
                        preparedStatement.setString(1, person.getName());
                        preparedStatement.setString(2, person.getSurname());
                        java.sql.Date sqlDate = new java.sql.Date(person.getBirthdate().getTime());
                        preparedStatement.setDate(3, sqlDate);
                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {

                        preparedStatement.close();
                    }
                    System.out.println("Enter profession id:");
                    String scannedId = scanner.nextLine();
                    if(scannedId.matches("([1-9]+)")){

                        preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id = (SELECT MAX (id) FROM person)");
                        ResultSet rs = preparedStatement.executeQuery();
                        rs.next();
                        int id = rs.getInt("id");
                        rs.close();
                        preparedStatement = connection.prepareStatement("insert into person_detalies(person_id,profession_id) values (?,?)");
                        preparedStatement.setInt(1, id);
                        int professionId = Integer.parseInt(scannedId);
                        preparedStatement.setInt(2,professionId);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }

                }else System.out.println("Enter birthday properly!");

            }else System.out.println("Enter surname properly!");

        }else System.out.println("Enter name properly!");
    }

    @Override
    public void selectAllPeople() throws SQLException {
        try {
            connection = Provider.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT person.\"id\", person.\"name\", person.surname, person.birthday,profession.\"name\" AS profession FROM person LEFT JOIN person_detalies ON person.id = person_detalies.person_id LEFT JOIN profession ON profession.id = person_detalies.profession_id");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Surname: " + resultSet.getString("surname"));
                System.out.println("Birthday: " + resultSet.getDate("birthday"));
                System.out.println("Profession: " + resultSet.getString("name"));
                System.out.println("----------------------------------------------------------------------------------------");
            }
            System.out.println("\n");
        } catch (Exception e){
            System.out.println(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void updatePerson() throws SQLException {
        System.out.println("Unavailable");
    }

    @Override
    public void removePerson() throws SQLException {
        System.out.println("Unavailable");
    }
}
