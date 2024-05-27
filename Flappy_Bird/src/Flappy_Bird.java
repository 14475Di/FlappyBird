import java.awt.* ;
import java.awt.event.*;
import java.util.ArrayList;// este API/libraria vai armazenar todos os canos do jogo
import java.util.Random;// este Api / libraria tambem vai servir para por os canos em posiçoes aleatorias
import javax.swing.*;


// esta classe / ficheiro foi criada para servir como "canva" para desenharmos o nosso jogo
public class Flappy_Bird extends JPanel implements ActionListener , KeyListener { // a classe (flappy bird) herda da classe (Jpanel) que podemos usar coisas da classe Jpanel para o nosso jogo 
    int larguraTabuleiro = 360 ;
    int alturaTabuleiro = 640 ;

    //imagens para todas as variaveis que vao armazenar as nossas imagens
    Image imagemFundo ;
    Image imagemPassaro ;
    Image imagemCanoTopo;
    Image imagemCanoFundo;
    Image imagemGameOver;
    Image imagemFireBall;


    //Variaveis do passaro
    //pro passaro nos temos que criar uma posiçao x e y e altura e largura
    int passaroX = larguraTabuleiro/8 ; // isto coloca o passaro no posiçao que seria a largura a dividir por 8
    int passaroY = alturaTabuleiro/2 ; // e a posiçao Y vai ser metade da altura do tabuleiro // entao o passaro vai ser posisionado a esquerda
    int larguraPassaro = 34 ; 
    int alturaPassaro = 24 ;

    class Passaro { // esta classe vai guardar as variaveis do Passaro e é mais facil para acessar os valores
        int x = passaroX;
        int y = passaroY;
        int largura = larguraPassaro;
        int altura = alturaPassaro;
        Image img ;

        Passaro( Image img) {
            this.img = img ;
        }

    }

    //Canos
    int canoX = larguraTabuleiro ;
    int canoY = 0 ;
    int canoLargura = 64 ;
    int canoAltura = 512 ;

    class Cano {
        int X = canoX ;
        int y = canoY ;
        int largura = canoLargura ;
        int altura = canoAltura ;
        Image img ;
        boolean atravessou = false ;

        
        Cano( Image img) {
            this.img = img ;
        }
    }
    
    //Fire ball
    int ballX = larguraTabuleiro/8 ; // isto coloca o passaro no posiçao que seria a largura a dividir por 8
    int ballY = alturaTabuleiro/2 ; // e a posiçao Y vai ser metade da altura do tabuleiro // entao o passaro vai ser posisionado a esquerda
    int larguraBall = 22 ; 
    int alturaBall = 12 ;

    class Ball { // esta classe vai guardar as variaveis do Passaro e é mais facil para acessar os valores
        int x = ballX;
        int y = ballY;
        int largura = larguraBall;
        int altura = alturaBall;
        Image img ;

        Ball( Image img) {
            this.img = img ;
        }

    }

    // logica do jogo 
    Ball ball ;
    Passaro passaro ;
    int velocidadeX = -4 ;
    int velocidadeY= 0 ;
    int gravidade = 1 ;

    ArrayList <Cano> canos ;
    Random random = new Random() ;

    //Timers
    Timer gameLoop;// serve para passar os farmes durante o jogo 
    Timer colocaCanosTimer ; // serve para colocar os canos

    //gmae over 
    boolean gameOver = false ;
    double pontuaçao = 0.0d ;


    public Flappy_Bird () {
        setPreferredSize(new Dimension(larguraTabuleiro , alturaTabuleiro ));
        //setBackground(Color.blue);// isto seria um "debugger" para ver se a classe Flappy_Bird esta a funcionar
        setFocusable(true);
        addKeyListener(this); // faz com que verificamos as  functions que o keyListerner pediu
        //.getResource()<- localizaçao da classe que é a pasta src e tambem temos as imgens no mesmo .getClass()<- refere se a classe Flappy_Brid 
        // popular as variaveis de imagem 
        imagemFundo = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage() ;
        imagemPassaro = new ImageIcon(getClass().getResource("./flappybird.png")).getImage() ;
        imagemCanoTopo = new ImageIcon(getClass().getResource("./toppipe.png")).getImage() ;
        imagemCanoFundo = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage() ;
        imagemGameOver = new ImageIcon(getClass().getResource("./gameOver.png")).getImage() ;
     
        //passaro
        passaro = new Passaro(imagemPassaro) ;
        //canos
        canos = new ArrayList<Cano>() ; 
        //fireball
        ball = new Ball(imagemFireBall)  ;                             


        //colocar canos timer
        colocaCanosTimer = new Timer(1500, new ActionListener() {// coloca um cano a 1500 que é 1.5 segundos
            @Override
            public void actionPerformed(ActionEvent e) {
                colocaCanos() ;
            }
        });
        colocaCanosTimer.start() ;

        //timer do jogo 
        gameLoop = new Timer(1000/50, this); // 1000 é um segundo e queremos fazer 60 vezes por segundo "this" vai se referir a classe do flappy bird
        gameLoop.start();
    }

