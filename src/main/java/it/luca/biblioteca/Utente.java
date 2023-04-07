package it.luca.biblioteca;

import java.time.LocalDate;
/*
 * Rappresenta l'utente della relativa tabella ma non lo uso per ora
 */
public class Utente {
	private Integer id;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	
	public Utente() {
		
	}
	
	public Utente(Integer id, String nome, String cognome, LocalDate dataNascita) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
}
