package it.uniroma3.siw.spring.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQuery(name = "findAllIntervento", query = "SELECT a FROM Intervento a")
public class Intervento {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String descrizione;
	
	@ManyToOne
	private Meccanico meccanico;
	
	@ManyToOne
	private TipologiaIntervento tipologiaIntervento;
	
	@OneToMany(mappedBy="intervento")
	private List<Prenotazione> prenotazioni;
	
	public Intervento(){
		
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Meccanico getMeccanico() {
		return meccanico;
	}

	public void setMeccanico(Meccanico meccanico) {
		this.meccanico = meccanico;
	}

	public TipologiaIntervento getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(TipologiaIntervento tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervento other = (Intervento) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return false;
	}

}