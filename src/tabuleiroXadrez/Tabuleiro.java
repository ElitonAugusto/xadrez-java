package tabuleiroxadrez;

public class Tabuleiro {

	private Integer linhas;
	private Integer colunas;
	private Peca[][] pecas;

	public Tabuleiro() {
	}

	public Tabuleiro(Integer linhas, Integer colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcepition("Erro na criacao do tabuleiro: "
					+ "Crie o tabuleiro com ao menos 1 linha e 1 coluna!");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Integer getLinhas() {
		return linhas;
	}
	public Integer getColunas() {
		return colunas;
	}	

	public Peca peca(int linha, int coluna) {
		if(!existePosicao(linha, coluna)) {
			throw new TabuleiroExcepition("Posicao fora do tabuleiro.");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroExcepition("Posicao fora do tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroExcepition("Ja existe uma peça nessa posicao.");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroExcepition("Posicao fora do tabuleiro.");
		}
		if(peca(posicao) == null) {
			return null;
		}
		else {
			Peca aux = peca(posicao);
			aux.posicao = null;
			pecas[posicao.getLinha()][posicao.getColuna()] = null;
			return aux;
		}
	}

	public boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean existePosicao(Posicao poscisao) {
		return existePosicao(poscisao.getLinha(), poscisao.getColuna());
	}

	public boolean temPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new TabuleiroExcepition("Posicao fora do tabuleiro.");
		}
		return peca(posicao) != null;
	}

}
