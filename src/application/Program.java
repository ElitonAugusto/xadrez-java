package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcepition;

public class Program {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		List<PecaXadrez> capturado = new ArrayList<>();
		
		
		while (!partida.getXequeMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partida, capturado);
				System.out.println();
		     	System.out.print("Origem: ");
		     	PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		     	
		     	boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);
		     	System.out.println();
		     	System.out.print("Destino: ");
		     	PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		     	
		     	PecaXadrez pecaCapturada = partida.andarPeca(origem, destino);
		     	
		     	if(pecaCapturada != null) {
		     		capturado.add(pecaCapturada);
		     	}
		     	
		     	if(partida.getPromocao() != null) {
		     		System.out.print("Digite a peca da promocao (A/B/C/T): ");
		     		String tipo = sc.nextLine().toUpperCase();
		     		
		     		while(!tipo.equals("A") && !tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C")) {
		     			System.out.print("Valor invalido! Digite a peça da promocao (A/B/C/T): ");
			     		tipo = sc.nextLine().toUpperCase();
		     		}
		     		partida.substituirPecaPromovida(tipo);
		     	}
			}
			catch(XadrezExcepition e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printPartida(partida, capturado);
	}

}
