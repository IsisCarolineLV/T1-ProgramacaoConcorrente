/* ***************************************************************
* Autor............: Isis Caroline Lima Viana
* Matricula........: 202410016
* Inicio...........: 16/03/2025
* Ultima alteracao.: 20/03/2025
* Nome.............: Nave.java
* Funcao...........: Essa eh a classe nave, o objeto que anda pelas trilhas douradas.
					 Para resumir o comportamento de ambas naves criou-se essa classe
					 que possui a rota que a nave ira pegar (que decisoes ela vai tomar 
					 a cada bifurcacao), sua posica original (para que possa retornar a 
					 elas quando o botao reiniciar for clicado), seu estado (reto, subindo
					 ou descendo)
*************************************************************** */

package modelo;

//importando as bibliotecas necessarias
import java.util.Random;	//para escolher a rota aleatoriamente
import javafx.scene.image.ImageView;

public class Nave {
  private int[] rotaDaNave= new int[2];	//cima=1 e baixo=0
  private Random aleatorio = new Random();
  private int posicaoOriginalLayoutX;
  private int posicaoOriginalLayoutY;
  private String estadoInicial;	//usado quando a nave alcanca o limite da tela
  private String estado;
  private final double FATOR_DE_SUAVIZACAO = 0.2;	//pequeno multiplicador que ajusta levemente a posicao da 
                                                  //imagem no eixo Y quando ela entra no estado "reto"


  /* ***************************************************************
  * Metodo: construtor
  * Funcao: inicia o novo objeto(this) e sorteia a rota dele aleatoriamente com o metodo sortearRota
  * Parametros: nenhum
  * Retorno: nenhum
  * *************************************************************** */
  public Nave() {
    sortearRota();
  } //fim do construtor


  /* ***************************************************************
  * Metodo: sortearRota
  * Funcao: sorteia entre 1 ou 0 para cada uma das duas bifurcacoes que a nave encontrara, guardando
            essas variaveis no array rotaDaNave
  * Parametros: nenhum
  * Retorno: nenhum
  * *************************************************************** */
  public void sortearRota(){
    rotaDaNave[0] = aleatorio.nextInt(2); //0 ou 1
    rotaDaNave[1] = aleatorio.nextInt(2); //0 ou 1
  } //fim do metodo sortearRota


  /* ***************************************************************
  * Metodo: setPosicaoOriginal
  * Funcao: guarda a posicao no eixo X e no eixo Y de onde a nave inicia o caminho para que quando 
            ela cheguar no final da tela ela retorne a essa posicao
  * Parametros: ImageView imagemNave
  * Retorno: nenhum
  * *************************************************************** */
  public void setPosicaoOriginal(ImageView imagemNave) {
    posicaoOriginalLayoutX = (int) imagemNave.getLayoutX(); //posicao no eixo X
    posicaoOriginalLayoutY = (int) imagemNave.getLayoutY(); //posicao no eixo Y
  } //fim do metodo setPosicaoOriginal


  /* ***************************************************************
  * Metodo: setEstadoInicial
  * Funcao: guarda o estado inicial da nave para que quando ela chegar no final da tela 
            ela retorne a esse estado
  * Parametros: String estadoInicial
  * Retorno: nenhum
  * *************************************************************** */
  public void setEstadoInicial(String estadoIncial){
    this.estado=estadoIncial; //a nave fica no estado inicial
    this.estadoInicial = estadoIncial;  //guarda esse estado pra depois
  } //fim do metodo setEstadoInicial


