package dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConexaoHibernate{
    //Conexao com Banco de Dados
    private static final SessionFactory sessionFactory;   
    static{
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();          
        }catch(HibernateException ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
