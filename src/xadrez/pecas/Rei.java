package xadrez.pecas;

import tabuleiroxadrez.Posicao;
import tabuleiroxadrez.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	private boolean testeRoqueTorre(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// ##Movimento especial: Roque
		if(getContMovimento() == 0 && !partidaDeXadrez.getXeque()) {
			// ##Movimento especial: Roque (Lado do Rei (roque pequeno))
			Posicao pT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if(testeRoqueTorre(pT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if(getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
		// ##Movimento especial: Roque (Lado da Rainha (roque grande))
		Posicao pT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
		if(testeRoqueTorre(pT2)) {
			Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
			Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
			Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
			if(getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null && getTabuleiro().peca(p3)==null	) {
				mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
			}
		}			
	}
		
	

	return mat;
}}
