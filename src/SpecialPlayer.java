// Mariana Rosa a22608340
// Martim Pires a22610371
// Tomás Silva a22608276

package highLow.src;

public class SpecialPlayer extends Player {
    
    private boolean retryUsed;

    // Construtor da classe que chama o construtor da superclasse Player
    public SpecialPlayer(String name){
        super(name);
        this.retryUsed = false; // Inicialmente a segunda previsão não foi utilizada
    }

    // Define o início da ronda
    @Override
    public void startround(){
        System.out.println(getName() + " começou a ronda.");
    }

    // Verfica se o jogador ainda pode usar a segunda previsão
    @Override
    public boolean canRetry(){
        // Caso ainda não tenha usado, pode repetir a previsão
        if (!retryUsed) {
            retryUsed = true; // Segunda previsão utilizada
            return true;
        }
        // Se já usou, não pode prever outra vez
        return false;
    }
}