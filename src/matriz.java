// Source code is decompiled from a .class file using FernFlower decompiler.
class Matriz {
    public static final double SMALL = 1.0E-6;
    private int lin;
    private int col;
    private double[][] m;
 
    public static Matriz identidade(int n) {
       Matriz mat = new Matriz(n, n);
 
       for(int i = 0; i < mat.lin; ++i) {
          mat.m[i][i] = 1.0;
       }
 
       return mat;
    }
 
    public Matriz(int n, int m) {
       this.lin = n;
       this.col = m;
       this.m = new double[this.lin][this.col];
    }
 
    public void set(int i, int j, double valor) {
       this.m[i][j] = valor;
    }
 
    public double get(int i, int j) {
       return this.m[i][j];
    }
 
    public void imprime() {
       for(int i = 0; i < this.lin; ++i) {
          for(int j = 0; j < this.col; ++j) {
             System.out.printf("%7.2f ", this.m[i][j]);
          }
 
          System.out.println();
       }
 
    }
 
    public void imprimeDeterminante() {
       for(int i = 0; i < this.lin; ++i) {
          System.out.print("|");
 
          for(int j = 0; j < this.col; ++j) {
             System.out.printf("%7.2f ", this.m[i][j]);
          }
 
          System.out.print("|");
          System.out.println();
       }
 
    }
 
    public void imprime(Matriz agregada) {
       for(int i = 0; i < this.lin; ++i) {
          int j;
          for(j = 0; j < this.col; ++j) {
             System.out.printf("%7.2f ", this.m[i][j]);
          }
 
          System.out.print(" |");
 
          for(j = 0; j < agregada.col; ++j) {
             System.out.printf("%7.2f ", agregada.m[i][j]);
          }
 
          System.out.println();
       }
 
    }
 
    private void trocaLinha(int i1, int i2) {
       for(int i = 0; i < this.m[0].length; ++i) {
          double aux = this.m[i1 - 1][i];
          this.set(i1 - 1, i, this.m[i2 - 1][i]);
          this.set(i2 - 1, i, aux);
       }
 
    }
 
    private void multiplicaLinha(int i, double k) {
       for(int j = 0; j < this.m[0].length; ++j) {
          double[] var10000 = this.m[i - 1];
          var10000[j] *= k;
       }
 
    }
 
    private void combinaLinhas(int i1, int i2, double k) {
       for(int i = 0; i < this.m[0].length; ++i) {
          this.m[i1][i] += this.m[i2][i] * k;
       }
 
    }
 
    private int[] encontraLinhaPivo(int ini) {
       int pivo_lin = this.lin;
       int pivo_col = this.col;
 
       for(int i = ini; i < this.lin; ++i) {
          int j;
          for(j = 0; j < this.col && !(Math.abs(this.m[i][j]) > 0.0); ++j) {
          }
 
          if (j < pivo_col) {
             pivo_lin = i;
             pivo_col = j;
          }
       }
 
       return new int[]{pivo_lin, pivo_col};
    }
 
    public double cofator(double l, double c) {
       Matriz temp = new Matriz(this.lin - 1, this.col - 1);
       int x = 0;
       int y = 0;
 
       for(int i = 0; i < this.lin; ++i) {
          for(int j = 0; j < this.col; ++j) {
             if ((double)i != l && (double)j != c) {
                temp.set(x, y, this.get(i, j));
                ++y;
                if (y == this.m.length - 1) {
                   y = 0;
                   ++x;
                }
             }
          }
       }
 
       return (double)((int)Math.pow(-1.0, l + c)) * temp.determinante();
    }
 
    public double determinante() {
       double det = 0.0;
       if (this.lin == 1 && this.col == 1) {
          return this.m[0][0];
       } else {
          for(int i = 0; i < this.m.length; ++i) {
             det += this.get(0, i) * this.cofator(0.0, (double)i);
          }
 
          return det;
       }
    }
 
    public double[][] inversa() {
       Matriz temp = new Matriz(this.lin, this.col);
       double[][] aux = new double[this.lin][this.col];
 
       int i;
       int j;
       for(i = 0; i < this.lin; ++i) {
          for(j = 0; j < this.col; ++j) {
             if (i == j) {
                temp.set(i, j, 1.0);
             } else {
                temp.set(i, j, 0.0);
             }
          }
       }
 
       for(i = 0; i < aux.length; ++i) {
          for(j = 0; j < aux[i].length; ++j) {
             for(int k = 0; k < this.m[i].length; ++k) {
                aux[i][j] += this.m[i][k] * temp.get(k, j);
             }
          }
       }
 
       return aux;
    }
 
    public double formaEscalonada(Matriz agregada) {
       this.imprime(agregada);
       System.out.println();
 
       for(int k = 0; k < this.m.length - 1; ++k) {
          this.encontraLinhaPivo(k);
 
          for(int i = k + 1; i < this.m.length; ++i) {
             double fatorMultiplicador = this.get(i, k) / this.get(k, k);
             this.combinaLinhas(i, k, -fatorMultiplicador);
             agregada.combinaLinhas(i, 0, -fatorMultiplicador);
             this.imprime(agregada);
             System.out.println();
          }
       }
 
       double[] res = new double[this.lin];
       res[this.lin - 1] = agregada.get(this.lin - 1, 0) / this.m[this.lin - 1][this.lin - 1];
 
       for(int i = this.lin - 2; i > -1; --i) {
          double sum = 0.0;
 
          for(int j = i + 1; j < this.lin; ++j) {
             sum += this.m[i][j] * res[j];
          }
 
          res[i] = (agregada.get(i, 0) - sum) / this.m[i][i];
       }
 
       return this.determinante();
    }
 
    public void formaEscalonadaReduzida(Matriz agregada) {
       double[][] var10000 = new double[this.lin][this.col * 2];
 
       for(int i = 0; i < this.m.length; ++i) {
          this.encontraLinhaPivo(i);
       }
 
    }
 }
 