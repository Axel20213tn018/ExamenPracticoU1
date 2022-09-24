package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPersona {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    private final String INSERT_USER = "INSERT INTO persona (nombre, apellidoP, apellidoM, sexo, estado, fechanac, curp) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_USER = "SELECT * FROM persona WHERE curp = ?";

    public boolean saveUser(BeanPersona user){
        try {
            conn = new MySQLConnection().getConnection();
            String query = INSERT_USER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, user.getNombre());
            pstm.setString(2, user.getApellidoP());
            pstm.setString(3, user.getApellidoM());
            pstm.setString(4, String.valueOf(user.getSexo()));
            pstm.setString(5, user.getEstadoCodigo());
            pstm.setDate(6, user.getFechanac());
            pstm.setString(7, user.getCurp());

            return pstm.executeUpdate()==1; //1==1

        }catch (SQLException e){
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error savePerson -> ", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public BeanPersona findPerson (String curp){
        try{
            conn = new MySQLConnection().getConnection();
            String query = FIND_USER;
            pstm = conn.prepareStatement(query);
            pstm.setString(1, curp);
            rs = pstm.executeQuery();
            while (rs.next()){
                BeanPersona user = new BeanPersona();
                user.setId(rs.getLong("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCurp(rs.getString("curp"));
                user.setApellidoP(rs.getString("apellidoP"));
                user.setApellidoM(rs.getString("apellidoM"));
                user.setSexo(rs.getString("sexo").charAt(0));
                user.setEstadoCodigo(rs.getString("estado"));
                user.setFechanac(rs.getDate("fechanac"));
                user.setCurp(rs.getString("curp"));
                return user;
            }
        }catch (SQLException e){
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error en findPerson -> ", e);
        }finally {
            closeConnections();
        }
        return null;
    }

    public void closeConnections(){
        try{
            if (conn!= null){
                conn.close();
            }
            if (pstm!= null){
                pstm.close();
            }
            if (rs!= null){
                rs.close();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
