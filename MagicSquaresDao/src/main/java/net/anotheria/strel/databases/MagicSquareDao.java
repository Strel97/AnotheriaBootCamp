package net.anotheria.strel.databases;

import net.anotheria.strel.basic.magicsquares.MagicSquare;

import java.util.List;

/**
 * @author Strel97
 */
public interface MagicSquareDao {

    void saveSquare(MagicSquare square);

    void saveSquares(List<MagicSquare> squares);

    MagicSquare getSquareById(int id);

    List<MagicSquare> getAllSquares();

    void deleteSquareById(int id);

    int deleteAllSquares();

    List<MagicSquare> findSquares(String pattern);
}
