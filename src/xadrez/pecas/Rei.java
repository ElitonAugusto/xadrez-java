package xadrez.pecas;

import tabuleiroxadrez.Tabuleiro;
import xadrez.PecaXadrez;
import xadrez.Cor;

public class Rei extends PecaXadrez {

	
	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}
}
