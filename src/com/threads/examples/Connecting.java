package com.threads.examples;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Connecting implements GoodsMethods{

    @Override
    public Item loadItemById(Integer specId){
        Item item = new Item();
        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/homework11","root","ErikNisler");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            while (resultSet.next()) {

                item.setId(resultSet.getInt("id"));
                item.setPartNo(resultSet.getString("partno"));
                item.setSerialNo(resultSet.getString("serialno"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setNumberInStock(resultSet.getInt("number_in_stock"));
                item.setPrice(resultSet.getBigDecimal("price"));

                if (item.getId() == specId) {
                    item.getName();
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Došlo k chybě v databázi! " + e.getLocalizedMessage());
        }
        return item;
    }

    @Override
    public void deleteAllOutOfStockItems() {
            try {
                Connection connection = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/homework11", "root", "ErikNisler");
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM item WHERE number_in_stock = 0");
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                System.err.println("Došlo k chybě v databázi! " + e.getLocalizedMessage());
            }

    }

    @Override
    public List<Item> loadAllAvailableItems(){
        List<Item> itemList = new ArrayList<>();
        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/homework11", "root", "ErikNisler");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item WHERE number_in_stock > 0");

            while (resultSet.next()) {
                Item item = new Item();

                item.setId(resultSet.getInt("id"));
                item.setPartNo(resultSet.getString("partno"));
                item.setSerialNo(resultSet.getString("serialno"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setNumberInStock(resultSet.getInt("number_in_stock"));
                item.setPrice(resultSet.getBigDecimal("price"));

                itemList.add(item);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Došlo k chybě v databázi! " + e.getLocalizedMessage());
        }

        return new ArrayList<>(itemList);
    };


    @Override
    public void saveItem(Item item){
        Item newItem = item;

        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/homework11", "root", "ErikNisler");

            Statement statement = connection.createStatement();

            String query = "INSERT INTO item (partno,serialno,name,description,number_in_stock,price) VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newItem.getPartNo());
            preparedStatement.setString(2, newItem.getSerialNo());
            preparedStatement.setString(3, newItem.getName());
            preparedStatement.setString(4, newItem.getDescription());
            preparedStatement.setInt(5, newItem.getNumberInStock());
            preparedStatement.setBigDecimal(6, newItem.getPrice());

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            System.err.println("Došlo k chybě v databázi! " + e.getLocalizedMessage());
        }
    };

    @Override
    public void updatePrice(Integer specId, BigDecimal newPrice){
        Item item = new Item();

        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/homework11", "root", "ErikNisler");

            Statement statement = connection.createStatement();
            String query = "UPDATE item SET price = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(2,specId);
            preparedStatement.setBigDecimal(1, newPrice);

            preparedStatement.execute();

            connection.close();

        } catch (SQLException e) {
            System.err.println("Došlo k chybě v databázi! " + e.getLocalizedMessage());
        }
    };
}
