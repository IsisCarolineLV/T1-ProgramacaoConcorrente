/* ***************************************************************
* Autor............: Isis Caroline Lima Viana
* Matricula........: 202410016
* Inicio...........: 16/03/2025
* Ultima alteracao.: 20/03/2025
* Nome.............: ControllerTelaPrincipal.java!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
* Funcao...........: Esta classe eh a responsavel por controlar os elementos do telaPrincipal.fxml e  
                     seus eventos. Esses elementos consistem em: o anchorPane onde a tela esta, a  
                     imagem de fundo,a imagem do botao sair, a ComboBox usada para escolher a disposicao 
                     inicial das naves e o botao start. Quando o botao start eh clicado ele ativa um metodo 
                     que carrega um novo arquivo fxml (telaPrincipal.fxml) no pane ja criado.
*************************************************************** */

package controle;

//importando as bibliotecas necessarias para a aplicacao
import java.io.IOException;
import javafx.application.Platform;

//importando as bibliotecas necessarias para trocar de tela (retornar ao menu)
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//importando as bibliotecas necessarias para manipular os elementos da cena
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

//importando biblioteca para a animacao das naves
import javafx.animation.AnimationTimer;

//importando a classe Nave
import modelo.Nave;


public class ControllerTelaPrincipal {

  @FXML
  private AnchorPane anchorPanePrincipal;  //mesma referencia do anchorPaneMenu

  @FXML
  private ImageView fundoTelaPrincipal;

  @FXML
  private Slider sliderVelocidadeDaNave1;  //controle da velocidade da nave vermelha

  @FXML
  private Slider sliderVelocidadeDaNave2;  //controle da velocidade da nave azul

  @FXML
  private ImageView imageViewBotaoReiniciar;  //imagem do botao reiniciar(o botao real esta com opacidade = 0)

  @FXML
  private ImageView imageViewBotaoSair;  //imagem do botao sair(o botao real esta com opacidade = 0)

  @FXML
  private ImageView imageViewBotaoVoltar;  //imagem do botao voltar(o botao real esta com opacidade = 0)

  @FXML
  private ComboBox<String> comboBoxEscolheDisposicao;  //comboBox para trocar a disposicao ao reiniciar
  
  private  String selecionado;  //guarda a opcao selecionada no comboBox
  private int disposicaoEscolhida;  //guarda o numero da disposicao escolhida anteriormente para ser usada no reiniciar
  private double velocidadeDaNave1 = 5;  //iteracao da animacao da nave vermelha
  private double velocidadeDaNave2 = 5;  //iteracao da animacao da nave azul
  private Nave nave1;  //nave vermelha
  private Nave nave2;  //nave azul
  private ImageView imageViewNave1 = new ImageView(("/imagens/nave1_1.png"));  //imageView da nave vermelha
  private ImageView imageViewNave2 = new ImageView(new Image("/imagens/nave2_1.png"));  //imageView da nave azul
  private AnimationTimer animacaoNaves;
  

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: primeiro metodo chamado quando o fxml eh iniciado. Ele chama o metodo criarListeners, adiciona
            as imageViews das duas naves no anchorPane, envia elas e a imagem de fundo para baixo dos demais 
            elementos (pois as naves estavam passando por cima dos demais e estava ficando estranho), chama o
            metodo definirAnimacao(que programa o AnimationTime para que ele fique repetindo infinitamente) e 
            inicializa as naves informando ao construtor qual a disposicao que o usuario escolheu.
  * Parametros: nenhum
  * Retorno: vazio
  * *************************************************************** */
  public void initialize() {
    criarListeners();  //dos sliders e do comboBox
    anchorPanePrincipal.getChildren().add(imageViewNave1);  //adiciona a imagem da nave vermelha a cena
    anchorPanePrincipal.getChildren().add(imageViewNave2);  //adiciona a imagem da nave azul a cena
    imageViewNave1.toBack();  //envia a nave vermelha para debaixo de todos os elementos
    imageViewNave2.toBack();  //envia a nave azul para debaixo de todos os elementos
    fundoTelaPrincipal.toBack();  //envia a imagem de fundo para debaixo de todos os elementos

    definirAnimacao();  //animacao = movimentacao das naves
    nave1 = new Nave();  //cria nave vermelha
    nave2 = new Nave();  //cria nave azul
  }  //fim do metodo initialize


