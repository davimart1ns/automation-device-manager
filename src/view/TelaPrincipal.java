package view;

import model.Atuador;
import model.Dispositivo;
import model.Sensor;
import repository.DispositivoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame{

    // componentes de formulario
    private JTextField txtNome, txtIp, txtCampoExtra1, txtCampoExtra2;
    private JCheckBox chkStatus;
    private JComboBox<String> cbTipo;
    private JLabel lblExtra1, lblExtra2;

    // botoes
    private JButton btnSalvar, btnExcluir, btnTestar;

    // tabela e dados
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private DispositivoDAO dao;

    public TelaPrincipal() {
        dao = new DispositivoDAO();

        // configurações da janela
        setTitle("Gerenciador de Dispositivos - Automação & Redes ");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza a janela na tela
        setLayout(new BorderLayout(10, 10));

        // criando os paines de tela
        add(criarFormulario(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);

        // carrega os dados iniciais do SQLite para a tabela
        atualizarTabela();
    }

    // metodo que desenha o formulário na parte superior
    private JPanel criarFormulario() {
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Cadastro / Edição de Dispositivo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // linha 0 -> tipo do dispositivo
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        cbTipo = new JComboBox<>(new String[]{"SENSOR", "ATUADOR"});
        painelForm.add(cbTipo, gbc);

        // linha 1 -> nome
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelForm.add(txtNome, gbc);

        // linha 2 -> endereço IP
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("IP Address:"), gbc);
        gbc.gridx = 1;
        txtIp = new JTextField(20);
        painelForm.add(txtIp, gbc);

        // linha 3 -> status (ativo/inativo)
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        chkStatus = new JCheckBox("Ativo");
        chkStatus.setSelected(true);
        painelForm.add(chkStatus, gbc);

        // linha 4 -> campos dinamicos (mudam conforme o tipo)
        gbc.gridx = 0; gbc.gridy = 4;
        lblExtra1 = new JLabel("Tipo Sinal: ");
        painelForm.add(lblExtra1, gbc);
        gbc.gridx = 1;
        txtCampoExtra1 = new JTextField(20); // armazena TipoSinal ou TipoComando
        painelForm.add(txtCampoExtra1, gbc);

        // linha 5 -> Segundo Campo Dinâmico
        gbc.gridx = 0; gbc.gridy = 5;
        lblExtra2 = new JLabel("Unidade Medida: ");
        painelForm.add(lblExtra2, gbc);
        gbc.gridx = 1;
        txtCampoExtra2 = new JTextField(20);
        painelForm.add(txtCampoExtra2, gbc);

        // linha 6 -> botao salvar
        gbc.gridx = 1; gbc.gridy = 6;
        btnSalvar = new JButton("Salvar Dispositivo");
        painelForm.add(btnSalvar, gbc);


        // evento para mudar os Labels do formulario dinamicamente
        cbTipo.addActionListener(e -> alternarCamposFormulario());

        return painelForm;
    }

    // metodo que desenha a tabela e os botões de ação na parte central
    private JPanel criarPainelCentral() {
        JPanel painelCentral = new JPanel(new BorderLayout(5,5));
        painelCentral.setBorder(BorderFactory.createTitledBorder("Dispositivos Cadastrados"));


        // configurando as colunas da JTable
        String[] colunas = {"ID", "Tipo", "Nome", "IP Address", "Status", "Info Extra 1", "Info Extra 2"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede o usuario de editar clicando direto na tabela
            }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        painelCentral.add(scroll, BorderLayout.CENTER);

        // painel lateral de botões de controle do CRUD
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 5, 5));
        btnTestar = new JButton(" Executar Teste");
        btnExcluir = new JButton("Excluir Selecionado");

        painelBotoes.add(btnTestar);
        painelBotoes.add(btnExcluir);

        // envolve o painel de botões para não ficar esticado
        JPanel containerBotoes = new JPanel(new FlowLayout());
        containerBotoes.add(painelBotoes);
        painelCentral.add(containerBotoes, BorderLayout.EAST);

        return painelCentral;
    }

    // alterna os textos das Labels dependendo do tipo selecionado no ComboBox
    private void alternarCamposFormulario() {
        if("SENSOR".equals(cbTipo.getSelectedItem())) {
            lblExtra1.setText("Tipo Sinal:");
            lblExtra2.setText("Unidade Medida:");
        } else  {
            lblExtra1.setText("Tipo Comando:");
            lblExtra2.setText("Tensão Operação:");
        }
    }

    // atualiza as linhas da tabel buscando os dados do SQLite via DAO
    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // limpa a tabela antes de recarregar
        List<Dispositivo> lista = dao.listarTodos();

        for (Dispositivo d : lista) {
            String tipo = (d instanceof Sensor) ? "SENSOR" : "ATUADOR";
            String extra1 = "";
            String extra2 = "";

            if (d instanceof Sensor) {
                extra1 = ((Sensor) d).getTipoSinal();
                extra2 = ((Sensor) d).getUnidadeMedida();
            } else if (d instanceof Atuador) {
                extra1 = ((Atuador)d).getTipoComando();
                extra2 = ((Atuador)d).getTensaoOperacao();
            }

            modeloTabela.addRow(new Object[]{
                    d.getId(),
                    tipo,
                    d.getNome(),
                    d.getIpAddress(),
                    d.isStatus() ? "Ativo" : "Inativo",
                    extra1,
                    extra2
            });
        }
    }

}
