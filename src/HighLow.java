// Mariana Rosa a22608340
// Martim Pires a22610371
// Tomás Silva a22608276

package highLow.src;

import java.util.ArrayList;
import java.util.Scanner;

public class HighLow {

    // Método static que valida uma opção introduzida pelo utilizador
    private static String readOption(Scanner scanner, String message, String... options) {

        System.out.print(message);
        String choice = scanner.nextLine().trim().toUpperCase();

        // Repetir enquanto a resposta não for válida
        while (true) {
        for (String option : options) {
            if (choice.equals(option)) {
                return choice;
            }
        }

        System.out.print("Escolha inválida. Tenta novamente: ");
        choice = scanner.nextLine().trim().toUpperCase();
        }
    }

    // Método static que verifica se a previsão do jogador está correta
    private static boolean isCorrect(String choice, int currentValue, int nextValue) {
    return (choice.equals("H") && nextValue > currentValue) || (choice.equals("L") && nextValue < currentValue);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();

        // Variáveis que guardam os resultados gerais do torneio
        int roundsPlayed = 0;
        int totalPoints = 0;
        int correctGuesses = 0;
        int playerIndex = 0;
        boolean continuePlaying = true;

        System.out.println("Bem-vindo ao torneio HighLow!");
        System.out.println("O objetivo é adivinhar se a próxima carta é mais alta (H) ou mais baixa (L).");

        // Registar os jogadores antes de começar o torneio
        System.out.print("Quantos jogadores vão participar? ");
        int numberOfPlayers = 0;

        // Validar a entrada do número de jogadores
        while (numberOfPlayers <= 0) {
            if (!scanner.hasNext()) {
                System.out.println("\nEntrada terminada. Registo cancelado.");
                scanner.close();
                return;
            }
            // Verificar se a resposta é um número inteiro
            if (scanner.hasNextInt()){
                numberOfPlayers = scanner.nextInt();
            } else {
                scanner.next();
            }
            // Impedir a introdução de zero ou números negativos
            if (numberOfPlayers <= 0) {
                System.out.print("Número inválido. Insere um número maior que zero: ");
            }
        }
        scanner.nextLine();

        // Criar e registar os jogadores
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("\nJOGADOR " + (i + 1));
            System.out.print("Nome: ");
            String name = scanner.nextLine().trim();

            while (name.isEmpty()) {
                System.out.print("O nome não pode estar vazio. Nome: ");
                name = scanner.nextLine().trim();
            }
            // Escolhe o tipo de jogador
            String type = readOption(scanner, "Tipo de jogador - Normal (N) ou Especial (E): ", "N", "E");
            if (type.equals("N")) {
                players.add(new NormalPlayer(name));
            } else {
                players.add(new SpecialPlayer(name));
            }
        }

        // O torneio continua enquanto o utilizador quiser jogar
        while (continuePlaying) {

            // Obter o jogador atual através do seu índice na lista
            Player player = players.get(playerIndex);
            Deck deck = new Deck(); // Cria e baralha um novo baralho em cada ronda
            Card currentCard = deck.dealCard();
            int pointsRound = 0;
            boolean roundActive = true;
            roundsPlayed++;

            System.out.println("\n-----------------------------");
            System.out.println("RONDA " + roundsPlayed);
            player.startRound();

            // Continuar a ronda enquanto o jogador acertar e existirem cartas
            while (roundActive && deck.cardsLeft() > 0) {
                System.out.println("\nCarta atual: " + currentCard);
                // Pedir ao jogador para prever se a próxima carta será maior ou menor
                String choice = readOption(scanner, "Próxima carta é mais alta (H) ou mais baixa (L)? ", "H", "L");

                Card nextCard = deck.dealCard();
                System.out.println("Carta seguinte: " + nextCard);

                // Compara os valores das duas cartas
                int currentValue = currentCard.getValue();
                int nextValue = nextCard.getValue();

                // Se as cartas forem iguais, a ronda termina para todos os jogadores
                if (nextValue == currentValue) {
                    System.out.println("As cartas têm o mesmo valor! A ronda terminou.");
                    roundActive = false;
                // Se a previsão estiver correta, a ronda continua; caso contrário, verifica se o jogador pode tentar novamente
                } else if (isCorrect(choice, currentValue, nextValue)) {
                    pointsRound++;
                    correctGuesses++;
                    System.out.println("Acertaste! Pontuação da ronda: " + pointsRound);
                    currentCard = nextCard;
                } else {
                    System.out.println("Erraste a previsão!");

                    // O jogador especial pode tentar outra vez, uma vez por ronda
                    if (player.canRetry()) {
                        System.out.println("Tens direito a uma segunda previsão para a mesma carta.");
                        String secondChoice = readOption(scanner, "Mais alta (H) ou mais baixa (L)? ", "H", "L");

                    // Verificar se a segunda previsão está correta
                    if (isCorrect(secondChoice, currentValue, nextValue)) {
                        pointsRound++;
                        correctGuesses++;
                        System.out.println("Acertaste na segunda previsão!");
                        currentCard = nextCard;
                        } else {
                            System.out.println("Erraste novamente! A ronda terminou.");
                            roundActive = false;
                        }
                    } else {
                        System.out.println("A ronda terminou.");
                        roundActive = false;
                    }
                }
            }

            // Guardar os resultados da ronda do jogador
            player.registerRound(pointsRound);
            totalPoints += pointsRound;

            System.out.println("\nRESULTADO DA RONDA");
            System.out.println("Jogador: " + player.getName());
            System.out.println("Pontuação da ronda: " + pointsRound);
            System.out.println("Pontuação acumulada: " + player.getTotalScore());
            System.out.println("Rondas do jogador: " + player.getRoundNumber());

            // Perguntar se o torneio deve continuar
            String answer = readOption(scanner, "\nQueres continuar o torneio? (S/N): ", "S", "N");
            continuePlaying = answer.equals("S");

            // Passar ao jogador seguinte e voltar ao primeiro no fim da lista
            playerIndex++;
            if (playerIndex >= players.size()) {
                playerIndex = 0;
            }
        }

        // Apresentar o relatório final de todos os jogadores
        System.out.println("\n========== RESULTADO FINAL DO TORNEIO ==========");
        int bestTotalScore = 0;

        for (Player player : players) {
            System.out.println("\nJogador: " + player.getName());
            System.out.println("Pontuação acumulada: " + player.getTotalScore());
            System.out.println("Rondas realizadas: " + player.getRoundNumber());
            System.out.printf("Média de pontos por ronda: %.2f%n", player.calculateAverage());
            System.out.println("Melhor pontuação numa ronda: " + player.getBestScore());
            System.out.println("Histórico de pontuações: " + player.getScoreHistory());

            if (player.getTotalScore() > bestTotalScore) {
                bestTotalScore = player.getTotalScore();
            }
        }

        // Estatísticas globais
        System.out.println("\nESTATÍSTICAS DO TORNEIO");
        System.out.println("Jogadores inscritos: " + players.size());
        System.out.println("Rondas realizadas: " + roundsPlayed);
        System.out.println("Previsões corretas: " + correctGuesses);
        System.out.println("Pontos atribuídos: " + totalPoints);

        // Mostrar todos os jogadores com a maior pontuação
        System.out.println("\nVENCEDOR(ES) DO TORNEIO");
        for (Player player : players) {
            if (player.getTotalScore() == bestTotalScore) {
                System.out.println(player.getName() + " - " + player.getTotalScore() + " pontos");
            }
        }
        scanner.close();
    }
}
