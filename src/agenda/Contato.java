package agenda;

import java.util.Date;
import utilitario.Validador;

public class Contato {
    private final int id;
    private String primeiroNome;
    private String ultimoNome;
    private String telefone01;
    private String telefone02;
    private Date diaAniversario;
    private String email;
    private Endereco enderecoCasa;

    public Contato(int id, String primeiroNome, String telefone01) throws IllegalArgumentException {
        if(id < 0) {
            throw new IllegalArgumentException("Erro: Número de ID inválido!");
        }
        this.id = id;
        setPrimeiroNome(primeiroNome);
        setTelefone01(telefone01);
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    } 

    public String getTelefone01() {
        return this.telefone01;
    } 

    public void setPrimeiroNome(String primeiroNome) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(primeiroNome)) {
            throw new IllegalArgumentException("Erro: Primeiro Nome inválido!");
        }
        this.primeiroNome = primeiroNome;
    }

    public void setTelefone01(String telefone01) throws IllegalArgumentException {
        if(!Validador.validarTelefone(telefone01)) {
            throw new IllegalArgumentException("Erro: Telefone 01 inválido!");
        }
        this.telefone01 = telefone01;
    }

    public String toString() {
        String endereco_str = String.format("ID: %d\nPrimeiro Nome: %s\nTelefone 01: %s\n",
                                            this.id, this.primeiroNome, this.telefone01);

        return endereco_str;    
    }
}