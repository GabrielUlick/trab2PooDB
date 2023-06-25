package windows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JanelaPrincipalProduto extends JanelaVisualizarProduto {

    @Override
    protected void montarCabecalho() {
        super.montarCabecalho();
        Vector colunas = this.obterNomeColunasTabela();
        int qtMenus = colunas.size() * 2;
        System.out.println(" " + (qtMenus + 1) + ") Incluir Novo Produto");
        System.out.println(" " + (qtMenus + 2) + ") Alterar Produto");
        System.out.println(" " + (qtMenus + 3) + ") Excluir Produto");
    }

    private Vector lerDados() {
        System.out.println("===========================================================================================================");
        System.out.println("Digite os seguintes dados: ");
        Vector colunas = this.obterNomeColunasTabela();
        Scanner leitor = new Scanner(System.in);
        Vector dados = new Vector();
        String valor;
        for (int i = 0; i < colunas.size(); i++) {
            System.out.println(colunas.get(i));
            valor = leitor.next();
            dados.add(valor);
        }
        System.out.println("");
        System.out.println("===========================================================================================================");
        return dados;
    }

    @Override
    public void executar() {
        Vector colunas = this.obterNomeColunasTabela();
        Scanner leitorOpcao = new Scanner(System.in);
        int opcao;
        int qtMenus = colunas.size() * 2;
        do {
            this.montarLayout();

            opcao = leitorOpcao.nextInt();
            // sair
            if (opcao == 0) {
                break;
            } // ordenação
            else if (opcao <= qtMenus) {
                limparTabelaProdutos();
                this.coluna = (String) colunas.get((opcao - 1) / 2);
                if ((opcao % 2) == 1) {
                    this.crescente = true;
                } else {
                    this.crescente = false;
                }
            } // incluir
            else if (opcao == qtMenus + 1) {
                limparTabelaProdutos();
                Vector dados = lerDados();
                try {
                    this.controladora.inserirNovoProduto(dados);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                }

            } // alterar
            else if (opcao == qtMenus + 2) {
                System.out.println("Digite o número da linha que deseja alterar: ");
                this.controladora.setMarcador(leitorOpcao.nextInt());
                limparTabelaProdutos();
                Vector dados = lerDados();
                try {
                    this.controladora.alterarProduto(dados);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                }

            } // excluir
            else if (opcao == qtMenus + 3) {
                System.out.println("Digite o número da linha que deseja excluir: ");
                this.controladora.setMarcador(leitorOpcao.nextInt());
                try {
                    this.controladora.excluirProduto();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JanelaPrincipalProduto.class.getName()).log(Level.SEVERE, null, ex);
                }

                limparTabelaProdutos();

            } else {
                System.out.println("Falha GERAL");
            }

        } while (true);
        leitorOpcao.close();
    }
}
