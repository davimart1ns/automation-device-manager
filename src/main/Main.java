package main;

import model.Atuador;
import model.Dispositivo;
import model.Sensor;
import repository.DispositivoDAO;
import repository.FabricaConexao;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC"); // Força o carregamento do driver do SQLite
        } catch (ClassNotFoundException e) {
            System.err.println("O driver do SQLite não foi encontrado no projeto! " + e.getMessage());
            return;
        }
        System.out.println("=== INICIANDO TESTE DO SISTEMA ===");

        // inicia o banco de dados e cria a tabela se nao existir
        FabricaConexao.inicializarBanco();

        DispositivoDAO dao = new DispositivoDAO();

        // Criando objetos de teste (ID inicial como 0 porque o SQLite vai incrementar)
        Sensor sensorTemperatura = new Sensor(0, "Sensor Temp Estufa", "192.168.1.50", true, "Analógico", "°C");
        Atuador releBomba = new Atuador(0, "Relé da Bomba Hidráulica", "192.168.1.55",false, "Relé", "220V");

        System.out.println("\n--- Testando Cadastro (Create) ---");
        dao.cadastrar(sensorTemperatura);
        dao.cadastrar(releBomba);

        System.out.println("\n--- Testando Listagem (Read) e Polimorfismo ---");
        List<Dispositivo> lista = dao.listarTodos(); // Uso de Collections

        for (Dispositivo d : lista) {
            System.out.println("-----------------------------------------------");
            System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome());

            // o java decide em tempo de execução qual método rodar baseado no objeto real
            System.out.println(d.executarTeste());
        }
        System.out.println("------------------------------------------------");

        // se a lista nao estiver vazia vamos testar a alteracao e exclusao para fechar o CRUD
        if(!lista.isEmpty()) {
            System.out.println("\n--- Testando Alteracao (UPDATE) ---");
            Dispositivo primeiro = lista.get(0);
            primeiro.setNome(primeiro.getNome() + "[MODIFICADO]");
            dao.atualizar(primeiro);

            System.out.println("\n--- Testando Exclusão (DELETE) ---");
            // vamos excluir o segundo item apenas para testar o comando
            if (lista.size() > 1) {
                int idParaExcluir = lista.get(1).getId();
                dao.excluir(idParaExcluir);
                System.out.println("Dispositivo com ID " + idParaExcluir + " foi removido.");
            }

            System.out.println("\n--- Listagem Final após modificações ---");
            for (Dispositivo d : dao.listarTodos()) {
                System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome());
            }
        }

        System.out.println("\n=== FIM DO TESTE ===");
    }
}
