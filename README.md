# HighLow

Primeiro miniprojeto da unidade curricular de Programação Orientada a Objetos em Java.

## Autores

- Mariana Rosa — a22608340
- Martim Pires — a22610371
- Tomás Silva — a22608276

## Descrição

O HighLow é um jogo de cartas em consola que utiliza um baralho de 52 cartas.

O jogador deve adivinhar se a próxima carta terá um valor superior ou inferior ao da carta atual. Cada resposta correta atribui um ponto. Uma resposta errada ou uma carta com o mesmo valor termina a ronda.

No final de cada ronda, o jogador pode escolher continuar, desde que existam cartas suficientes. Quando o jogo termina, são apresentados o número de rondas, a pontuação total e a pontuação média por ronda.

## Regras do jogo

1. O baralho é criado e baralhado no início do programa.
2. É retirada uma carta e apresentada ao jogador.
3. O jogador escolhe:
   - `H` — a próxima carta terá um valor mais alto;
   - `L` — a próxima carta terá um valor mais baixo.
4. A próxima carta é retirada e apresentada.
5. Se a previsão estiver correta, o jogador ganha um ponto e a carta revelada passa a ser a carta atual.
6. Se a previsão estiver errada ou as cartas tiverem o mesmo valor, a ronda termina.
7. No final da ronda, o jogador escolhe:
   - `S` — jogar outra ronda;
   - `N` — terminar o jogo.
8. O jogo também termina quando não existem pelo menos duas cartas para iniciar uma nova ronda.

O mesmo baralho é utilizado ao longo das várias rondas. As cartas já distribuídas não voltam a ser utilizadas e o baralho não é novamente baralhado entre rondas.

### Card.java

Representa uma carta individual através de dois atributos privados:

- `value`: valor numérico da carta;
- `suit`: naipe da carta.

O construtor recebe o valor e o naipe. Os métodos `getValue()` e `getSuit()` permitem consultar esses atributos.

O método `toString()` devolve uma representação textual da carta, por exemplo:

```text
Valor da carta: 10 Naipe: Copas
```

Os atributos são privados para impedir a sua alteração direta por outras classes.

### Deck.java

Representa o baralho e cria as cartas disponíveis.

Utiliza os seguintes atributos privados:

- `cards`: array com as 52 cartas;
- `cardsLeft`: número de cartas ainda disponíveis.

#### Construção do baralho

O construtor cria o array, chama `createDeck()` para preencher as suas posições e chama `shuffle()` para baralhar as cartas.

O método privado `createDeck()` utiliza dois ciclos `while`:

- o ciclo exterior percorre os quatro naipes: Espadas, Copas, Paus e Ouros;
- o ciclo interior cria as cartas com valores de 1 a 13 para cada naipe.

São criadas 52 combinações diferentes de valor e naipe.

Nesta implementação, as comparações utilizam diretamente os valores numéricos:

- Ás: 1;
- cartas de 2 a 10: respetivo valor;
- Valete: 11;
- Dama: 12;
- Rei: 13.

O Ás é considerado a carta de menor valor. Os naipes não influenciam as comparações.

#### Baralhamento

O método `shuffle()` percorre o array desde a última posição até à segunda.

Em cada passagem, escolhe uma posição aleatória entre zero e a posição atual:

```java
int randomIndex = (int) (Math.random() * (cardIndex + 1));
```

A carta atual é trocada com a carta da posição escolhida, utilizando uma variável temporária.

Este procedimento corresponde ao algoritmo de baralhamento Fisher–Yates. No final, `cardsLeft` é reposto para o tamanho do array.

#### Distribuição das cartas

O método `dealCard()` verifica se ainda existem cartas:

- se o baralho estiver vazio, devolve `null`;
- caso contrário, decrementa `cardsLeft` e devolve a carta dessa posição.

As cartas são distribuídas desde o fim do array até ao início. Não são removidas fisicamente do array: o contador indica quais ainda estão disponíveis.

O método `getCardsLeft()` permite consultar o número de cartas restantes.

### HighLow.java

Contém o método `main()` e controla a execução do jogo.

É responsável por:

- criar o `Scanner` e o baralho;
- apresentar as instruções;
- ler e validar as respostas;
- controlar as rondas e as jogadas;
- comparar os valores das cartas;
- atualizar as pontuações;
- perguntar se o jogador pretende continuar;
- apresentar os resultados finais.

## Controlo das rondas

A implementação utiliza dois ciclos principais:

- um ciclo exterior que controla a realização de novas rondas;
- um ciclo interior que mantém a ronda ativa enquanto o jogador acertar e existirem cartas.

No início de cada ronda, a pontuação dessa ronda é inicializada a zero e é retirada uma carta inicial.

Após cada previsão correta, a pontuação aumenta e a carta revelada passa a ser a referência para a próxima jogada.

No final da ronda, os pontos obtidos são adicionados à pontuação total.

## Validação das respostas

As respostas são lidas com `Scanner`.

Antes da validação, são utilizados:

- `trim()`, para remover espaços no início e no fim;
- `toUpperCase()`, para converter a resposta para maiúsculas.

Desta forma, respostas como `a`, `A` e ` A ` são interpretadas da mesma maneira.

As previsões só aceitam `A` ou `B`. A decisão de continuar só aceita `S` ou `N`. Quando uma resposta não pertence às opções permitidas, o programa apresenta uma mensagem e volta a pedir uma resposta.

O código também utiliza `hasNextLine()` antes das leituras para detetar o fim da entrada e evitar chamar `nextLine()` quando não existe uma linha disponível.

## Pontuação e resultados

O programa mantém três contadores:

- `roundsPlayed`: número de rondas iniciadas
- `pointsRound`: pontos obtidos na ronda atual
- `totalPoints`: soma dos pontos das rondas

A média é calculada dividindo a pontuação total pelo número de rondas:

```java
averagePoints = (float) totalPoints / roundsPlayed;
```

A conversão para `float` permite obter um resultado com casas decimais.

No final, são apresentados:

- número de rondas jogadas;
- pontuação total;
- pontuação média por ronda, formatada com duas casas decimais.

## Compilação e execução

É necessário ter um JDK instalado.

```text
highLow/
├── README.md
└── src/
    ├── Card.java
    ├── Deck.java
    └── HighLow.java
```

A partir da pasta `highLow`, compilar com:

```bash
javac src/*.java
```
Executar com:
```bash
java highLow.src.HighLow
```

## Modelo de Dominio
<img width="826" height="534" alt="Modelo de Dominio" src="https://github.com/user-attachments/assets/f3448312-6e3d-4391-a442-0e97949e47b7" />

## Inteligência Artificial

Utilizamos IA para consultar sintaxe sobre o `trim(), nextline(), hasnextline(), equals()` e para formatar o README.md.