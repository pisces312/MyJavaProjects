/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution2;

import java.util.HashSet;

/**
 *
 * @author Administrator
 */
public class GSSolution2 {
    //�ü��ϱ������ɵ�����

    HashSet<Man> freeMen;

    public GSSolution2() {
        freeMen = new HashSet<Man>();
//        Woman[] women={new Woman("Amy"),new Woman("Berths"),new Woman("Clare"),new Woman("Diane"),new Woman("Erika")};
//        Man[] men={new Man("Victor")};
        Woman Amy = new Woman("Amy");
        Woman Berths = new Woman("Berths");
        Woman Clare = new Woman("Clare");
        Woman Diane = new Woman("Diane");
        Woman Erika = new Woman("Erika");
        Man Victor = new Man("Victor");
        Man Wyatt = new Man("Wyatt");
        Man Xavier = new Man("Xavier");
        Man Yancey = new Man("Yancey");
        Man Zeus = new Man("Zeus");
        Victor.setPrefList(new Human[]{Berths, Amy, Diane, Erika, Clare});
        Wyatt.setPrefList(new Human[]{Diane, Berths, Amy, Clare, Erika});
        Xavier.setPrefList(new Human[]{Berths, Erika, Clare, Diane, Amy});
        Yancey.setPrefList(new Human[]{Amy, Diane, Clare, Berths, Erika});
        Zeus.setPrefList(new Human[]{Berths, Diane, Amy, Erika, Clare});

        Amy.setPrefList(new Human[]{Zeus, Victor, Wyatt, Yancey, Xavier});
        Berths.setPrefList(new Human[]{Xavier, Wyatt, Yancey, Victor, Zeus});
        Clare.setPrefList(new Human[]{Wyatt, Xavier, Yancey, Victor, Zeus});
        Diane.setPrefList(new Human[]{Victor, Zeus, Yancey, Xavier, Wyatt});
        Erika.setPrefList(new Human[]{Yancey, Wyatt, Zeus, Xavier, Victor});

        freeMen.add(Victor);
        freeMen.add(Wyatt);
        freeMen.add(Xavier);
        freeMen.add(Yancey);
        freeMen.add(Zeus);
        // m,m2,w;
        Woman w;
        Man m, m2;
//        Iterator<Integer> freeMenItr=freeMen.iterator();
        //������������˲��Ҹ���������û��������Ů����飬��ִ��ѭ��
        while (!freeMen.isEmpty() && (m = freeMen.iterator().next()) != null) {
            //��w��m�����ȱ���m��û���������������Ů��
            w = m.getNextWoman();
            m2 = w.getCurrentMan();
            if (m2 == null) {
//                matchResult[m]=w;
                w.setCurrentMan(m);
                //��ʱm��������
                freeMen.remove(m);

            } else {
                //�����ߵķ���ǰ
                if (w.getRank(m2) < w.getRank(m)) {
                    //m��������
                } else {
                    w.setCurrentMan(m);
//                    matchResult[m]=w;
                    freeMen.remove(m);
                    freeMen.add(m2);
                }

            }
        }
        System.out.println(Amy.getName() + " " + Amy.getCurrentMan().getName());
        System.out.println(Berths.getName() + " " + Berths.getCurrentMan().getName());
        System.out.println(Clare.getName() + " " + Clare.getCurrentMan().getName());
        System.out.println(Diane.getName() + " " + Diane.getCurrentMan().getName());
        System.out.println(Erika.getName() + " " + Erika.getCurrentMan().getName());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new GSSolution2();
    }
}
