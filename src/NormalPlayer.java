// Mariana Rosa a22608340
// Martim Pires a22610371
// Tomás Silva a22608276

package highLow.src;

public class NormalPlayer extends Player {
    
    // Construtor que inicializa o jogador através da superclasse Player
    public NormalPlayer(String name){
        super(name);
    }

    // O jogador normal não tem oportunidade de fazer uma segunda previsão
    @Override
    public boolean canRetry(){
        return false;
    }
}