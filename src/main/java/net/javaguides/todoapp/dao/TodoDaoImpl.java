package net.javaguides.todoapp.dao;

import net.javaguides.todoapp.model.Todo;
import net.javaguides.todoapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * This DAO class provides CRUD database operations for the table todos in the
 * database.
 *
 * @author Ramesh Fadatare
 */

public class TodoDaoImpl implements TodoDao {

    private final Session session;
    private final Transaction transaction;

    private final CriteriaBuilder builder;

    public TodoDaoImpl() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        builder = session.getCriteriaBuilder();

    }

    @Override
    public void insertTodo(Todo todo) {
        session.save(todo);
        transaction.commit();
    }

    @Override
    public Todo selectTodo(long todoId) {
        CriteriaQuery<Todo> criteriaQuery = builder.createQuery(Todo.class);
        Root<Todo> root = criteriaQuery.from(Todo.class);
        criteriaQuery.select(root).where(builder.equal(root.get("id"), todoId));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Todo> selectAllTodos() {
        CriteriaQuery<Todo> criteriaQuery = builder.createQuery(Todo.class);
        Root<Todo> root = criteriaQuery.from(Todo.class);
        criteriaQuery.select(root);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean deleteTodo(int id) {
        Todo todo = this.selectTodo(id);
        if (todo != null) {
            session.delete(todo);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTodo(Todo todo) {
        Todo temp = this.selectTodo(todo.getId());
        if (temp != null) {
            session.merge(todo);
            return true;
        }
        return false;
    }
}
