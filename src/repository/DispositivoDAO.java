package repository;

import model.Atuador;
import model.Dispositivo;
import model.Sensor;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DispositivoDAO {

    // CREATE
    public void cadastrar(Dispositivo d) {
        String sql = "INSERT INTO dispositivos (nome, ip_address, status, tipo_dispositivo, " +
                "tipo_sinal, unidade_medida, tipo_comando, tensao_operacao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getIpAddress());
            stmt.setInt(3, d.isStatus() ? 1 : 0);

            // verificamos o tipo de objeto para salvar os campos específicos correspondentes
            if (d instanceof Sensor) {
                Sensor s = (Sensor) d;
                stmt.setString(4, "SENSOR");
                stmt.setString(5, s.getTipoSinal());
                stmt.setString(6, s.getUnidadeMedida());
                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.VARCHAR);
            } else if (d instanceof Atuador) {
                Atuador a = (Atuador) d;
                stmt.setString(4, "ATUADOR");
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.VARCHAR);
                stmt.setString(7, a.getTipoComando());
                stmt.setString(8, a.getTensaoOperacao());
            }

            stmt.executeUpdate();
            System.out.println("Dispositivo cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar dispositivo: " + e.getMessage());
        }
    }

    // READ
    public List<Dispositivo> listarTodos() {
        List<Dispositivo> lista = new ArrayList<>();
        String sql = "SELECT * FROM dispositivos";

        try (Connection conn = FabricaConexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String ip = rs.getString("ip_address");
                boolean status = rs.getInt("status") == 1;
                String tipo = rs.getString("tipo_dispositivo");

                if("SENSOR".equals(tipo)) {
                    String tipoSinal = rs.getString("tipo_sinal");
                    String unidade = rs.getString("unidade_medida");
                    lista.add(new Sensor(id, nome, ip, status, tipoSinal, unidade));
                } else if ("ATUADOR".equals(tipo)) {
                    String tipoComando = rs.getString("tipo_comando");
                    String tensao = rs.getString("tensao_operacao");
                    lista.add(new Atuador(id, nome, ip, status, tipoComando, tensao));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar dispositivos: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public void atualizar(Dispositivo d) {
        String sql = "UPDATE dispositivos SET nome = ?, ip_address = ?, status = ?, " +
                    "tipo_sinal = ?, unidade_medida = ?, tipo_comando = ?, tensao_operacao = ? " +
                    "WHERE id = ?";

        try (Connection conn = FabricaConexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getIpAddress());
            stmt.setInt(3, d.isStatus() ? 1 : 0);

            if (d instanceof Sensor) {
                Sensor s = (Sensor) d;
                stmt.setString(4, s.getTipoSinal());
                stmt.setString(5, s.getUnidadeMedida());
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.VARCHAR);
            } else if (d instanceof Atuador) {
                Atuador a = (Atuador) d;
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setString(6, a.getTipoComando());
                stmt.setString(7, a.getTensaoOperacao());
            }

            //  o ID vai para a última posição (índice 9 agora, já que adicionamos o tipo_dispositivo)
            stmt.setInt(8, d.getId());

            stmt.executeUpdate();
            System.out.println("Dispositivo atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o dispositivo: " + e.getMessage());
        }
    }


    // DELETE
    public void excluir(int id) {
        String sql = "DELETE FROM dispositivos WHERE id = ?";

        try (Connection conn = FabricaConexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Dispositivo excluído com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir dispositivo: " + e.getMessage());
        }
    }
}
