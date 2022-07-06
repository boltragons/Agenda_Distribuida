package server;

import org.json.JSONObject;

import contato.Contato;

public class AgendaEsqueleto {
     private Agenda agenda;

	public AgendaEsqueleto() {
		this.agenda = new Agenda();
	}

	public String adicionarContato(String args) {
		JSONObject arguments = new JSONObject(args);
		Contato contato = new Contato((int) arguments.get("id"), 
								(String) arguments.get("primeiroNome"), 
								(String) arguments.get("telefone01"));
		return String.valueOf(this.agenda.adicionarContato(contato));
	}

	public String listarContatos(String args) {
		return this.agenda.listarContatos();
	}
}
