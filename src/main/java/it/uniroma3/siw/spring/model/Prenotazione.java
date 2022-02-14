package it.uniroma3.siw.spring.model;








import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

@Entity
@NamedQuery(name = "findAllPrenotazione", query = "SELECT p FROM Prenotazione p")
public class Prenotazione {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private Date dataPrenotazione;

	@Column(nullable=false)
	private int oraPrenotazione;

	@ManyToOne
	private Meccanico meccanico;
	
	@ManyToOne
	private User cliente;
	
	@ManyToOne                              
	private Intervento intervento;
	

	
	public Meccanico getMeccanico() {
		return meccanico;
	}
	
	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(Date dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public int getOraPrenotazione() {
		return oraPrenotazione;
	}
	public void setOraPrenotazione(int oraPrenotazione) {
		this.oraPrenotazione = oraPrenotazione;
	}
	public void setMeccanico(Meccanico meccanico) {
		this.meccanico = meccanico;
	}
	public User getCliente() {
		return cliente;
	}
	public void setCliente(User cliente) {
		this.cliente = cliente;
	}
	public Intervento getIntervento() {
		return intervento;
	}
	public void setIntervento(Intervento intervento) {
		this.intervento = intervento;
	}
	@ModelAttribute("prenotazione.id")
	public long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meccanico == null) ? 0 : meccanico.hashCode());
		result = prime * result + ((dataPrenotazione == null) ? 0 : dataPrenotazione.hashCode());
		result = prime * result + oraPrenotazione;
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
		Prenotazione other = (Prenotazione) obj;
		if (meccanico == null) {
			if (other.meccanico != null)
				return false;
		} else if (!meccanico.equals(other.meccanico))
			return false;
		if (dataPrenotazione == null) {
			if (other.dataPrenotazione != null)
				return false;
		} else if (!dataPrenotazione.equals(other.dataPrenotazione))
			return false;
		if (oraPrenotazione != other.oraPrenotazione)
			return false;
		return true;
	}	
}