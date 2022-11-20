package xadrez;

import tabuleiroxadrez.Posicao;
import tabuleiroxadrez.TabuleiroExcepition;

public class PosicaoXadrez {
	
	private Character coluna;
	private Integer linha;

	public PosicaoXadrez(Character coluna, Integer linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8 ) {
			throw new TabuleiroExcepition("Erro: Valores validos sao de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public Character getColuna() {
		return coluna;
	}

	public Integer getLinha() {
		return linha;
	}

	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez daPosição(Posicao posicao) {
		return new PosicaoXadrez ((char)('a' - posicao.getColuna()),8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
