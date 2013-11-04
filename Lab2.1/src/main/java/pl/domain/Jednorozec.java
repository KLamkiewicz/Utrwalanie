package pl.domain;

public class Jednorozec {
	private Long id;
	private String imieJednorozca;
	private int rokUrodzeniaJednorozca;
	private int wagaJednorozca;

	public Jednorozec() {

	}

	public Jednorozec(String imieJednorozca, int rokUrodzeniaJednorozca,
			int wagaJednorozca) {
		super();
		this.imieJednorozca = imieJednorozca;
		this.rokUrodzeniaJednorozca = rokUrodzeniaJednorozca;
		this.wagaJednorozca = wagaJednorozca;
	}

	public String getImieJednorozca() {
		return imieJednorozca;
	}

	public void setImieJednorozca(String imieJednorozca) {
		this.imieJednorozca = imieJednorozca;
	}

	public int getRokUrodzeniaJednorozca() {
		return rokUrodzeniaJednorozca;
	}

	public void setRokUrodzeniaJednorozca(int rokUrodzeniaJednorozca) {
		this.rokUrodzeniaJednorozca = rokUrodzeniaJednorozca;
	}

	public int getWagaJednorozca() {
		return wagaJednorozca;
	}

	public void setWagaJednorozca(int wagaJednorozca) {
		this.wagaJednorozca = wagaJednorozca;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
