package control;

import dao.*;
import domain.Produto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ControladoraProdutos {

    private Vector<Produto> Produtos;
    private int marcador;
    private GenericDAO ProdutoDao;

    private String obterNomeColunaBanco(String coluna) {
        if (coluna.equals("Código")) {
            return "codigo";
        }
        if (coluna.equals("Nome")) {
            return "nome";
        }
        if (coluna.equals("Preco")) {
            return "preco";
        }
        if (coluna.equals("Data Compra")) {
            return "datacompra";
        }
        if (coluna.equals("Quantidade")) {
            return "quantidade";
        }
        return "id";
    }

    public ControladoraProdutos() {
        ConexaoHibernate.getSessionFactory();
        this.ProdutoDao = new GenericDAO();
    }

    private void atualizarProduto(Produto Produto, Vector linha) {
        Produto.setCodigo(linha.get(0).toString());
        Produto.setNome(linha.get(1).toString());
        Produto.setPreco(Double.parseDouble(linha.get(2).toString()));
        Produto.setDatacompra(linha.get(3).toString());
        Produto.setQuantidade(Integer.parseInt(linha.get(4).toString()));
    }

    private Vector criarLinhaProduto(Produto Produto) {
        Vector linha = new Vector();
        linha.addElement(Produto.getCodigo());
        linha.addElement(Produto.getNome());
        linha.addElement(String.valueOf(Produto.getPreco())); // Converter para String
        linha.addElement(Produto.getDatacompra());
        linha.addElement(String.valueOf(Produto.getQuantidade())); // Converter para String
        return linha;
    }

    public void inserirNovoProduto(Vector linha) throws FileNotFoundException, IOException, ClassNotFoundException {
//        Produto Produto = new Produto();
//        this.atualizarProduto(Produto, linha);
//        this.Produtos.add(Produto);
//        ProdutoDao.salvarProdutos(this.Produtos);
//        public Produto(String codigo, String nome, String datacompra, double preco, int quantidade

        String codigo = (String) linha.get(0);
        String nome = (String) linha.get(1);
        double preco = Double.parseDouble((String) linha.get(2));
        String datacompra = (String) linha.get(3);
        int quantidade = Integer.parseInt((String) linha.get(4));
        Produto produto = new Produto(codigo, nome, datacompra, preco, quantidade);
        ProdutoDao.inserir(produto);
        this.Produtos.add(produto);
    }

    public void setMarcador(int marcador) {
        this.marcador = marcador;
    }

    public void alterarProduto(Vector linha) throws FileNotFoundException, IOException, ClassNotFoundException {
        Produto produto = Produtos.get(marcador);
        this.atualizarProduto(produto, linha);
        ProdutoDao.alterar(produto);
    }

    public void excluirProduto() throws FileNotFoundException, IOException, ClassNotFoundException {
        Produto produto = Produtos.get(marcador);
        Produtos.remove(marcador);
        ProdutoDao.excluir(produto);
    }

    private Vector<Produto> obterProdutos(String coluna, boolean crescente) throws FileNotFoundException, IOException, ClassNotFoundException {
        String nomeColunaBanco = this.obterNomeColunaBanco(coluna);

        ArrayList<Produto> produtosList = (ArrayList<Produto>) ProdutoDao.obterProdutosOrdenados(nomeColunaBanco, crescente);
        Produtos = new Vector<>(produtosList);
        return Produtos;
    }

    public Vector obterLinhasProdutos(String coluna, boolean crescente) throws FileNotFoundException, IOException, ClassNotFoundException {
        Vector<Produto> Produtos = obterProdutos(coluna, crescente);
        Vector linhas = new Vector();
        // Montando as linhas
        for (int i = 0; i < Produtos.size(); i++) {
            Produto Produto = Produtos.get(i);
            linhas.addElement(this.criarLinhaProduto(Produto));
        }
        return linhas;
    }
}
