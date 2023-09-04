/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ignac
 */
public class CustomersDao {
    ConnectionMysql cn = new ConnectionMysql();
    Connection conn;
    PreparedStatement pst; // sirve para la consultas
    ResultSet rs; //sirve para obetener datos de las consultas
    
    public boolean registerCustomerQuery(Customers customer){
        String query = "INSERT INTO customers (id, full_name, telephone, email, created, updated)" 
                + "VALUES (?, ?, ?, ?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3,customer.getTelephone());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.execute();
            
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar al cliente " + e);
            return false;
        }
        
    }
    
    public List listCustomerQuery(String value){
        List<Customers> list_customers = new ArrayList();
        
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "'%"; 
        
        try {
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customers;
    }
    
    // modificar cliente
    public boolean updateCustomerQuery(Customers customer){
        String query = "UPDATE SET full_name = ?,  telephone = ?, email = ?, updated = ? "
            + "WHERE id = ?";
                
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, customer.getFull_name());
            pst.setString(2,customer.getTelephone());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del cliente ");
            return false;
        }
        
    }
    
    // eliminar cliente
    public boolean deleteCustomerQuery(int id){
            String query = "DELETE FROM customers WHERE ID = " + id;
            
            try {
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                pst.execute();
                return true;
            }catch( SQLException e){
                JOptionPane.showMessageDialog(null, "No puede eliminar un cliente que tenga relacion con otra tabla ");
                return false;
            }       
        }
    
    
}
