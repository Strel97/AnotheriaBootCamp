package net.anotheria.strel.hibernate.magicsquares;

import net.anotheria.strel.basic.magicsquares.MagicSquare;
import net.anotheria.strel.databases.MagicSquareDao;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for operating database where all magic squares are stored.
 * @author Strel97
 */
public class MagicSquaresManager implements MagicSquareDao {

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
     */
    @Override
    public void saveSquare(MagicSquare square) {

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new MagicSquareEntity(square));
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to process save transaction: " + ex);
        }
    }

    /**
     * Saves all {@link MagicSquare} from the list
     * to database.
     *
     * @param squares   List of squares
     */
    @Override
    public void saveSquares(List<MagicSquare> squares) {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            for (MagicSquare square : squares) {
                session.save(new MagicSquareEntity(square));
            }
            transaction.commit();
        }
        catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to process save transaction: " + ex);
        }
    }

    /**
     * Returns all squares from database in form of list.
     * @return  List of {@link MagicSquare}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<MagicSquare> getAllSquares() {
        try (Session session = factory.openSession()) {
            return castList( session.createQuery("from MagicSquareEntity").list() );
        }
    }

    /**
     * Deletes all squares from database.
     * @return Records deleted
     */
    @Override
    public int deleteAllSquares() {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            int recordsDeleted = session.createQuery("delete from MagicSquareEntity").executeUpdate();
            transaction.commit();

            log.info(String.format("Deleted %d squares.", recordsDeleted));
            return recordsDeleted;
        }
        catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();

            log.error("Failed to delete squares: " + ex.getMessage());
        }

        return 0;
    }

    /**
     * Obtains square with given id from database and returns it.
     * @param squareId  Square id
     * @return          {@link MagicSquare}
     */
    @Override
    public MagicSquare getSquareById(int squareId) {
        try (Session session = factory.openSession()) {
            MagicSquareEntity entity = session.get(MagicSquareEntity.class, squareId);
            return entity != null ? entity.createSquare() : null;
        }
    }

    /**
     * Deletes square with given id from database.
     * @param squareId  Square id
     */
    @Override
    public void deleteSquareById(int squareId) {
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            MagicSquareEntity square = session.get(MagicSquareEntity.class, squareId);

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

    /**
     * Finds all squares by given pattern. Comparison is done by
     * values of square.
     *
     * @param pattern   Square pattern
     * @return          List of {@link MagicSquare}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<MagicSquare> findSquares(String pattern) {
        try (Session session = factory.openSession()) {
            return castList( session.createQuery("from MagicSquareEntity where square like :pattern")
                    .setParameter("pattern", '%' + pattern + '%').list() );
        }
    }

    /**
     * Stops Magic square manager
     */
    public void stop() {
        factory.close();
    }

    /**
     * Casts the list of {@link MagicSquareEntity} used in hibernate
     * to list of {@link MagicSquare} used in all other cases.
     *
     * @param entities  List of {@link MagicSquareEntity}
     * @return          List of {@link MagicSquare}
     */
    private List<MagicSquare> castList(List<MagicSquareEntity> entities) {
        List<MagicSquare> squares = new ArrayList<>();
        for (MagicSquareEntity entity : entities) {
            squares.add(entity.createSquare());
        }

        return squares;
    }
}
