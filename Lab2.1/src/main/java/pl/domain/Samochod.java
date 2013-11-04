package pl.domain;

import java.util.Date;

public class Samochod {
	private Long id;
	private String nazwa;
	private int rokProdukcji;
	private Long idJednorozec;
	
	public Samochod(){
		
	}
	
	public Samochod(String nazwa, int rokProdukcji){
		this.nazwa = nazwa;
		this.rokProdukcji = rokProdukcji;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Long getIdJednorozec() {
		return idJednorozec;
	}

	public void setIdJednorozec(Long idJednorozec) {
		this.idJednorozec = idJednorozec;
	}

	public int getRokProdukcji() {
		return rokProdukcji;
	}

	public void setRokProdukcji(int rokProdukcji) {
		this.rokProdukcji = rokProdukcji;
	}
	
	
}