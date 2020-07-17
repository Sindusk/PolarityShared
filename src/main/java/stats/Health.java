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
