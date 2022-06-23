package agenda;

import utilitario.Validador;

public class Endereco {
    private String rua;
    private String bairro; 
    private String cidade;
    private String cep;

    public Endereco(String rua, String bairro, String cidade) throws IllegalArgumentException {
        
        // Valida os dados obrigatórios do endereço e os preenche.
        setRua(rua);
        setBairro(bairro);
        setCidade(cidade);
    }

    public Endereco(String rua, String bairro, String cidade, String cep) throws IllegalArgumentException {
        
        this(rua, bairro, cidade);

        // CEP não é obrigatório, mas se for preenchido deve estar correto!
        setCep(cep);
    }
    
    public String getRua() {
        return this.rua;
    } 

    public String getBairro() {
        return this.bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public String getCep() {
        return this.cep;
    }

    public void setRua(String rua) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(rua)) {
            throw new IllegalArgumentException("Erro: Nome da Rua inválido!");
        }
        this.rua = rua;
    }

    public void setBairro(String bairro) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(bairro)) {
            throw new IllegalArgumentException("Erro: Nome do Bairro inválido!");
        }
        this.bairro = bairro;
    }

    public void setCidade(String cidade) throws IllegalArgumentException {
        if(!Validador.validarIdentificador(cidade)) {
            throw new IllegalArgumentException("Erro: Nome da Cidade inválido!");
        }
        this.cidade = cidade;
    }

    public void setCep(String cep) throws IllegalArgumentException {
        if(cep != null && !Validador.validarCep(cep)) {
            throw new IllegalArgumentException("Erro: CEP informado inválido!");
        }
        this.cep = cep;
    }

    public String toString() {
        String endereco_str = String.format("Rua: %s\nBairro: %s\nCidade: %s\n",
                                            this.rua, this.bairro, this.cidade);
        if(this.cep != null) {
            endereco_str = endereco_str + String.format("CEP: %s\n", this.cep);
        }

        return endereco_str;    
    }
}
