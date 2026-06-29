package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FabricaConexao {

    // o banco sera salvo em um arquivo chamado "automacao.db" na raiz do projeto
    private static final String URL = "jdbc:sqlite:automacao.db";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // metodo para criar a tabela automaticamente assim que o programa iniciar
    public static void inicializarBanco() {
        // criacao de uma tabela unica usando a estrategia de herança "Single Table"
        String sql = "CREATE TABLE IF NOT EXISTS dispositivos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "ip_address TEXT NOT NULL," +
                "status INTEGER NOT NULL," +  // 0 para inativo, 1 para ativo
                "tipo_dispositivo TEXT NOT NULL," +
                // campos especificos do Sensor
                "tipo_sinal TEXT," +
                "unidade_medida TEXT," +
                // campos especificos do atuador
                "tipo_comando TEXT," +
                "tensao_operacao TEXT" +
                ");";

        try (Connection conn = getConexao(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Banco de dados inicializado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco: " + e.getMessage());
        }
    }
}