package stats;

/**
 *
 * @author SinisteRing
 */
public class Mana extends Resource{
    public Mana(float base, float max){
        super(base, max);
        priority = 99;
    }
}
