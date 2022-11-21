package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroxadrez.Peca;
import tabuleiroxadrez.Posicao;
import tabuleiroxadrez.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Integer turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configInicial();
	}
	
	public Integer getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
	}
	

	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i< tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				mat [i][j]= (PecaXadrez)tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	public boolean[][] possiveisMovimentos (PosicaoXadrez posicaoInicial){
		Posicao posicao = posicaoInicial.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public PecaXadrez andarPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazMover(origem, destino);
		
		if(testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcepition("Voce nao pode se colocar em xeque.");
		}
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		if(testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
			proximoTurno();
		}		
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca fazMover (Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.aumentaContMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada ) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.diminuirContMovimento();
		tabuleiro.lugarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temPeca(posicao)) {
			throw new XadrezExcepition("Nao existe peca na posicao de origem.");
		}
		if(jogadorAtual	!= ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcepition("Essa peca pertence ao adversario, escolha uma peca que seja sua.");
		}
		if(!tabuleiro.peca(posicao).existAlgumPossivelMovimento()){
			throw new XadrezExcepition("Nao existem movimentos possiveis para a peca escolhida.");

		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcepition("A peca escolhida nao pode se mover para a posicao de destino.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro. (verificar o cod do sistema)");
	}
	
	private boolean testeXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(Cor cor) {
		if(!testeXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean [][] mat = p.possiveisMovimentos();
			for (int i=0; i <tabuleiro.getLinhas(); i++ ) {
				for (int j=0; j <tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicao();
					    Posicao destino = new Posicao(i, j);
					    Peca pecaCapturada = fazMover(origem, destino);
					    boolean testexeque = testeXeque(cor);
					    desfazerMovimento(origem, destino, pecaCapturada);
					    if(!testexeque) {
					    	return false;
					    }
					}
				}	
			}
		}
		return true;
	}
	
	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void configInicial() {
        lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
	}
}
