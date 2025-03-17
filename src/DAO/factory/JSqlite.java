package DAO.factory;

import java.sql.*;

public class JSqlite implements Conn {

    private static JSqlite instance = new JSqlite();
    private Connection connection;
    private String errMsg;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private JSqlite() {
        System.out.println("TEST JSqlite okay");
    }

    public static JSqlite getInstance() {
        return instance;
    }

    @Override
    public void conectar() {
        try {
            // Registrar el driver de SQLite
            Class.forName("org.sqlite.JDBC");

            // Conectar a la base de datos SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:ahorcado.db");
            connection.setAutoCommit(false); // Asegurar que debemos confirmar manualmente los cambios

            System.out.println("Conexión a SQLite establecida.");

            // Crear la tabla si no existe
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tablero ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "Tablero TEXT,"
                    + "PalabraAdivinada VARCHAR(100),"
                    + "Letras TEXT,"
                    + "Intentos INTEGER)";
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }

        } catch (SQLException | ClassNotFoundException e) {
            setErrMsg("Error en la conexión SQLite: " + e.getMessage());
        }
    }

    @Override
    public void Desconectar() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Desconexión de SQLite.");
            }
        } catch (SQLException e) {
            setErrMsg("Error al desconectar SQLite: " + e.getMessage());
        }
    }

    @Override
    public void setErrMsg(String msg) {
        this.errMsg = msg;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public void query(String sql) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int _id = rs.getInt("id");
                String json = rs.getString("Tablero");
                String palabraAdivinada = rs.getString("PalabraAdivinada");
                String letras = rs.getString("Letras");
                int intentos = rs.getInt("Intentos");

                System.out.printf("ID=%d, Tablero=%s, Palabra=%s, Letras=%s, Intentos=%d%n",
                        _id, json, palabraAdivinada, letras, intentos);
            }
        } catch (SQLException e) {
            setErrMsg("Error en consulta SQLite: " + e.getMessage());
        }
    }

    @Override
    public void add(String palabraAdivinada, String letras, int intentos, String tableroJson) {
        if (connection == null) {
            setErrMsg("Error: Conexión SQLite no establecida.");
            return;
        }

        String sql = "INSERT INTO tablero (Fecha, Tablero, PalabraAdivinada, Letras, Intentos) VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tableroJson);
            pstmt.setString(2, palabraAdivinada);
            pstmt.setString(3, letras);
            pstmt.setInt(4, intentos);
            pstmt.executeUpdate();

            // Confirmar los cambios
            connection.commit();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            System.out.println("Registro insertado correctamente en SQLite.");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Revertir si hay error
            } catch (SQLException rollbackError) {
                setErrMsg("Error al hacer rollback: " + rollbackError.getMessage());
            }
            setErrMsg("Error al insertar en SQLite: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        if (connection == null) {
            setErrMsg("Error: Conexión SQLite no establecida.");
            return;
        }

        String sql = "DELETE FROM tablero";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            connection.commit(); // Confirmar los cambios
            System.out.println("Se eliminaron " + rowsAffected + " registros de la tabla tablero en SQLite.");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Revertir si hay error
            } catch (SQLException rollbackError) {
                setErrMsg("Error al hacer rollback: " + rollbackError.getMessage());
            }
            setErrMsg("Error al borrar los registros en SQLite: " + e.getMessage());
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
                System.out.println("\n===== RESUMEN DE LA ÚLTIMA PARTIDA (SQLite) =====");
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
