// Mariana Rosa a22608340
// Martim Pires a22610371
// Tomás Silva a22608276

package highLow.src;

public class Card {
    
    // Atributos que guardam o valor e o naipe da carta
    private int value;
    private String suit;
    
    // Construtor que inicializa o valor e o naipe da carta
    public Card(int value, String suit){
        this.value = value;
        this.suit = suit;
    }
    // Método que retorna o valor da carta
    public int getValue(){
        return value;
    }
    // Método que retorna o naipe da carta
    public String getSuit(){
        return suit;
    }
    // Método que retorna a representação da carta em texto
    @Override
    public String toString(){
        return "Valor: " + value + " Naipe: " + suit;
    }
}