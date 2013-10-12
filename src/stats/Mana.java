/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

/**
 *
 * @author SinisteRing
 */
public class Mana extends StatWithMax{
    public Mana(float base, float max){
        super(base, max);
        priority = 99;
    }
}
