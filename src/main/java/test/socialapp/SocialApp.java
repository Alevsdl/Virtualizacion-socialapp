/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package test.socialapp;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ale1
 */
public class SocialApp {

    public static void main(String[] args) throws SQLException {
        Conexion cc = new Conexion();
        Connection cn = cc.getConnection();
        Faker faker = new Faker();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            String address = faker.address().streetAddress();

            System.out.println(name + " " + email + " " + address);

            try {
                PreparedStatement pst = cn.prepareStatement("CALL agregar_user (?,?,?)");
                pst.setString(1, name);
                pst.setString(2, email);
                pst.setString(3, address);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        // Programa la ejecución del código cada segundo
        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
