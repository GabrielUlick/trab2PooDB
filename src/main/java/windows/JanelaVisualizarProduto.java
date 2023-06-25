package windows;

import control.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class JanelaVisualizarProduto {

    protected ControladoraProdutos controladora;
    protected String coluna;
    protected boolean crescente;
    private Vector colunas = null;

    protected Vector obterNomeColunasTabela() {
        if (colunas == null) {
            colunas = new Vector(10);

            colunas.addElement("Código");
            colunas.addElement("Nome");
            colunas.addElement("Preco");
            colunas.addElement("Data Compra");
            colunas.addElement("Quantidade");
        }

        return colunas;
    }

    protected void limparTabelaProdutos() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    protected void preencherTabelaProdutos() {
        try {
            Vector linhas;
            linhas = controladora.obterLinhasProdutos(coluna, crescente);

            System.out.println("Legenda da ordem das colunas");
            for (int i = 0; i < colunas.size() - 1; i++) {
                System.out.print(colunas.get(i) + "   -   ");
            }
            System.out.print(colunas.get(colunas.size() - 1));
            System.out.println("");
            System.out.println("===========================================================================================================");

            int numLinhas = linhas.size();
            for (int i = 0; i < numLinhas; i++) {
                Vector linha = (Vector) linhas.get(i);
                for (int j = 0; j < colunas.size() - 1; j++) {
                    System.out.print(linha.get(j) + "   -   ");
                }
                String valor = (String) linha.get(colunas.size() - 1);
                if (valor.equals("")) {
                    System.out.print(" null ");
                } else {
                    System.out.print(valor);
                }
                System.out.println("");
                System.out.println("----------------------------------------------------------------------------------------------------------");

            }
            System.out.println("===========================================================================================================");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JanelaVisualizarProduto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(JanelaVisualizarProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void montarCabecalho() {

        System.out.println(" Escolha uma das opções abaixo:");
        System.out.println(" 0) Sair");
        int desloque = 0;
        for (int i = 0; i < colunas.size(); i++) {
            System.out.println(" " + (i + 1 + desloque) + ") " + colunas.get(i) + " - Crescente");
            System.out.println(" " + (i + 2 + desloque) + ") " + colunas.get(i) + " - Decrescente");
            desloque++;
        }

    }

    protected void montarLayout() {
        // Montar cabeçalho
        System.out.println("===========================================================================================================");
        this.montarCabecalho();
        System.out.println("===========================================================================================================");
        // Preenchendo a Tabela de Produtos
        this.preencherTabelaProdutos();
    }

    public JanelaVisualizarProduto() {
        controladora = new ControladoraProdutos();
        this.obterNomeColunasTabela();
        this.coluna = colunas.get(0).toString();
        this.crescente = true;

    }

    public void executar() {
        Scanner leitorOpcao = new Scanner(System.in);
        int opcao;
        do {
            this.montarLayout();

            opcao = leitorOpcao.nextInt();

            limparTabelaProdutos();
            // sair
            if (opcao == 0) {
                break;
            }

            this.coluna = (String) this.colunas.get((opcao - 1) / 2);
            if ((opcao % 2) == 1) {
                this.crescente = true;
            } else {
                this.crescente = false;
            }

        } while (true);
        leitorOpcao.close();
    }
}