  /* ***************************************************************
  * Metodo: posicionarNaves
  * Funcao: pega a disposicaoEscolhida pelo usuario na tela anterior e informa ela pra a variavel de mesmo nome
            (disposicaoEscolhida) e, dentro de um switch case com essa variavel, define a posicao e estado inicial 
            de cada nave. Por fim o metodo exibe as imageViews das naves na tela e SET'a a posicao definida como 
            posicao original.
  * Parametros: disposicaoEscolhida (inteiro de 1 a 4)
  * Retorno: vazio
  * *************************************************************** */
  public void posicionarNaves(int disposicaoEscolhida) {
    this.disposicaoEscolhida = disposicaoEscolhida; //informa pra disposicaoEscolhida desse Controller
                                                    //qual foi escolhida no ControllerMenu
    
    switch (disposicaoEscolhida) {
      case 1: {  //ambos na direita
        imageViewNave1.setLayoutX(-20);   //define posicao da nave vermelha no eixo X
        imageViewNave1.setLayoutY(201);   //define posicao da nave vermelha no eixo Y
        nave1.setEstadoInicial("retoD");  //nave1 comeca no estado "indo reto pra direita"
        imageViewNave2.setLayoutX(-20);   //define posicao da nave azul no eixo X
        imageViewNave2.setLayoutY(328);   //define posicao da nave azul no eixo Y
        nave2.setEstadoInicial("retoD");  //nave2 comeca no estado "indo reto pra direita"
        break;
      } //fim do case 1
      
      case 2: {  //ambos em cima
        imageViewNave1.setLayoutX(-20);   //define posicao da nave vermelha no eixo X
        imageViewNave1.setLayoutY(201);   //define posicao da nave vermelha no eixo Y
        nave1.setEstadoInicial("retoD");  //nave1 comeca no estado "indo reto pra direita"
        imageViewNave2.setLayoutX(1109);  //define posicao da nave azul no eixo X
        imageViewNave2.setLayoutY(201);   //define posicao da nave azul no eixo Y
        nave2.setEstadoInicial("retoE");  //nave2 comeca no estado "indo reto pra esquerda"
        imageViewNave2.setScaleX(-1);  //inverte a imagem
        break;
      } //fim do case 2
      
      case 3: {  //na diagonal
        imageViewNave1.setLayoutX(-20);   //define posicao da nave vermelha no eixo X
        imageViewNave1.setLayoutY(201);   //define posicao da nave vermelha no eixo Y
        nave1.setEstadoInicial("retoD");  //nave1 comeca no estado "indo reto pra direita"
        imageViewNave2.setLayoutX(1109);  //define posicao da nave azul no eixo X
        imageViewNave2.setLayoutY(328);   //define posicao da nave azul no eixo Y
        nave2.setEstadoInicial("retoE");  //nave2 comeca no estado "indo reto pra esquerda"
        imageViewNave2.setScaleX(-1);     //inverte a imagem
        break;
      } //fim do case 3
      
      case 4: {  //ambos na esquerda
        imageViewNave1.setLayoutX(1109);   //define posicao da nave vermelha no eixo X
        imageViewNave1.setLayoutY(201);    //define posicao da nave vermelha no eixo Y
        nave1.setEstadoInicial("retoE");   //nave1 comeca no estado "indo reto pra direita"
        imageViewNave1.setScaleX(-1);      //inverte a imagem
        imageViewNave2.setLayoutX(1109);   //define posicao da nave azul no eixo X
        imageViewNave2.setLayoutY(328);    //define posicao da nave azul no eixo Y
        nave2.setEstadoInicial("retoE");   //nave2 comeca no estado "indo reto pra esquerda"
        imageViewNave2.setScaleX(-1);      //inverte a imagem
        break;
      } //fim do case 4
    } //fim do switch
    imageViewNave1.setVisible(true);  //mostra a nave vermelha
    imageViewNave2.setVisible(true);  //mostra a nave azul
    nave1.setPosicaoOriginal(imageViewNave1); //manda a posicao original para o objeto nave1
    nave2.setPosicaoOriginal(imageViewNave2); //manda a posicao original para o objeto nave2
  } //fim do metodo posicionarNaves


