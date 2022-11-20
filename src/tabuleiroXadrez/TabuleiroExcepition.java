package tabuleiroxadrez;

public class TabuleiroExcepition extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TabuleiroExcepition (String msg) {
		super(msg);
	}	
}
