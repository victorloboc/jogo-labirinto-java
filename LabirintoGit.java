
public class LabirintoGit {
    private static final char PAREDE_VERTICAL = '|';
    private static final char PAREDE_HORIZONTAL = '-';
    private static final char VAZIO = ' ';
    private static final char TAMANHO = 10;
    private static char[][] tabuleiro;
    private static final char PAREDE_INTERNA = '@';
    private static final double PROBABILIDADE = 0.7;
    private static final char INICIO = 'I';
    private static final char DESTINO = 'D';
    private static int linhainicio;
    private static int colunainicio;
    private static int linhadestino;
    private static int colunadestino;
    private static char CAMINHO = '.';
    private static char SEM_SAIDA = 'X';
    
    public static void inicializarMatriz(){
        //preenchimento da matriz com as paredes
        for(int i=0; i<TAMANHO;i++){
            tabuleiro[i][0]=PAREDE_VERTICAL;
            tabuleiro[i][TAMANHO-1]=PAREDE_VERTICAL;
            tabuleiro[0][i]=PAREDE_HORIZONTAL;
            tabuleiro[TAMANHO-1][i]=PAREDE_HORIZONTAL;
        }
        //espaços vazios na parte interna da matriz
        for(int i=1;i<TAMANHO-1;i++){
            for(int j=1; j<TAMANHO-1; j++){
                if(Math.random()>PROBABILIDADE){
                    tabuleiro[i][j] = PAREDE_INTERNA;
                }else{
                    tabuleiro[i][j] = VAZIO;
                }
            }
        }
        //definindo as posições de inicio e destino do labirinto
        linhainicio = gerarNumero(1,TAMANHO/2-1);
        colunainicio = gerarNumero(1, TAMANHO/2-1);
        tabuleiro[linhainicio][colunainicio] = INICIO;
        linhadestino = gerarNumero(TAMANHO/2, TAMANHO-2);   
        colunadestino = gerarNumero(TAMANHO/2, TAMANHO-2);
        tabuleiro[linhadestino][colunadestino] = DESTINO;
    }
    //procedimento para imprimir a matriz na tela
        public static void imprimir(){
            for(int i=0;i<TAMANHO;i++){
                for(int j=0;j<TAMANHO;j++){
                    System.out.print(tabuleiro[i][j]);
                }
                System.out.println();
            }
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
    //função para definir os valores da linhainicio e colunainicio
        public static int gerarNumero(int minimo, int maximo){
            int valor = (int) Math.round(Math.random()*(maximo-minimo));
            return minimo+valor;
        }
        
    //função de busca pelo caminho
        public static boolean procurarCaminho(int linhaAtual, int colunaAtual){
            int proxLinha;
            int proxColuna;
            boolean achou = false;
            //tentar subir
            proxLinha = linhaAtual -1;
            proxColuna = colunaAtual;
            achou = tentarCaminho(proxLinha, proxColuna);
            //tentar descer
            if(!achou){
                proxLinha = linhaAtual+1;
                proxColuna = colunaAtual;
                achou = tentarCaminho(proxLinha,proxColuna);
            }
            //tentar esquerda
            if(!achou){
                proxLinha = linhaAtual;
                proxColuna = colunaAtual-1;
                achou = tentarCaminho(proxLinha,proxColuna);
            }
            //tentar direita
            if(!achou){
                proxLinha = linhaAtual;
                proxColuna = colunaAtual+1;
                achou = tentarCaminho(proxLinha,proxColuna);  
            }
           return achou;           
        }
        
     //função que irá verificar se determinada posição é o destino, vazio ou não tem saida
        public static boolean tentarCaminho(int proxLinha, int proxColuna){
            boolean achou = false;
            if(tabuleiro[proxLinha][proxColuna]==DESTINO){
                achou = true;
            } else if(posicaoVazia(proxLinha, proxColuna)){
                //marcar o caminho pois a posição é vazia
                tabuleiro[proxLinha][proxColuna] = CAMINHO;
                imprimir();
                achou = procurarCaminho(proxLinha,proxColuna);      //RECURSÃO
                if(!achou){
                    tabuleiro[proxLinha][proxColuna] = SEM_SAIDA;
                    imprimir();
                }
            }
            return achou;
        }
        
    //função que vai verificar se a posição está vazia
        public static boolean posicaoVazia(int linha, int coluna){
            boolean vazio = false;
            if(linha>=0 && coluna>=0 && linha<TAMANHO && coluna<TAMANHO){
                vazio = (tabuleiro[linha][coluna]==VAZIO);
            }
            return vazio;
        }
        
    //procedimento main
        public static void main(String[] args){
            tabuleiro = new char[TAMANHO][TAMANHO];
            inicializarMatriz();
            imprimir();
            System.out.println("\n-Procurando solução -\n");
            boolean achou = procurarCaminho(linhainicio, colunainicio);
            if(achou){
                System.out.println("Achou o caminho!");
            }else{
                System.out.println("Não tem caminho");
            }
        }
    
 
}
