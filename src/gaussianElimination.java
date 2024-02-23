import java.util.*;

import static java.lang.Math.pow;

// classe que representa uma matriz de valores do tipo double.
class Matriz {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private int lin, col;
    private double[][] m;

    // metodo estatico que cria uma matriz identidade de tamanho n x n.
    public static Matriz identidade(int n) {
        Matriz mat = new Matriz(n, n);
        for (int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
        return mat;
    }

    // construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.
    public Matriz(int n, int m) {
        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor) { m[i][j] = valor; }

    public double get(int i, int j) {

        return m[i][j];
    }

    // metodo que imprime as entradas da matriz.
    public void imprime() {
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.println();
        }
    }

    public void imprimeDeterminante() {
        for (int i = 0; i < lin; i++) {
            System.out.print("|");
            for (int j = 0; j < col; j++) {
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
    }

    // metodo que imprime a matriz expandida formada pela combinacao da matriz que
    // chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada
    // linha da matriz impressa possui as entradas da linha correspondente da matriz
    // que chama o metodo, seguida das entradas da linha correspondente em "agregada".
    public void imprime(Matriz agregada) {
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.print(" |");

            for (int j = 0; j < agregada.col; j++) {
                System.out.printf("%7.2f ", agregada.m[i][j]);
            }
            System.out.println();
        }
    }

    // metodo que troca as linhas i1 e i2 de lugar.
    private void trocaLinha(int i1, int i2) {
        for (int i = 0; i < m[0].length; i++) {
            double aux = this.m[i1 - 1][i];
            set(i1 - 1, i, this.m[i2 - 1][i]);
            set(i2 - 1, i, aux);
        }
    }

    // metodo que multiplica as entradas da linha i pelo escalar k
    private void multiplicaLinha(int i, double k) {
        for (int j = 0; j < this.m[0].length; j++) {
            this.m[i - 1][j] *= k;
        }
    }

    // metodo que faz a seguinte combinacao de duas linhas da matriz:
    //
    // 	(linha i1) = (linha i1) + (linha i2 * k)
    //
    private void combinaLinhas(int i1, int i2, double k) {
        for (int i = 0; i < this.m[0].length; i++) {
            this.m[i1][i] = (this.m[i1][i] + (this.m[i2][i] * k));
        }
    }

    // metodo que procura, a partir da linha ini, a linha com uma entrada nao nula que
    // esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da
    // coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
    // Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
    // e eleminacao de Gauss-Jordan.
    private int[] encontraLinhaPivo(int ini) {

        int pivo_col, pivo_lin;

        pivo_lin = lin;
        pivo_col = col;

        for (int i = ini; i < lin; i++) {
            int j;
            for (j = 0; j < col; j++) if (Math.abs(m[i][j]) > 0) break;
            if (j < pivo_col) {

                pivo_lin = i;
                pivo_col = j;
            }
        }

        return new int[]{pivo_lin, pivo_col};
    }

    public double cofator(double l, double c) {
        Matriz temp = new Matriz(this.lin - 1, this.col - 1);
        int x = 0, y = 0;
        for (int i = 0; i < this.lin; i++) {
            for (int j = 0; j < this.col; j++) {
                if (i != l && j != c) {
                    temp.set(x, y, this.get(i, j));
                    y++;
                    if (y == this.m.length - 1) {
                        y = 0;
                        x++;
                    }
                }
            }
        }
        return (int) pow(-1.0, l + c) * temp.determinante();
    }

    public double determinante() {
        double det = 0;
        if (this.lin == 1 && this.col == 1)
            return this.m[0][0];
        else
            for (int i = 0; i < this.m.length; i++) det += this.get(0, i) * cofator(0, i);
        return det;
    }

    public double[][] inversa() {
        Matriz temp = new Matriz(this.lin, this.col);
        double[][] aux = new double[this.lin][this.col];

        for (int i = 0; i < this.lin; i++) {
            for (int j = 0; j < this.col; j++) {
                if (i == j)
                    temp.set(i, j, 1.0);
                else
                    temp.set(i, j, 0.0);
            }
        }

        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux[i].length; j++) {
                for (int k = 0; k < this.m[i].length; k++) {
                    aux[i][j] += this.m[i][k] * temp.get(k, j);
                }
            }
        }

        return aux;
    }

    // metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
    // na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada
    // tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo
    // tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos
    // que a matriz que invoca este metodo eh uma matriz quadrada.
    public double formaEscalonada(Matriz agregada) {
        this.imprime(agregada);
        System.out.println();

        for (int k = 0; k < this.m.length - 1; k++) {
            int[] pivo = encontraLinhaPivo(k);
            for (int i = k + 1; i < this.m.length; i++) {
                double fatorMultiplicador = this.get(i, k) / this.get(k, k);
                this.combinaLinhas(i, k, -fatorMultiplicador);
                agregada.combinaLinhas(i, 0, -fatorMultiplicador);
                this.imprime(agregada);
                System.out.println();
            }
        }

        double[] res = new double[this.lin];

        res[this.lin-1] = agregada.get(this.lin-1, 0)/this.m[this.lin-1][this.lin-1];
        for(int i = this.lin-2; i > -1; i--){
            double sum = 0;
            for(int j = i+1; j < this.lin; j++){
                sum += this.m[i][j] * res[j];
            }
            res[i] = (agregada.get(i, 0) - sum)/this.m[i][i];
        }

        return this.determinante();
    }

    // metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
    // na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada
    // reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
    // a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
    // matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).
    public void formaEscalonadaReduzida(Matriz agregada) {
        double[][] aux = new double[this.lin][this.col * 2];

        for (int i = aux.length-1; i >= 0; i--) {
            for (int k = i + 1; k < aux[i].length; k++) {
                double p = aux[k][i];
                for (int j = 0; j < aux[i].length; j++) {
                    aux[k][j] -= (aux[i][j] * p);
                }
            }
        }

        for(int i = 0; i < this.m.length; i++){
            encontraLinhaPivo(i);

        }
    }
}

public class gaussianElimination {

    public static void main(String [] args){

        Scanner in = new Scanner(System.in);	// Scanner para facilitar a leitura de dados a partir da entrada padrao.
        String operacao = in.nextLine();		// le, usando o scanner, a string que determina qual operacao deve ser realizada.
        int n = in.nextInt();			        // le a dimensão da matriz a ser manipulada pela operacao escolhida.

        Matriz m = new Matriz(n, n);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int nTemp = in.nextInt();
                m.set(i, j, nTemp);
            }
        }

        System.out.println();
        Matriz a = new Matriz(n,1);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 1; j++){
                int nTemp = in.nextInt();
                a.set(i, j, nTemp);
            }
        }

        m.imprime(a);
        System.out.println();

        if("resolve".equals(operacao)){
            double det = m.formaEscalonada(a);
            if(Math.abs(det) - Math.abs(Math.floor(det)) < Matriz.SMALL)
                det = Math.floor(det);
            System.out.println("det(M) = " + det);
        }
        else if("inverte".equals(operacao)){
            m.formaEscalonadaReduzida(a);
        }
        else if("determinante".equals(operacao)){
            m.imprimeDeterminante();
            m.determinante();
            double det = 0;
            if(Math.abs(det) - Math.abs(Math.floor(det)) < Matriz.SMALL)
                det = Math.floor(det);
            System.out.println("det(M) = " + det);
        }
        else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }
}

