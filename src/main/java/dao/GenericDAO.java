package dao;

import domain.Produto;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class GenericDAO {

    //CRUD:
    //----------------------------------------------------------------    
    public void inserir(Object obj) throws HibernateException {
        Session sessao = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.beginTransaction();

            // OPERAÇÕES
            sessao.save(obj);

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException erro) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(erro);
        }
    }

    public void alterar(Object obj) throws HibernateException {
        Session sessao = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.beginTransaction();

            // OPERAÇÕES
            sessao.update(obj);

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException erro) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(erro);
        }
    }

    public void excluir(Object obj) throws HibernateException {
        Session sessao = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.beginTransaction();

            // OPERAÇÕES
            sessao.delete(obj);

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException erro) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(erro);
        }
    }

    public List listar(Class classe) throws HibernateException {
        Session sessao = null;
        List lista = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.beginTransaction();

            // OPERAÇÕES
            CriteriaQuery consulta = sessao.getCriteriaBuilder().createQuery(classe);
            consulta.from(classe);
            lista = sessao.createQuery(consulta).getResultList();

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException erro) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(erro);
        }
        return lista;
    }

    // -----------------------------------------------
    // Se não existir no banco retorna NULL
    // -----------------------------------------------
    public Object get(Class classe, int id) throws HibernateException {
        Session sessao = null;
        Object objReturn = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.getTransaction().begin();

            objReturn = sessao.get(classe, id);

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException ex) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(ex);
        }
        return objReturn;
    }

    // -----------------------------------------------------
    //  Se não existir no banco, retorna uma EXCEÇÃO
    // ----------------------------------------------------
    // Sempre retorna um PROXY e não o objeto em si.
    // PROXY é apenas uma referência ao objeto. 
    // Ele será realmente carregado quando o primeiro acesso
    // for feito ao objeto
    // ENTÃO, por isso que colocamos um primeiro acesso ao objeto 
    // dentro dessa função, como o método toString (somente para teste)
    public Object load(Class classe, int id) throws HibernateException {
        Session sessao = null;
        Object objReturn = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.getTransaction().begin();

            objReturn = sessao.load(classe, id);
            objReturn.toString();

            sessao.getTransaction().commit();
            sessao.close();
        } catch (HibernateException ex) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(ex);
        }
        return objReturn;
    }

    public List<Produto> obterProdutosOrdenados(String coluna, boolean crescente) throws HibernateException {
        Session sessao = null;
        try {
            sessao = ConexaoHibernate.getSessionFactory().openSession();
            sessao.beginTransaction();

            CriteriaBuilder builder = sessao.getCriteriaBuilder();
            CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
            Root<Produto> root = query.from(Produto.class);
            query.select(root);

            // Aplicar a ordenação com base nos critérios
            switch (coluna) {
                case "codigo" -> {
                    if (crescente) {
                        query.orderBy(builder.asc(root.get("codigo")));
                    } else {
                        query.orderBy(builder.desc(root.get("codigo")));
                    }
                }
                case "nome" -> {
                    if (crescente) {
                        query.orderBy(builder.asc(root.get("nome")));
                    } else {
                        query.orderBy(builder.desc(root.get("nome")));
                    }
                }
                case "preco" -> {
                    if (crescente) {
                        query.orderBy(builder.asc(root.get("preco").as(Double.class)));
                    } else {
                        query.orderBy(builder.desc(root.get("preco").as(Double.class)));
                    }
                }
                case "datacompra" -> {
                    if (crescente) {
                        query.orderBy(builder.asc(root.get("datacompra")));
                    } else {
                        query.orderBy(builder.desc(root.get("datacompra")));
                    }
                }
                 case "quantidade" -> {
                    if (crescente) {
                        query.orderBy(builder.asc(root.get("quantidade")));
                    } else {
                        query.orderBy(builder.desc(root.get("quantidade")));
                    }
                }
                default -> {
                }
            }

            List<Produto> produtos = sessao.createQuery(query).getResultList();

            sessao.getTransaction().commit();
            sessao.close();

            return produtos;
        } catch (HibernateException erro) {
            if (sessao != null) {
                sessao.getTransaction().rollback();
                sessao.close();
            }
            throw new HibernateException(erro);
        }
    }

}