    public void colocaCanos () {// adiciona os canos no array de canos

        //Math.random() * (canoAltura/ 2)) escolheum numero aleiatorio de 256 que é metade da altura
        int randomCanoY = (int) (canoY - canoAltura / 4 - Math.random() * (canoAltura/ 2)) ;
        int espaçoAberto = alturaTabuleiro/4 ; // crie a variavel para deixar um espaço aberto para o passaro

        Cano canoDoTopo = new Cano(imagemCanoTopo) ;
        canoDoTopo.y = randomCanoY ; 
        canos.add(canoDoTopo) ;

        Cano canoDoFundo = new Cano(imagemCanoFundo) ;
        canoDoFundo.y = canoDoTopo.y + canoAltura + espaçoAberto;// isto pega no valor de canoDoTopo.y que é 0 e soma com a altura do cano e o espaço aberto sendo 0 + 512 + 160  
        canos.add(canoDoFundo) ;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);//envoca a funçao do classe (Pai) Jpanel
        draw(g);
    }

    public void draw (Graphics g) {
        //System.out.println("Draw");degugger para ver se a fun;ao draw estava a funcionar
        //fundo
        g.drawImage(imagemFundo, 0, 0, larguraTabuleiro , alturaTabuleiro , null) ;

        //passaro
        g.drawImage(passaro.img , passaro.x , passaro.y , passaro.largura, passaro.altura , null) ;

        
        // dasenha canos
        for (int i = 0 ; i < canos.size()  ; i= i + 1) {
            Cano cano = canos.get(i) ;
            g.drawImage(cano.img, cano.X, cano.y, cano.largura, cano.altura, null) ;
        }

        //pontuaçao e gameover 
        g.setColor(Color.white);
        g.setFont(new Font("Arial" , Font.PLAIN , 32));
        if(gameOver ) {
            g.drawImage(imagemGameOver, 0, 0, larguraTabuleiro, alturaTabuleiro, null) ;
            g.drawString("Pontuaçao : " + String.valueOf((int) pontuaçao), 10, 35);
        } else {
            g.drawString("Pontuaçao : " + String.valueOf((int) pontuaçao), 10, 35);
        }


       /*  //gameover 
        if (gameOver != false){
            g.drawImage(imagemGameOver, 0, 0, larguraTabuleiro, alturaTabuleiro, null) ;
        }*/
    }

    public void move () {
        //passaro
        velocidadeY += gravidade ;
        passaro.y += velocidadeY ;
        passaro.y = Math.max(passaro.y , 0) ;

        // canos 
        for (int i = 0 ; i < canos.size() ; i= i + 1) {
            Cano cano = canos.get(i) ;
            cano.X += velocidadeX ; 

            if(!cano.atravessou && passaro.x > cano.X + cano.largura ) { // se o passaro passar o lado correto do cano
                cano.atravessou = true ;
                pontuaçao += 0.5 ; // como sao dois canos o jogador a passar pelo os dois recebe um ponto por iss que a variavel esta double ao em vez de int
            }
            
            if (colisao(passaro, cano) ){
                gameOver = true ;
            }
        }

        if (passaro.y > alturaTabuleiro) {
            gameOver = true ;
        }
    }

    public boolean colisao (Passaro a , Cano b  ) {
        return a.x < b.X + b.largura &&
                a.x + a.largura > b.X && 
                a.y < b.y + b.altura &&
                a.y + a.altura > b.y ; //formula para dectatar colisao o rapaz nao explica 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move() ;
        repaint();
        if (gameOver) {
            colocaCanosTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {// para todas a teclas quando pressionadas
        if (e.getKeyCode() == KeyEvent.VK_SPACE ){// Se a tecla que neste caso é espaço for pressionada vai fazer algo
            velocidadeY = -9 ;
        }
        if (e.getKeyCode() == KeyEvent.VK_R){// Se a tecla que neste caso é espaço for pressionada vai fazer algo
            if (gameOver) {
                // se o r for clicado o jogo reseta
               passaro.y = passaroY ;
               velocidadeY = 0 ;
               canos.clear();
               pontuaçao = 0 ;
               gameOver = false ; 
               gameLoop.start();
               colocaCanosTimer.start();

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E ){

        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {}// por exemplo f5 quanto clica na faria nada mas se "A" fosse a iria fazer algo

    
    @Override
    public void keyReleased(KeyEvent e) {}// para todas a teclas quando pressionadas e largas
      
    
}
