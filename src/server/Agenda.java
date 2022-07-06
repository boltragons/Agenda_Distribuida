package server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashSet;

import contato.Contato;

public class Agenda {
     private static final String ARQUIVO_AGENDA = "database/agenda.txt";
     private HashSet<Contato> agenda;

     public Agenda() {
          this.agenda = new HashSet<>();
     }

     public boolean adicionarContato(Contato contato) {

          lerArquivo();

          boolean result = this.agenda.add(contato);

          // escrever arquivo
          salvarArquivo();

          return result;
     }

     public String listarContatos() {
          lerArquivo();

          StringBuilder contatos = new StringBuilder();
          for(Contato cont : agenda) {
               contatos.append(cont.getPrimeiroNome() + "\n");
          }

          salvarArquivo();

          return contatos
          .replace(contatos.length() - 1, contatos.length(), "")
          .toString();
     }

     public void salvarArquivo() {
          try (
               FileOutputStream arquivoAgenda = new FileOutputStream(ARQUIVO_AGENDA);
               ObjectOutputStream agendaStream = new ObjectOutputStream(arquivoAgenda);
          ) {
               for (Contato contato : this.agenda) {
                    agendaStream.writeObject(contato);
               }
          }
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

     private void lerArquivo() {
          try (
               FileInputStream arquivoAgenda = new FileInputStream(ARQUIVO_AGENDA);
               ObjectInputStream agendaStream = new ObjectInputStream(arquivoAgenda);
          ) {
               Contato contatoTemporario;
               while (true) {
                    try {
                         contatoTemporario = (Contato) agendaStream.readObject();
                         this.agenda.add(contatoTemporario);
                    }
                    catch (EOFException e) {
                         break;
                    }
               }
          } 
          // O arquivo não pôde ser encontrado
          catch(FileNotFoundException e) {
               System.out.println("Arquivo não encontrado. Criando arquivo...");
          }
          catch(ClassNotFoundException e) {
              System.out.println("Tentando ler um objeto de uma classe desconhecida.");
          }
          catch(StreamCorruptedException e) { // thrown by the constructor ObjectInputStream
              System.out.println("Formato do arquivo não é válido.");
          }
          // Arquivo sem cabeçalho/vazio
          catch(IOException e) {
               System.out.println("Houve um erro ao abrir o arquivo informado!");
          }
     }

}
