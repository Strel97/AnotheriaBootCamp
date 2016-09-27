package net.anotheria.strel.hibernate.magicsquares;


import net.anotheria.strel.basic.magicsquares.MagicSquare;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents magic square solution in form, it will be
 * stored in database.
 *
 * @author Strel97
 */
@Entity
@Table( name = "squares" )
public class MagicSquareEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int     id;

    /**
     * Size of magic square
     */
    @Column(name = "square_size")
    private int     size;

    /**
     * Values of square in form of string
     */
    @Column(name = "square")
    private String  square;


    public MagicSquareEntity() {
    }

    public MagicSquareEntity(int size, String square) {
        this.size = size;
        this.square = square;
    }

    public MagicSquareEntity(MagicSquare square) {
        this(square.getSize(), square.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public MagicSquare createSquare() {
        return new MagicSquare(square);
    }
}
