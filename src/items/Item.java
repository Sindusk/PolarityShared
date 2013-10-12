/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import stats.StatList;

/**
 *
 * @author SinisteRing
 */
public class Item {
    protected String icon;      // Icon (without path) to be used by the item.
    protected StatList stats;  // Stat table for tooltip & effects.
    
    // Default constructor.
    public Item(String icon){
        this.icon = icon;
    }
}
