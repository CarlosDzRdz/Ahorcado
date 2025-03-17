package DAO.factory;

import java.sql.*;

public class JMysql implements Conn {

    private static JMysql instance = new JMysql();
    private String errMsg;
    private Connection connection;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void conectar() {
        try{
            //Tenemos que registrar si la class existe
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Conectar si existe
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8989/ahorcado", "root", "root");
            boolean valid = connection.isValid(50000);
            System.out.println(valid?"Conexion mysql ok":"Conexion mysql Error");

        } catch (SQLException | ClassNotFoundException sql) {
            setErrMsg(sql.getMessage());
        }
    }

    @Override
    public void Desconectar() {
        try{
            if (!connection.equals(null)) {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexion mysql desconectada");
                }
            }
        }catch (SQLException err){
            setErrMsg(err.getMessage());
        }
    }

    @Override
    public void setErrMsg(String msg) {
        errMsg = msg;
    }

    private JMysql() {
        System.out.println("TEST Mysql okay");
        id = -1;
    }

    public static JMysql getInstance() {
        return instance;
    }

    public String getErrMsg(){
        return errMsg;
    }


    @Override
    public void query(String sql) {

        sql = "SELECT id, Tablero, PalabraAdivinada, Letras, Intentos FROM tablero WHERE id = ?";

        try(PreparedStatement smtp = connection.prepareStatement(sql)) {
            if (id != -1){
                smtp.setInt(1, getId());
                ResultSet rs = smtp.executeQuery();

                while (rs.next()) {
                    int _id = rs.getInt("id");
                    String json  = rs.getString("Tablero");
                    String palabraAdivinada = rs.getString("PalabraAdivinada");
                    String letras = rs.getString("Letras");
                    String intentos = rs.getString("Intentos");

                    System.out.printf("id= %d Tablero%s PalabraAdivinada=%s Letras %s intentos%s", _id, json, palabraAdivinada, letras, intentos);
                }
            }
        } catch (Exception e) {
            setErrMsg(e.getMessage());
        }
    }


    @Override
    public void add(String palabraAdivinada, String letras, int intentos, String tableroJson) {
        String sql = "INSERT INTO tablero (fecha, Tablero, PalabraAdivinada, Letras, Intentos) VALUES (now(), ?, ?, ? ,?)";
        try(PreparedStatement smtp = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            smtp.setString(1, tableroJson);
            smtp.setString(2, palabraAdivinada);
            smtp.setString(3, letras);
            smtp.setInt(4, intentos);
            smtp.executeUpdate();

            ResultSet rs = smtp.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
            setErrMsg(err.getMessage());
        }
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM tablero";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Se eliminaron " + rowsAffected + " registros de la tabla tablero.");
        } catch (SQLException err) {
            System.out.println("Error al borrar los registros: " + err.getMessage());
            setErrMsg(err.getMessage());
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void summary() {
        String sql = "SELECT Fecha, Tablero, PalabraAdivinada, Letras, Intentos FROM tablero ORDER BY id DESC LIMIT 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("\n===== RESUMEN DE LA ÚLTIMA PARTIDA (MySQL) =====");
                System.out.println("Fecha: " + rs.getString("Fecha"));
                System.out.println("Tablero: " + rs.getString("Tablero"));
                System.out.println("PalabraAdivinada: " + rs.getString("PalabraAdivinada"));
                System.out.println("Letras: " + rs.getString("Letras"));
                System.out.println("Intentos: " + rs.getInt("Intentos"));
                System.out.println("=============================================\n");
            }
        } catch (SQLException e) {
            setErrMsg("Error al consultar el resumen: " + e.getMessage());
        }
    }
}