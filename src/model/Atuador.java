package model;

public class Atuador extends Dispositivo {
    private String tipoComando;
    private String tensaoOperacao;

    public Atuador(int id, String nome, String ipAddress, boolean status, String tipoComando, String tensaoOperacao) {
        super(id, nome, ipAddress, status);
        this.tipoComando = tipoComando;
        this.tensaoOperacao = tensaoOperacao;
    }

    @Override
    public String executarTeste() {
        return "Testando Atuador [" + getNome() + "] no IP " + getIpAddress() +
                "\nStatus: " + (isStatus() ? "Ativo" : "Inativo") +
                "\nEnviando pulso de comando via " + tipoComando + "... Carga acionada em " + tensaoOperacao + ".";
    }

    // getters e setters
    public String getTipoComando() { return tipoComando; }
    public void setTipoComando(String tipoComando) { this.tipoComando = tipoComando; }

    public String getTensaoOperacao() { return tensaoOperacao; }
    public void setTensaoOperacao(String tensaoOperacao) { this.tensaoOperacao = tensaoOperacao; }
}
