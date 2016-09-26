package net.anotheria.strel.hibernate.magicsquares;

import net.anotheria.strel.basic.magicsquares.MagicSquare;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Used for operating database where all magic squares are stored.
 *
 * @author Strel97
 */
public class MagicSquaresManager {

    private SessionFactory factory;
    private Transaction transaction;
    private Logger log = Logger.getLogger(MagicSquaresManager.class);


    public MagicSquaresManager() {
        BasicConfigurator.configure();

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            log.error(String.format("Failed to create session factory object: %s", ex));
        }
    }

    /**
     * Saves {@link MagicSquare} to database.
     * @param square    Square to save
     * @return          Id of record in database
     */
    public Integer saveSolution(MagicSquare square) {

        Transaction transaction = null;
        Integer squareID = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            squareID = (Integer) session.save(square);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to process save transaction: " + ex);
        }

        return squareID;
    }

    public void saveAllSquares(List<MagicSquare> squares) {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            for (MagicSquare square : squares) {
                session.save(square);
            }
            transaction.commit();
        }
        catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to process save transaction: " + ex);
        }
    }

    public List<MagicSquare> getAllSquares() {
        try (Session session = factory.openSession()) {
            return (List<MagicSquare>) session.createQuery("from MagicSquare").list();
        }
    }

    public void deleteSquares() {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            int recordsDeleted = session.createQuery("delete from MagicSquare").executeUpdate();
            transaction.commit();

            log.info(String.format("Deleted %d squares.", recordsDeleted));
        }
        catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to delete squares: " + ex.getMessage());
        }
    }

    public MagicSquare getSquareById(int squareId) {
        try (Session session = factory.openSession()) {
            return session.get(MagicSquare.class, squareId);
        }
    }

    public void deleteSquareById(int squareId) {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            MagicSquare square = session.get(MagicSquare.class, squareId);

            if (square != null) {
                session.delete(square);
                log.info(String.format("Square with id = %d was deleted from database.", squareId));
            }

            transaction.commit();
        }
        catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error(String.format("Can't delete square with id = %d: %s", squareId, ex.getMessage()));
        }
    }

    public List<MagicSquare> findSquares(String pattern) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from MagicSquare s where str(s.square) like :pattern")
                    .setParameter("pattern", pattern + "%").list();
        }
    }

    /**
     * Stops Magic square manager
     */
    public void stop() {
        factory.close();
    }
}
