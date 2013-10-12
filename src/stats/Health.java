/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

/**
 *
 * @author SinisteRing
 */
public class Health extends StatWithMax{
    public Health(float base, float max){
        super(base, max);
        priority = 100;
    }
}