  /* ***************************************************************
  * Metodo: movimentarNave
  * Funcao: verifica se a nave chegou ao fim da tela (se sim manda ela de volta a posicao original), 
            chama o metodo mudar estado e, em seguida, de acordo com o estado em que a nave ficou apos
            esse metodo, ela muda o seu LayoutX e LayoutY.
  * Parametros: ImageView imagemNave 
                double velocidade (influencia no tanto que os Layout X e Y vao mudar)
                int posicao (usado na definicao do estado[disposicaoEscolhida])
                int qualNave (o movimento de cada nave eh diferente)
  * Retorno: nenhum
  * *************************************************************** */
  public void movimentarNave(ImageView imagemNave, double velocidade, int posicao, int qualNave){
    //verificando se a nave ultrapassou as bordas da imagem:
    if(Math.abs(imagemNave.getLayoutX()-posicaoOriginalLayoutX)>=1150){ 
      imagemNave.setLayoutX(posicaoOriginalLayoutX);  //volta para as
      imagemNave.setLayoutY(posicaoOriginalLayoutY);  //posicoes originais
      sortearRota();    //sorteia uma nova rota
      estado = estadoInicial; //volta ao estado inicial
    } //fim do if
    
    //verifica se chegou numa posicao que precisa de um movimento diferente
    mudarEstado(posicao, qualNave, imagemNave);

    //movimentando a nave segundo seu estado:
    if(estado.equals("subindoD")){  //"subindo e indo pra direita"
      imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidade/5);
      imagemNave.setLayoutY((imagemNave.getLayoutY() - velocidade/4.5));
    }else if(estado.equals("descendoD")){ //"descendo e indo pra direita"
      imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidade/5);
      imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidade/4.5);
    } else if(estado.equals("retoD")){  //"indo reto e pra direita"
      imagemNave.setLayoutX(imagemNave.getLayoutX() + velocidade/3);
    }else if(estado.equals("subindoE")){  //"subindo e indo pra esquerda"
      imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidade/5);
      imagemNave.setLayoutY(imagemNave.getLayoutY() - velocidade/4.5);
    }else if(estado.equals("descendoE")){ //"descendo e indo pra esquerda"
      imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidade/5);
      imagemNave.setLayoutY(imagemNave.getLayoutY() + velocidade/4.5);
    }else{  //retoE = "indo reto e pra esquerda"
      imagemNave.setLayoutX(imagemNave.getLayoutX() - velocidade/3);
    }
  } //fim do metodo movimentarNave


  /* ***************************************************************
  * Metodo: mudarEstado
  * Funcao: existem 8 pontos no eixo X onde, caso a nave os ultrapasse, seu estado deve mudar.
            Esse metodo verifica se a nave passou por algum deles e quando o faz muda seu estado
            de acordo com qual nave eh
  * Parametros: ImageView imagemNave 
                int posicao (disposicaoEscolhida)
                int qualNave (o movimento de cada nave eh diferente)
  * Retorno: nenhum
  * *************************************************************** */
  public void mudarEstado(int posicao, int qualNave, ImageView imagemNave){
    //verificacoes nave1(vermelha):
    if(qualNave==1){
      if(posicao==4){ //comeca na esquerda e em cima
        //verificando se eh o primeiro ponto de mudanca
        if(imagemNave.getLayoutX()<984 && imagemNave.getLayoutX()>918) estado = "descendoE";
        else  direcoesDireitaPraEsquerda(imagemNave);
      } //fim do if ==4
      else{ //comeca na direita e em cima
        //verificando se eh o primeiro ponto de mudanca
        if(imagemNave.getLayoutX()>138 && imagemNave.getLayoutX()<=201) estado = "descendoD";
        else  direcoesEsquerdaPraDireita(imagemNave);
      } //fim do else
    }//fim das verificacoes da nave1
  
    //verificacoes nave2(azul):
    else{
      
      switch(posicao){  //posicao = disposicaoEscolhida
        case 1:{  //comeca na direita e embaixo
          //verificando se eh o primeiro ponto de mudanca
          if(imagemNave.getLayoutX()>138 && imagemNave.getLayoutX()<=201) estado = "subindoD";
          else  direcoesEsquerdaPraDireita(imagemNave);
          break;
        } //fim case 1
        case 2:{ //comeca na esquerda e em cima 
          //verificando se eh o primeiro ponto de mudanca
          if(imagemNave.getLayoutX()<984 && imagemNave.getLayoutX()>918) estado = "descendoE";
          else  direcoesDireitaPraEsquerda(imagemNave);
          break;
        } //fim case 2
        case 3:   //comeca na 
        case 4:{  //esquerda e embaixo
          //verificando se eh o primeiro ponto de mudanca
          if(imagemNave.getLayoutX()<975 && imagemNave.getLayoutX()>918) estado = "subindoE";
          else  direcoesDireitaPraEsquerda(imagemNave);
          break;
        } //fim case 4
      } //fim do switch
      
    }//fim do else (verificacoes da nave2)
  }// fim do metodo mudarEstado


  /* ***************************************************************
  * Metodo: direcoesDireitaPraEsquerda
  * Funcao: esse trecho se repete muito no processo de mudarEstado entao, para resumir mais
            o codigo foi criada essa funcao. Relembrando: existem 8 pontos no eixo X onde, 
            caso a nave os ultrapasse, seu estado deve mudar.
  * Parametros: ImageView imagemNave 
  * Retorno: nenhum
  * *************************************************************** */
 public void direcoesDireitaPraEsquerda(ImageView imagemNave){
    if(imagemNave.getLayoutX()<158){  //ponto 2[158]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      //+ resultado da posicao inicial:
      if(rotaDaNave[0]==1)
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (198 - imagemNave.getLayoutY()) *FATOR_DE_SUAVIZACAO);
      else
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (335 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoE"; //indo reto e pra esquerda
    } //fim do if do ponto 2
    
    else if(imagemNave.getLayoutX()<211) {  //ponto 3[211]
      //bifurcacao:
      if(rotaDaNave[0]==1)
        estado = "subindoE";  //subindo e pra esquerda
      else
        estado = "descendoE"; //descendo e pra esquerda
    } //fim do if do ponto 3
    
    else if(imagemNave.getLayoutX()<420 ){  //ponto 4[420]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      imagemNave.setLayoutY(imagemNave.getLayoutY() + (265 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoE"; //indo reto e pra esquerda
    } //fim do if do ponto 4
    
    else if(imagemNave.getLayoutX()<459) {  //ponto 5[459]
      //resultdo da escolha na bifurcacao
      if(rotaDaNave[1]==1)
        estado = "descendoE"; //descendo e pra esquerda
      else
        estado = "subindoE";  //subindo e pra esquerda
    } //fim do if do ponto 5
    
    else if(imagemNave.getLayoutX()<668){  //ponto 6[668]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      //+ resultado da escolha na bifurcacao:
      if(rotaDaNave[1]==1)
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (198 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      else
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (335 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoE"; //indo reto e pra esquerda
    } //fim do if do ponto 6
    
    else if(imagemNave.getLayoutX()<716) {  //ponto 7[716]
      //bifurcacao:
      if(rotaDaNave[1]==1)
        estado = "subindoE";  //subindo e pra esquerda
      else
        estado = "descendoE"; //descendo e pra esquerda
    } //fim do if do ponto 7
    
    else if(imagemNave.getLayoutX()<908){ //ponto 8[908]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      imagemNave.setLayoutY(imagemNave.getLayoutY() + (265 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoE"; //indo reto e pra esquerda
    } //fim do if do ponto 8
  
  } //fim do metodo direcoesDireitaPraEsquerda
  

  /* ***************************************************************
  * Metodo: direcoesEsquerdaPraDireita
  * Funcao: esse trecho se repete muito no processo de mudarEstado entao, para resumir mais
            o codig foi criada essa funcao. Relembrando: existem 8 pontos no eixo X onde, 
            caso a nave os ultrapasse, seu estado deve mudar.
  * Parametros: ImageView imagemNave 
  * Retorno: nenhum
  * *************************************************************** */
  public void direcoesEsquerdaPraDireita(ImageView imagemNave){

    if(imagemNave.getLayoutX()>954){    //ponto 8[954]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      //+ resultado da escolha na bifurcacao:
      if(rotaDaNave[1]==1)
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (198 - imagemNave.getLayoutY()) * 0.15);
      else
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (335 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
       estado = "retoD";  //indo reto e pra direita
    } //fim do if do ponto 8
    
    else if(imagemNave.getLayoutX()>900) {  //ponto 7[900]
      //bifurcacao:
      if(rotaDaNave[1]==1)
        estado = "subindoD";  //subindo e pra direita
      else
        estado = "descendoD"; //descendo e pra direita
    } //fim do if do ponto 7
    
    else if(imagemNave.getLayoutX()>696){ //ponto 6[696]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      imagemNave.setLayoutY(imagemNave.getLayoutY() + (265 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoD"; //indo reto e pra direita
    } //fim do if do ponto 6
    
    else if(imagemNave.getLayoutX()>621) {  //ponto 5[621]
      //resultado da escolha na bifurcacao:
      if(rotaDaNave[0]==1)
        estado = "descendoD"; //descendo e pra direita
      else
        estado = "subindoD";  //subindo e pra direita
    } //fim do if do ponto 5
    
    else if(imagemNave.getLayoutX()>439){ //ponto 4[439]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      //+ resultado da escolha na bifurcacao:
      if(rotaDaNave[0]==1)
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (198 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      else
        imagemNave.setLayoutY(imagemNave.getLayoutY() + (335 - imagemNave.getLayoutY()) * FATOR_DE_SUAVIZACAO);
      estado = "retoD"; //indo reto e pra direita
    } //fim do if do ponto 4
    
    else if(imagemNave.getLayoutX()>400 ) { //ponto 3[400]
      //bifurcacao:
      if(rotaDaNave[0]==1)
        estado = "subindoD";  //subindo e pra direita
      else
        estado = "descendoD"; //descendo e pra direita
    } //fim do if do ponto 3
    
    else if(imagemNave.getLayoutX()>201){ //ponto 2[201]
      //caso a nave desca/suba demais usa-se essa funcao para corrigir seu eixo Y
      imagemNave.setLayoutY(imagemNave.getLayoutY() + (265 - imagemNave.getLayoutY())* 0.15);
      estado = "retoD"; //indo reto e pra direita
    } //fim do if do ponto 2
    
  } //fim do metodo direcoesEsquerdaPraDireita

}// fim da classe Nave
