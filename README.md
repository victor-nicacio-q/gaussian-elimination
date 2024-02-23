***Codigo em Java que implementa o método de eliminação de Gauss para se encontrar a solução do sistema.**

A eliminação gaussiana e a eliminação de Gauss-Jordan são dois processos que transformam uma matriz a partir de três operações elementares:
  * Troca da posição de duas linhas.
  * Multiplicação de uma linha por um valor escalar.
  * Soma de uma linha por outra multiplicada por um escalar, com substituição da primeira linha pelo resultado.

A eliminação gaussiana tem por objetivo colocar a matriz na chamada **forma escalonada**.
Segundo, nesta forma a matriz apresenta as seguintes propriedades:
  * A entrada não nula mais à esquerda de cada linha possui o valor igual a 1 (valor chamado de pivô da linha).
  * O pivô da linha inferior ocorre sempre à direita do pivô da linha superior.
  * Linhas nulas são agrupadas na porção inferior da matriz

Uma consequência direta destas propriedades é que todas as entradas de uma coluna
abaixo do pivô terão valor igual a zero. Além disso, se a matriz em questão for quadrada e
não houver nenhuma linha nula nesta matriz, a matriz será uma matriz triangular superior, e
todos os pivôs estarão localizados na diagonal principal da matriz.
A eliminação de Gauss-Jordan dá continuidade ao processo iniciado pela eliminação
gaussiana colocando a matriz na chamada forma escalonada reduzida.

Nesta outra forma, além das propriedades já listadas, também vale a seguinte propriedade:
  * Com exceção do pivô, todas as entradas de uma coluna possuem valor igual a zero (ou seja, tanto as entradas de acima quanto as abaixo do pivô são constituídas de zeros).

Observe ainda que, quando a matriz na forma escalonada reduzida for quadrada, sem a
presença de linhas nulas, tal matriz corresponderá à matriz identidade.


Dentre as aplicações estudadas da eliminação gaussiana e/ou a eliminação de
Gauss-Jordan podemos citar: a resolução de sistemas de equações lineares, a inversão de
matrizes e o cálculo de determinantes.
