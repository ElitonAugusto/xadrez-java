package xadrez;

import tabuleiroxadrez.Peca;
import tabuleiroxadrez.Posicao;
import tabuleiroxadrez.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	private int contMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContMovimento() {
		return contMovimento;
	}

	protected void aumentaContMovimento() {
		contMovimento++;
	}
	
	protected void diminuirContMovimento() {
		contMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosição(posicao);
	}
	
	protected boolean temUmaPecaOponente (Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	
}
