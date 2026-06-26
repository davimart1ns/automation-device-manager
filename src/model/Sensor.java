package model;

public class Sensor extends Dispositivo {
    private String tipoSinal;
    private String unidadeMedida;

    public Sensor(int id, String nome, String ipAddress, boolean status, String tipoSinal, String unidadeMedida) {
        super(id, nome, ipAddress, status);
        this.tipoSinal = tipoSinal;
        this.unidadeMedida = unidadeMedida;
    }

    @Override
    public String executarTeste() {
        return "Testando Sensor [" + getNome() + "] no IP " + getIpAddress() +
                "\nStatus: " + (isStatus() ? "Ativo" : "Inativo") +
                "\nColetando sinal " + tipoSinal + "... Leitura estável em " + unidadeMedida + ".";
    }

    // getters e setters

    public String getTipoSinal() { return tipoSinal; }
    public void setTipoSinal(String tipoSinal) { this.tipoSinal = tipoSinal; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
}
