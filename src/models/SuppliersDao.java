
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class SuppliersDao {
    ConnectionMysql cn = new ConnectionMysql();
    Connection conn;
    PreparedStatement pst; // sirve para la consultas
    ResultSet rs; //sirve para obetener datos de las consultas
    
    public boolean registerSupplierQuery(Suppliers supplier){
        String query = "INSERT INTO suppliers (name, description, address, telephone, email, city, created, updated) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar al Proveedor " + e);
            return false;
        }
    }
    
    public List listStuppliersQuery(String value){
        List<Suppliers> list_suppliers = new ArrayList();
        
        String query = "SELECT * FROM suppliers";
        String query_search_suppliers = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "'%";
        
        try {
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else {
                pst = conn.prepareStatement(query_search_suppliers);
                rs = pst.executeQuery();
            }
            while( rs.next() ){
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                list_suppliers.add(supplier);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
    }
        return list_suppliers;
    }
}
