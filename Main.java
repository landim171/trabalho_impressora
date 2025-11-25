import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {

        // Caminho completo para a DLL
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\ferreira_luan\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AvancaPapel(int linhas);

        int StatusImpressora(int param);

        int AbreGavetaElgin(int i, int i1, int i2);

        int AbreGaveta(int pino, int ti, int tf);

        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        int ModoPagina();

        int LimpaBufferModoPagina();

        int ImprimeModoPagina();

        int ModoPadrao();

        int PosicaoImpressaoHorizontal(int posicao);

        int PosicaoImpressaoVertical(int posicao);

        int ImprimeXMLSAT(String dados, int param);

        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static void configurarConexao() {
        if (!conexaoAberta) {
            tipo = Integer.parseInt(capturarEntrada("Digite o tipo de conexão (ex: 1 para USB, 2 para serial, etc.): "));
            modelo = capturarEntrada("Digite o modelo da impressora (ex: 'i9'): ");
            conexao = capturarEntrada("Digite a porta de conexão (ex: 'lp0' para Linux ou 'COM3' para Windows): ");
            parametro = Integer.parseInt(capturarEntrada("Digite o parâmetro adicional (ex: 0 para padrão): "));
            System.out.println("Parâmetros de conexão configurados com sucesso.");
        } else {
            System.out.println("Parâmetros de conexão já configurados. Pronto para abrir a conexão.");
        }
    }

    public static void abrirConexao() {
        if (!conexaoAberta){
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);

            if (retorno == 0){
                conexaoAberta = true;
                System.out.println("Conexao aberta com sucess");
            } else {
                System.out.println("ERRO. Retorno: " + retorno);
            }
        } else {
            System.out.println("\n-CONEXÃO JÁ ESTÁ ABERTA-");
        }
    }


    public static void fecharConexao () {

    }
    public static void ImpressaoTexto() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);
            if (retorno == 0) {
                System.out.println("Impressao realizada  com sucesso.");
            } else {
                System.out.println("Erro ao imprimir. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Já foi impresso");
        }
    }
    public static void ImpressaoQRCode() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);
            if (retorno == 0) {
                System.out.println("Impressao do Qr Code realizada  com sucesso.");
            } else {
                System.out.println("Erro ao imprimir. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Já foi impresso");
        }
    }

    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
            if (retorno == 0) {
                System.out.println("Impressao do codigo de barras realizada  com sucesso.");
            } else {
                System.out.println("Erro ao imprimir Código de barras erro: " + retorno);
            }
        } else {
            System.out.println("Já foi impresso");
        }
    }

    public static void AvancaPapel() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(2);
            if (retorno == 0) {
                System.out.println("Avanço papel realizada  com sucesso.");
            } else {
                System.out.println("Erro ao avançar papel erro: " + retorno);
            }
        } else {
            System.out.println("Já foi avançado");
        }
    }
    public static void AbreGavetaElgin() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin(1,50,50);
            if (retorno == 0) {
                System.out.println("Avanço papel realizada  com sucesso.");
            } else {
                System.out.println("Erro ao avançar papel erro: " + retorno);
            }
        } else {
            System.out.println("Já foi avançado");
        }
    }

    public static void imprimeXMLSAT() {
        if (conexaoAberta) {

            String dados = "path=C:\\Users\\rodrigues_geovanna\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\Java-Aluno EM\\XMLSAT.xml";

            int ret = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, 0);

            if(ret == 0) {
                System.out.println("Impressao realizada  com sucesso.");
            }else {
                System.out.println("Erro . Retorno"+ ret);
            }
        }

        else {
            System.out.println("Precisa abrir a conexão primeiro");
        }
    }

    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {

            String dados = "path=C:\\Users\\rodrigues_geovanna\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\Java-Aluno EM\\CANC_SAT.xml";

            String assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

            int ret = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, assQRCode,0);

            if(ret == 0) {
                System.out.println("Impressao realizada  com sucesso.");
            }else {
                System.out.println("Erro . Retorno"+ ret);
            }
        }

        else {
            System.out.println("Precisa abrir a conexão primeiro");
        }
    }
    public static void AbreGaveta() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);

            if (retorno == 0) {
                System.out.println("Avanço papel realizada  com sucesso.");
            } else {
                System.out.println("Erro ao avançar papel erro: " + retorno);
            }
        } else {
            System.out.println("Já foi avançado");
        }
    }

    public static void SinalSonoro() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(4,5,5);

            if (retorno == 0) {
                System.out.println("Avanço papel realizada  com sucesso.");
            } else {
                System.out.println("Erro ao avançar papel erro: " + retorno);
            }
        } else {
            System.out.println("Já foi avançado");
        }
    }



    //criar o restante das funçoes aqui!

	/* - `ImpressaoTexto()`          ("Teste de impressao", 1, 4, 0);
	- `Corte()/`						(2)  usar sempre após a impressao de algum documento
	- `ImpressaoQRCode()`            ("Teste de impressao", 6, 4)
	- `ImpressaoCodigoBarras()`    (8, "{A012345678912", 100, 2, 3)
	- `AvancaPapel()`                 (2)  usar sempre após a impressao de algum documento
	- `AbreGavetaElgin()`            (1, 50, 50)
	- `AbreGaveta()/`                  (1, 5, 10)
	- `SinalSonoro()`				 (4,5,5)
	- `ImprimeXMLSAT()`
	- `ImprimeXMLCancelamentoSAT()`    (assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";)
	*/


    public static void main (String[]args){
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Canc SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");
            System.out.println("--------------------------------------");
            //criar o restante do MENU


            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            }

            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    break;

                case "3":
                    ImpressaoTexto();
                    ImpressoraDLL.INSTANCE.Corte(3);
                    break;
                case "4":
                    ImpressaoQRCode();
                    ImpressoraDLL.INSTANCE.Corte(3);
                    break;
                case "5":
                    ImpressaoCodigoBarras();
                    ImpressoraDLL.INSTANCE.Corte(3);
                    break;

                case "6":
                    imprimeXMLSAT();
                    ImpressoraDLL.INSTANCE.Corte(3);
                    break;

                case "7":
                    ImprimeXMLCancelamentoSAT();
                    ImpressoraDLL.INSTANCE.Corte(3);
                    break;

                case "8":
                    AbreGavetaElgin();
                    break;

                case "9":
                    AbreGaveta();
                    break;
                case "10":
                    SinalSonoro();
                    break;
                default:
                    System.out.println("opção invalida");
            }
        }

        scanner.close();
    }

    private static String lerArquivoComoString (String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}

