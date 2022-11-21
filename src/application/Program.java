package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcepition;

public class Program {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printPartida(partida);
				System.out.println();
		     	System.out.print("Origem: ");
		     	PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		     	
		     	boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);
		     	System.out.println();
		     	System.out.print("Destino: ");
		     	PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		     	
		     	PecaXadrez pcaCapturada = partida.andarPeca(origem, destino);
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
	}

}
