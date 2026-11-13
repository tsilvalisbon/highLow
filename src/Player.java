// Mariana Rosa a22608340
// Martim Pires a22610371
// Tomás Silva a22608276

package highLow.src;

import java.util.ArrayList;

public abstract class Player {
    
    private String name;
    private int totalScore;
    private int roundsPlayed;
    private ArrayList<Integer> scoreHistory;

    // Construtor para inicializar os dados do jogador
    public Player(String name){
        this.name = name;
        this.totalScore = 0;
        this.roundsPlayed = 0;
        this.scoreHistory = new ArrayList<>();
    }

    // Devolve o nome
    public String getName(){
        return name;
    }

    // Devolve a pontuação toltal
    public int getTotalScore(){
        return totalScore;
    }

    // Devolve o número de rondas jogadas
    public int getRoundNumber(){
        return roundsPlayed;
    }

    // Devolve o histórico de pontuações 
    public ArrayList<Integer> getScoreHistory() {
        return scoreHistory;
    }

    // Regista a pontuação de cada ronda
    public void registerRound(int points) {
        totalScore += points;
        roundsPlayed++;
        scoreHistory.add(points);
    }

    // Calcula a média das pontuações por ronda
    public double calculateAverage() {
        if (roundsPlayed == 0) {
            return 0;
        }

    return (double) totalScore / roundsPlayed;
    }

    // Verifica e devolve qual foi a melhor pontuação
    public int getBestScore() {
        int best = 0;

        // Verifica todas as pontuações guardadas
        for (int points : scoreHistory) {
            // Se houver uma pontuação maior, o valor atualiza
            if (points > best) {
                best = points;
            }
        }
        return best;
    }

    // Método para iniciar uma ronda
    public abstract void startRound();

    // Método para indicar se o jogador pode ter mais uma tentativa
    public abstract boolean canRetry();

}