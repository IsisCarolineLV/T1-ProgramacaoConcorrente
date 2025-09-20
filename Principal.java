/* ***************************************************************
* Autor............: Isis Caroline Lima Viana
* Matricula........: 202410016
* Inicio...........: 15/03/2025
* Ultima alteracao.: 19/03/2025
* Nome.............: Principal.java
* Funcao...........: O programa consiste em duas naves que percorrem simultaneamente um caminho pre-definido, 
                     com trechos simples e duplos. O usuario define as posicoes iniciais das naves atraves do 
					 menu. Esta classe (Principal.java) eh responsavel por inicializar a aplicacao JavaFX, 
					 carregar a tela de menu (telaMenu.fxml) e exibi-la no StageInicial.
*************************************************************** */

//importando as bibliotecas necessarias
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//importando os constrollers dos fxml
import controle.ControllerMenu;
import controle.ControllerTelaPrincipal;

public class Principal extends Application {

  /* ***************************************************************
  * Metodo: main
  * Funcao: primeiro metodo chamado quando o progaram eh iniciado. Apenas chama o
            metodo launch para iniciar a aplicação
  * Parametros: argumentos da linha de comando
  * Retorno: vazio
  * *************************************************************** */
  public static void main(String[] args) {
    launch(); // inicia a aplicação JavaFX
  } // fim do método main


  /* ***************************************************************
  * Metodo: start
  * Funcao: eh chaamdo logo apos o launch(). Esse metodo eh responsavel por carregar
            o arquivo telaMenu.fxml(que contem o layout do menu), criar uma cena com
            base nesse arquivo, criar um stage(palco) para as cenas aparecerem e mostrar
            ao usuario esse stage.
  * Parametros: stageInicial (stage principal da aplicacao)
  * Retorno: vazio
  * *************************************************************** */
  @Override
  public void start(Stage stageInicial) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/visao/telaMenu.fxml")); // carrega o arquivo FXML que define
                                                                                        // o layout da tela de menu
    stageInicial.setScene(new Scene(root)); // define a cena do Stage com o layout carregado
    stageInicial.setResizable(false); // define que o tamanho da janela eh fixo
    stageInicial.initStyle(StageStyle.DECORATED);  //define o estilo visual da janela
    stageInicial.centerOnScreen(); //coloca a janela centralizada na cena
    stageInicial.show(); // exibe a janela para o usuário
    
  } // fim do método start

} // fim do programa principal
