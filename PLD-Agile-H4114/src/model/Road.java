package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Road {

    private Double length;
    private String name;
    private Integer origin;
    private Integer destination;

    /**
     * Default constructor
     */
    public Road() {


    }

    public Road(Integer myInter1,Integer myInter2,String myName,Double myLength){
        this.length = myLength;
        this.name = myName;
        this.origin = myInter1;
        this.destination = myInter2;
    }

    /**
     * 
     */


}