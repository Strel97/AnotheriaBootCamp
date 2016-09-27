package net.anotheria.strel.hibernate.magicsquares;

import net.anotheria.strel.basic.magicsquares.MagicSquare;
import net.anotheria.strel.databases.MagicSquareDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Strel97
 */
@Repository
@Transactional
public class SpringHibernateMagicSquareManager implements MagicSquareDao {
    @Autowired
    SessionFactory sessionFactory;


    @Override
    public void saveSquare(MagicSquare square) {
        sessionFactory.getCurrentSession().saveOrUpdate(new MagicSquareEntity(square));
    }

    @Override
    public void saveSquares(List<MagicSquare> squares) {
        Session currentSession = sessionFactory.getCurrentSession();
        for (MagicSquare square : squares) {
            currentSession.saveOrUpdate(new MagicSquareEntity(square));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MagicSquare> getAllSquares() {
        Session currentSession = sessionFactory.getCurrentSession();
        return castList( currentSession.createQuery("from MagicSquareEntity").list() );
    }

    @Override
    public MagicSquare getSquareById(int id) {
        MagicSquareEntity entity = sessionFactory.getCurrentSession().load(MagicSquareEntity.class, id);
        return entity != null ? entity.createSquare() : null;
    }

    @Override
    public void deleteSquareById(int id) {
        MagicSquareEntity entity = sessionFactory.getCurrentSession().load(MagicSquareEntity.class, id);
        if (entity != null) {
            sessionFactory.getCurrentSession().delete(entity);
        }
    }

    @Override
    public int deleteAllSquares() {
        int recordsDeleted = sessionFactory.getCurrentSession().createQuery("delete from MagicSquareEntity").executeUpdate();
        return recordsDeleted;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MagicSquare> findSquares(String pattern) {
        return castList( sessionFactory.getCurrentSession()
                .createQuery("from MagicSquareEntity where square like :pattern")
                .setParameter("pattern", pattern).list()
        );
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
