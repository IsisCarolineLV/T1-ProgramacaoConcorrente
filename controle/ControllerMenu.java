/* ***************************************************************
* Autor............: Isis Caroline Lima Viana
* Matricula........: 202410016
* Inicio...........: 15/03/2025
* Ultima alteracao.: 20/03/2025
* Nome.............: ControllerMenu.java
* Funcao...........: Esta classe eh a responsavel por controlar os elementos do telaMneu.fxml e seus 
                     eventos. Esses elementos consistem em: o anchorPane onde a tela esta, a imagem  
                     de fundo, a imagem do botao sair, a ComboBox usada para escolher a disposicao  
                     inicial das naves e o botao start. Quando o botao start eh clicado ele ativa um  
                     metodo que carrega um novo arquivo fxml (telaPrincipal.fxml) no pane ja criado.
*************************************************************** */

package controle;

//importando as bibliotecas necessarias para a aplicacao
import java.io.IOException;
import javafx.application.Platform;

//importando as bibliotecas necessarias para trocar de tela
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//importando as bibliotecas necessarias para manipular os elementos do fxml
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerMenu {

  @FXML
  private AnchorPane anchorPaneMenu; // anchorPane onde os elementos estao dispostos

  @FXML
  private ImageView imagemFundoMenu;

  @FXML
  private ImageView imageViewBotaoSair; /*
                                         * invocado aqui so para que possa ser
                                         * aumentado quando o mouse entrar na imagem
                                         */

  @FXML
  private ComboBox<String> comboBoxEscolheDisposicao;

  private int disposicaoEscolhida; // 1:ambos na direita
                                   // 2:ambos em cima
                                   // 3:em diagonal
                                   // 4:ambos na esquerda

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: primeiro metodo chamado quando o fxml eh iniciado. Ele define as
             opcoes que o ComboBox vai exibir, cria o listener dele (que ficara 
             "escutando" para definir a opcao selecionada) e, quando o listener 
             detecta que alguma opcao foi selecionada ele informa o numero 
             equivalente dessa opcao (1-4) para a variavel disposicaoEscolhida.
   * Parametros: nenhum
   * Retorno: vazio
   * ***************************************************************
   */
  public void initialize() {
    // definindo as opcoes do comboBox
    comboBoxEscolheDisposicao.getItems().addAll("Ambos na esquerda",
        "Ambos em cima",
        "Em diagonal",
        "Ambos na direita");
    // criando o listener
    comboBoxEscolheDisposicao.setOnAction(e -> {

      // informando a opcao selecionada para a variavel disposicao escolhida
      String selecionado = comboBoxEscolheDisposicao.getValue();
      disposicaoEscolhida = comboBoxEscolheDisposicao.getItems().indexOf(selecionado) + 1;
    }); // fim do listener

  } // fim do metodo initialize

  /*
   * ***************************************************************
   * Metodo: clicouSair
   * Funcao: fechar a aplicacao
   * Parametros: ActionEvent event (button clicked)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void clicouSair(ActionEvent event) {
    Platform.exit(); // fecha a aplicacao
  } // fim do metodo clicouSair

  /*
   * ***************************************************************
   * Metodo: destacarBotaoSair
   * Funcao: aumenta em 0.1 pixels no eixo X e no eixo Y a imageView do botaoSair
             (botaoSair.png) quando o mouse entra dentro dela a fim de destacar o botao e 
             mostrar para o usuario que eh um botao clicavel.
   * Parametros: MouseEvent event (mouse entered)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void destacarBotaoSair(MouseEvent event) {
    imageViewBotaoSair.setScaleX(1.1); // muda a escala do imageView no eixo X
    imageViewBotaoSair.setScaleY(1.1); // muda a escala do imageView no eixo Y
  } // fim do metodo destacarBotaoSair

  /*
   * ***************************************************************
   * Metodo: diminuirBotaoSair
   * Funcao: retorna a imageView do botaoSair ao seu tamanho original quando o
             mouse sai de dentro dela.
   * Parametros: MouseEvent event (mouse exited)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void diminuirBotaoSair(MouseEvent event) {
    imageViewBotaoSair.setScaleX(1); // retorna a escala do eixo X pra 1
    imageViewBotaoSair.setScaleY(1); // retorna a escala do eixo Y pra 1
  } // fim do metodo diminuirBotaoSair

  /*
   * ***************************************************************
   * Metodo: trocarDeTela
   * Funcao: Responsável por carregar a tela principal (telaPrincipal.fxml), 
             configurar a disposição inicial das naves e substituir o conteúdo 
             atual do AnchorPane do menu pela nova tela. Em caso de erro no 
             carregamento do arquivo FXML, trata a exceção e exibe mensagem no 
             console.
   * Parametros: nenhum
   * Retorno: vazio
   * ***************************************************************
   */
  public void trocarDeTela() {

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/visao/telaPrincipal.fxml"));
      Parent tela1 = loader.load();
      ControllerTelaPrincipal controlePrincipal = loader.getController(); //pegando o controller da telaPrincipal
      controlePrincipal.posicionarNaves(disposicaoEscolhida); //informando pro controller qual a disposicao escolhida
                                                              //e posicionando as Naves
      anchorPaneMenu.getChildren().setAll(tela1); //troca para a tela principal
    } // fim do try
    catch (IOException e) {
      // caso o loader falhe em carregar o arquivo telaPrincipal.fxml a mensagem a
      // seguir eh impressa no console
      System.out.println("Erro ao chamar o arquivo telaPrincipal.fxml\n" + e);
    } // fim do catch

  } // fim do metodo trocarDeTela

  /*
   * ***************************************************************
   * Metodo: clicouIniciar
   * Funcao: chama a funcao trocarDeTela quando o botaoIniciar (start) eh clicado
             se alguma opcao tiver sido escolhida no comboBox, caso nao tenha sido 
             escolhida nenhuma o botao nao fara nada.
   * Parametros: ActionEvent event (button clicked)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void clicouIniciar(ActionEvent event) {
    if (comboBoxEscolheDisposicao.getValue() != null) // verifica se alguma opcao do comboBox foi selecionada
      trocarDeTela(); // muda para o telaPrincipal.fxml
  } // fim do metodo clicouIniciar

  /*
   * ***************************************************************
   * Metodo: destacarBotaoStart
   * Funcao: quando o mouse entra dentro do botaoIniciar a imagem do fundo muda
             para uma versao onde a imagem do botao start esta maior 
             (FundoMenu2.png) a fim de destacar o botao e monstrar para o usuario
             que eh um botao clicavel.
   * Parametros: MouseEvent event (mouse entered)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void destacarBotaoStart(MouseEvent event) {
    // troca a imagem do fundo para a versao com o botao start maior
    imagemFundoMenu.setImage(new Image(getClass().getResourceAsStream("/imagens/FundoMenu2.png")));
  } // fim do metodo destacarBotaoStart

  /*
   * ***************************************************************
   * Metodo: voltarTelaAoNormal
   * Funcao: quando o mouse sai de dentro do botaoIniciar a imagem do fundo muda
             para sua versao original.
   * Parametros: MouseEvent event (mouse exited)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void voltarTelaAoNormal(MouseEvent event) {
    // troca a imagem do fundo para a versao com o botao start normal
    imagemFundoMenu.setImage(new Image(getClass().getResourceAsStream("/imagens/FundoMenu.png")));
  } // fim do metodo voltarTelaAoNormal

  /*
   * ***************************************************************
   * Metodo: destacarComboBox
   * Funcao: quando o mouse entra dentro do comboBox sua escala eh aumentada em
             0.1 a fim de destacar o botao e monstrar para o usuario que eh um 
             elemento clicavel.
   * Parametros: MouseEvent event (mouse entered)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void destacarComboBox(MouseEvent event) {
    comboBoxEscolheDisposicao.setScaleX(1.1); // muda a escala do comboBox no eixo X
    comboBoxEscolheDisposicao.setScaleY(1.1); // muda a escala do comboBox no eixo Y
  } // fim do metodo destacarComboBox

  /*
   * ***************************************************************
   * Metodo: diminuirComboBox
   * Funcao: quando o mouse sai de dentro do comboBox ele retorna ao seu tamanho
             original
   * Parametros: MouseEvent event (mouse exited)
   * Retorno: vazio
   * ***************************************************************
   */
  @FXML
  void diminuirComboBox(MouseEvent event) {
    comboBoxEscolheDisposicao.setScaleX(1); // retorna a escala do eixo X pra 1
    comboBoxEscolheDisposicao.setScaleY(1); // retorna a escala do eixo Y pra 1
  } // fim do metodo diminuirComboBox

} // fim da classe ControllerMenu
