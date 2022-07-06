package client;

import java.io.IOException;

import contato.Contato;

public class User {

    public static void main(String[] args) throws IOException {

        AgendaProxy p = new AgendaProxy();

        Contato david = new Contato(0, "David", "8888-8888");
        System.out.println(p.adicionarContato(david) ? "David inserido com sucesso!" : "Houve erro ao inserir!");

        Contato pedro = new Contato(1, "Pedro", "9859-5512");
        System.out.println(p.adicionarContato(pedro) ? "Pedro inserido com sucesso!" : "Houve erro ao inserir!");   

        System.out.println(p.listarContatos());

        p.finaliza();
    }
}
