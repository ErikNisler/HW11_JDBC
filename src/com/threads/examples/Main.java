package com.threads.examples;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Connecting connecting = new Connecting();

        /**1*/
        System.out.println(connecting.loadItemById(2).toString());
        /**2*/
        connecting.deleteAllOutOfStockItems();
        /**3*/
        System.out.println(connecting.loadAllAvailableItems());
        /**4*/
        connecting.saveItem(new Item("1239","999","Nůžky","na textil",2,BigDecimal.valueOf(42.9)));
        /**5*/
        connecting.updatePrice(3,BigDecimal.valueOf(50.3));
    }
}
