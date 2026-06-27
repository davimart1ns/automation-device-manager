package main;

import model.Atuador;
import model.Dispositivo;
import model.Sensor;
import repository.DispositivoDAO;
import repository.FabricaConexao;
import view.TelaPrincipal;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // inicializa o banco de dados antes da interface abrir
        FabricaConexao.inicializarBanco();

        // executa a janela Swing na Thread correta de eventos do Java
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true); // torna a janela visível
        });
    }
}
