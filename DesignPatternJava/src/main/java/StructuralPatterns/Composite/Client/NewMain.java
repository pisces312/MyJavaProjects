/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StructuralPatterns.Composite.Client;

import StructuralPatterns.Composite.Composite.CompositeEquipment;
import StructuralPatterns.Composite.Leaf.Leaf;

/**
 *
 * @author pisces312
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Leaf floppyDisk = new Leaf("3.5寸软盘", 50);
        CompositeEquipment cabinet = new CompositeEquipment("PC机箱", 400);
        CompositeEquipment mainboard = new CompositeEquipment("主板", 200);
        CompositeEquipment bus = new CompositeEquipment("总线", 100);
        bus.add(new Leaf("100M Ethenet PCI Card", 30));
        bus.add(new Leaf("2GB DDR3", 100));
        mainboard.add(bus);
        cabinet.add(mainboard);
        cabinet.add(floppyDisk);
        System.out.println(cabinet.getPrices());

    }
}
