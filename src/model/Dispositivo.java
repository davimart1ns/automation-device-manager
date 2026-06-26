package model;

public abstract class Dispositivo
{
    private int id;
    private String nome;
    private String ipAddress;
    private boolean status;

    // construtor completo
    public Dispositivo (int id, String nome, String ipAddress, boolean status) {
        this.id = id;
        this.nome = nome;
        this.ipAddress = ipAddress;
        this.status = status;
    }

    // metodo abstrato obrigatorio nas classes filhas
    public abstract String executarTeste();

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
