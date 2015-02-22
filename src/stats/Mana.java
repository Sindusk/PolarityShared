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
