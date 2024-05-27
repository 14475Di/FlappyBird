import javax.swing.* ; 

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
        // estas variaveis foram criadas para defenir o tamanho da janela do jogo em pixeis 
        //o autor do video que estou a seguir pos em pixeis por causa das imagens que pos 
        int larguraTabuleiro = 360 ;
        int alturaTabuleiro = 640 ;

        JFrame frame = new JFrame("Flappy Bird");
        //frame.setVisible(true); comentei e pos na linha 21 pos depois de todas as configuraçoes feitas
        frame.setSize(larguraTabuleiro , alturaTabuleiro);//set para a lagura e altura do tabuleiro
        frame.setLocationRelativeTo(null); // faz com que ponha a janela do jogo no centro do nosso ecra 
        frame.setResizable(false);// faz com que a janela nao possa ser mexida em tamanho
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Quando o utilizador clicar no X do direito lado superior da janela vai terminar o programa

        Flappy_Bird flappybird = new Flappy_Bird() ;
        frame.add(flappybird) ;
        frame.pack();// isto foi adicionado por que se nao a barra de cima que tem para minimirar e fechar ia contar na altura em pixeis
        flappybird.requestFocus();
        frame.setVisible(true);
        
    }
}