  /* ***************************************************************
  * Metodo: definirAnimacao
  * Funcao: define a movimentacao das naves num loop infinito que se repete 60 vezes por segundo
  * Parametros: nenhum
  * Retorno: vazio
  * *************************************************************** */
  public void definirAnimacao() {
    animacaoNaves = new AnimationTimer() { 
      /* ***************************************************************
      * Metodo: handle
      * Funcao: define o que vai acontecer a cada loop
      * Parametros: long now(segundo de execucao do programa em nanosegundos)
      * Retorno: vazio
      * *************************************************************** */
      @Override
      public void handle(long now) {  //-----------------------------------------------------------
          nave1.movimentarNave(imageViewNave1, velocidadeDaNave1, disposicaoEscolhida, 1);  //movimenta nave vermelha
          nave2.movimentarNave(imageViewNave2, velocidadeDaNave2, disposicaoEscolhida, 2);  //movimenta nave azul
      } //fim do metodo handle
    };  //fim da criacao do timer
    animacaoNaves.start();  //inicia o loop
  } //fim do metodo definirAnimacao


  /* ***************************************************************
  * Metodo: criarListeners
  * Funcao: cria o listener do slider1(velocidade da nave vermelha), do slider2 (velocidade da nave azul) e do
            comboBox (disposicoes das naves a cada reinicio).
  * Parametros: nenhum
  * Retorno: vazio
  * *************************************************************** */
  public void criarListeners() {
    
    //criando o listener do slider 1:
    sliderVelocidadeDaNave1.valueProperty().addListener(new ChangeListener<Number>() {
      /* ***************************************************************
      * Metodo: changed
      * Funcao: verifica se o valor do slider mudou e, se sim, passa esse valor para a variavel velocidadeDaNave1
      * Parametros: ObservableValue<? extends Number> observado 
                    Number velocidadeAnterior
                    Number novaVelocidade
      * Retorno: vazio
      * *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observado, Number velocidadeAnterior, Number novaVelocidade) {
        velocidadeDaNave1 = sliderVelocidadeDaNave1.getValue(); //passa novo valor do slider pra variavel velocidadeDaNave1
      } //fim do metodo changed
    }); //fim da criacao do listener
    
    //criando o listener do slider 1:
    sliderVelocidadeDaNave2.valueProperty().addListener(new ChangeListener<Number>() {
      /* ***************************************************************
      * Metodo: changed
      * Funcao: verifica se o valor do slider mudou e, se sim, passa esse valor para a variavel velocidadeDaNave2
      * Parametros: ObservableValue<? extends Number> observado 
                    Number velocidadeAnterior
                    Number novaVelocidade
      * Retorno: vazio
      * *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observado, Number velocidadeAnterior, Number novaVelocidade) {
        velocidadeDaNave2 = sliderVelocidadeDaNave2.getValue(); //passa novo valor do slider pra variavel velocidadeDaNave2
      } //fim do metodo changed
    }); //fim da criacao do listener
    
    //definindo as opcoes do comboBox:
    comboBoxEscolheDisposicao.getItems().addAll("Ambos na esquerda", "Ambos em cima", "Em diagonal", "Ambos na direita");
    //começa escrevendo "Trocar disposicao"
    comboBoxEscolheDisposicao.setValue("Trocar disposicao");
    //criando o listener do ComboBox:
    comboBoxEscolheDisposicao.setOnAction(e -> {
    selecionado = comboBoxEscolheDisposicao.getValue(); //passa a opcao escolhida pra variavel selecionado
    }); //fim da criacao do listener
  } //fim do método criarListeners


  /* ***************************************************************
  * Metodo: clicouBotaoReiniciar
  * Funcao: reseta os sliders para a posicao inicial (metade do valor maximo[5]), se a comboBox tiver alguma opcao selecionada
            a variavel disposicaoEscolhida eh atualizada, as naves sao reposicionadas de acordo com a disposicaoEscolhida
            e as naves sorteiam uma nova rota cada
  * Parametros: ActionEvent event(button clicked)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void clicouBotaoReiniciar(ActionEvent event) {
    sliderVelocidadeDaNave1.setValue(5);
    sliderVelocidadeDaNave2.setValue(5);
    //se alguma opcao estiver selecionada no comboBox:
    if(selecionado!=null){
      disposicaoEscolhida = comboBoxEscolheDisposicao.getItems().indexOf(selecionado)+1;  //numero equivalente a opcao selecionada
    } //fim do if
    posicionarNaves(disposicaoEscolhida);
    nave1.sortearRota();
    nave2.sortearRota();
  } //fim do metodo clicouBotaoReiniciar


  /* ***************************************************************
  * Metodo: clicouBotaoSair
  * Funcao: fechar a aplicacao
  * Parametros: ActionEvent event (button clicked)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void clicouBotaoSair(ActionEvent event) {
    Platform.exit();	//fecha a aplicacao
  }  //fim do metodo clicouSair


  /* ***************************************************************
  * Metodo: clicouBotaoVoltar
  * Funcao: para o loop da animacao/movimentacao, apaga os imageView das naves, carrega novamente o arquivo 
            telaMenu.fxml no anchorPanePrincipal(que eh o mesmo anchorPaneMenu) 
  * Parametros: ActionEvent event (button clicked)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void clicouBotaoVoltar(ActionEvent event) {
    animacaoNaves.stop(); //naves param de se mover
    anchorPanePrincipal.getChildren().remove(imageViewNave1); //apaga imageView da nave vermelha
    anchorPanePrincipal.getChildren().remove(imageViewNave2); //apaga imageView da nave azul
    try {
      //carregando telaMenu.fxml no anchorPanePrincipal no lugar do telaPrincipal.fxml:
      Parent tela1 = FXMLLoader.load(getClass().getResource("/visao/telaMenu.fxml"));
      anchorPanePrincipal.getChildren().setAll(tela1);
    } //fim do try
    catch (IOException e) {
      //caso haja algum erro na hora de invocar o telaMenu.fxml a mensagem abaixo aparece no console
      System.out.println("Erro ao chamar o arquivo telaMenu.fxml\n"+ e);
    } //fim do catch
  } //fim do metodo clicouBotaoVoltar


  /* ***************************************************************
  * Metodo: destacarBotaoReiniciar
  * Funcao: chama a funcao destacarBotao a fim de destacar o botao Reiniciar e mostrar para o usuario
            que eh um botao clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void destacarBotaoReiniciar(MouseEvent event) {
    destacarBotao(imageViewBotaoReiniciar); //aumenta o imageView do botao reiniciar
  } //fim do metodo destacarBotaoReiniciar


  /* ***************************************************************
  * Metodo: destacarBotaoSair
  * Funcao: chama a funcao destacarBotao a fim de destacar o botao Sair e mostrar para o usuario
            que eh um botao clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void destacarBotaoSair(MouseEvent event) {
    destacarBotao(imageViewBotaoSair);  //aumenta o imageView do botao sair
  } //fim do metodo destacarBotaoSair


  /* ***************************************************************
  * Metodo: destacarBotaoVoltar
  * Funcao: chama a funcao destacarBotao a fim de destacar o botao Voltar e mostrar para o usuario
            que eh um botao clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void destacarBotaoVoltar(MouseEvent event) {
    destacarBotao(imageViewBotaoVoltar);  //aumenta o imageView do botao voltar
  } //fim do metodo destacarBotaoVoltar


  /* ***************************************************************
  * Metodo: destacarSlider1
  * Funcao: aumenta em 0.1 pixels no eixo X e no eixo Y o slider1 a fim de destacar 
            o slider e mostrar para o usuario que eh um elemento clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void destacarSlider1(MouseEvent event) {
    sliderVelocidadeDaNave1.setScaleY(1.1);
  } //fim do metodo destacarSlider1


  /* ***************************************************************
  * Metodo: destacarSlider2
  * Funcao: aumenta em 0.1 pixels no eixo X e no eixo Y o slider2 a fim de destacar 
            o slider e mostrar para o usuario que eh um elemento clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void destacarSlider2(MouseEvent event) {
    sliderVelocidadeDaNave2.setScaleY(1.1);
  } //fim do metodo destacarSlider1


  /* ***************************************************************
  * Metodo: destacarComboBox
  * Funcao: aumenta em 0.1 pixels no eixo X e no eixo Y o comboBox quando o mouse entra dentro dela
            a fim de destacar o comboBox e mostrar para o usuario que eh um elemento clicavel. 
  * Parametros: MouseEvent event (mouse entered)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  public void destacarComboBox(){
    comboBoxEscolheDisposicao.setScaleX(1.1); //muda a escala do comboBox no eixo X
    comboBoxEscolheDisposicao.setScaleY(1.1); //muda a escala do comboBox no eixo Y
  } //fim do metodo destacarComboBox
  
  /* ***************************************************************
  * Metodo: retornarProModoNormal
  * Funcao: quando o mouse sai de dentro de um botao, slider ou do comboBox todos os elementos
            retornam ao seu tamanho original
  * Parametros: MouseEvent event (mouse exited)
  * Retorno: vazio
  * *************************************************************** */
  @FXML
  void retornarProModoNormal(MouseEvent event) {
    //botao reiniciar:
    imageViewBotaoReiniciar.setScaleX(1);
    imageViewBotaoReiniciar.setScaleY(1);
    //botao voltar:
    imageViewBotaoVoltar.setScaleX(1);
    imageViewBotaoVoltar.setScaleY(1);
    //botao sair:
    imageViewBotaoSair.setScaleX(1);
    imageViewBotaoSair.setScaleY(1);
    //slider 1 (vermelho)
    sliderVelocidadeDaNave1.setScaleX(1);
    sliderVelocidadeDaNave1.setScaleY(1);
    //slider 2 (azul)
    sliderVelocidadeDaNave2.setScaleX(1);
    sliderVelocidadeDaNave2.setScaleY(1);
    //comboBox
    comboBoxEscolheDisposicao.setScaleX(1);
    comboBoxEscolheDisposicao.setScaleY(1);
  }
 

  /* ***************************************************************
  * Metodo: destacarBotao
  * Funcao: aumenta em 0.1 pixels no eixo X e no eixo Y o imageView do botao enviado como paramentro 
            quando o mouse entra dentro dele a fim de destacar o botao e mostrar para o usuario 
            que eh um elemento clicavel. 
  * Parametros: ImageView objetoDestacado (imagem do botao)
  * Retorno: vazio
  * *************************************************************** */
  public void destacarBotao(ImageView objetoDestacado){
    objetoDestacado.setScaleX(1.1); //muda a escala do imageView no eixo X
    objetoDestacado.setScaleY(1.1); //muda a escala do imageView no eixo Y
  } //fim do metodo destacarBotao
  
} //fim da classe ControllerTelaPrincipal
