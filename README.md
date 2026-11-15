# HighLow

Segundo miniprojeto da unidade curricular de Programação Orientada a Objetos em Java.

## Autores

- Mariana Rosa — a22608340
- Martim Pires — a22610371
- Tomás Silva — a22608276

## Descrição

O HighLow é um jogo de cartas em consola que utiliza um baralho de 52 cartas.

O jogador deve adivinhar se a próxima carta terá um valor superior ou inferior ao da carta atual. Cada resposta correta atribui um ponto. Uma resposta errada ou uma carta com o mesmo valor termina a ronda.

No final de cada ronda, o jogador pode escolher continuar, desde que existam cartas suficientes. Quando o jogo termina, são apresentados o número de rondas, a pontuação total e a pontuação média por ronda.

Esta versão organiza o jogo num torneio com vários participantes. Cada jogador possui um perfil, normal ou especial, e mantém os seus resultados entre rondas. No final, são apresentados os resultados individuais, as estatísticas globais e todos os vencedores em caso de empate.

## Regras do torneio

1. Antes de começar, é indicado o número de jogadores e são registados o nome e o perfil de cada participante.
2. Os jogadores participam pela ordem de inscrição. Cada ronda pertence a um único jogador.
3. No início de cada ronda, é criado um novo baralho de 52 cartas, que é baralhado pelo seu construtor. É retirada uma carta de referência.
4. Antes de retirar a carta seguinte, o jogador escolhe `H` para prever um valor mais alto ou `L` para prever um valor mais baixo.
5. Cada previsão correta atribui um ponto. A carta revelada passa a ser a referência da próxima jogada.
6. Quando duas cartas têm o mesmo valor, a ronda termina para qualquer perfil, sem atribuir um ponto por essa previsão.
7. Uma previsão errada termina a ronda, exceto quando o jogador especial ainda pode utilizar a sua segunda oportunidade.
8. A ronda também termina quando não existem mais cartas no baralho.
9. Depois do resultado da ronda, é perguntado se o torneio deve continuar: `S` para continuar ou `N` para terminar.
10. Se continuar, joga o participante seguinte. Depois do último inscrito, a sequência recomeça no primeiro, sem apagar os resultados acumulados.

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

### Player

É uma classe abstrata que concentra os dados e as operações comuns aos dois perfis de jogador.

Os atributos privados guardam o nome (`name`), a pontuação acumulada (`totalScore`), o número de rondas registadas (`roundsPlayed`) e o histórico das pontuações (`scoreHistory`).

- `registerRound(int points)`: soma os pontos ao total, incrementa as rondas do jogador e guarda a pontuação no histórico.
- `calculateAverage()`: calcula a média com divisão em `double`; devolve zero quando o jogador ainda não tem rondas registadas.
- `getBestScore()`: percorre o histórico e devolve a maior pontuação de uma ronda, ou zero se o histórico estiver vazio.
- `getScoreHistory()`: devolve uma cópia da lista, impedindo que uma alteração à lista recebida modifique o histórico interno.
- `startRound()`: apresenta a mensagem de início da ronda do jogador.
- `canRetry()`: método abstrato cuja implementação depende do perfil.

A classe não pode ser instanciada diretamente. Os participantes são criados como `NormalPlayer` ou `SpecialPlayer`.

### NormalPlayer

Herda os dados e os métodos de `Player`. O construtor chama `super(name)` e a implementação de `canRetry()` devolve sempre `false`, impedindo uma segunda previsão após uma falha.

### SpecialPlayer

Herda de `Player` e acrescenta o atributo privado `retryUsed`.

O método `canRetry()` verifica e consome a oportunidade: se ainda não foi utilizada, marca-a como usada e devolve `true`; caso contrário, devolve `false`.

O método `startRound()` é sobrescrito para chamar a implementação da superclasse e repor `retryUsed` a `false`. Assim, a oportunidade volta a estar disponível na ronda seguinte do mesmo jogador.

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

### HighLow

Contém o método `main()` e controla a execução do jogo.

Os jogadores são guardados num `ArrayList<Player>`. O índice `playerIndex` identifica o participante atual, avança depois de cada ronda e regressa a zero quando chega ao fim da lista.

A classe inclui dois métodos auxiliares principais:

- `readOption(Scanner scanner, String message, String... options)`: apresenta uma pergunta, lê a resposta e repete enquanto não corresponder a uma das opções permitidas.
- `isCorrect(...)`: verifica se uma previsão corresponde à relação entre os valores das cartas, através de duas versões sobrecarregadas.

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

## Inteligência Artificial

Utilizamos IA para consultar sintaxe sobre o `trim(), nextline(), hasnextline(), equals()` e para formatar o README.md.