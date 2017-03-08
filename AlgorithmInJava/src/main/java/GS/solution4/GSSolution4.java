/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GS.solution4;

import java.util.HashSet;

/**
 *
 * @author Administrator
 */
public class GSSolution4 {
    //�ü��ϱ������ɵ�����

    HashSet<Human> freeHuman;

    public GSSolution4() {
        freeHuman = new HashSet<Human>();
//        Human[] women={new Human("Amy"),new Human("Berths"),new Human("Clare"),new Human("Diane"),new Human("Erika")};
//        Human[] men={new Human("Victor"),new Human("Wyatt"),new Human("Xavier"),new Human("Yancey"),new Human("Zeus")};
        Human Amy = new Human("Amy");
        Human Berths = new Human("Berths");
        Human Clare = new Human("Clare");
        Human Diane = new Human("Diane");
        Human Erika = new Human("Erika");
        Human Victor = new Human("Victor");
        Human Wyatt = new Human("Wyatt");
        Human Xavier = new Human("Xavier");
        Human Yancey = new Human("Yancey");
        Human Zeus = new Human("Zeus");

        Human[] women = {Amy,Berths,Clare,Diane,Erika};
        Human[] men = {Victor,Wyatt,Xavier,Yancey,Zeus};
//        Victor.setPrefList(new Human[]{women[1],women[0],women[3], women[[4], women[2]});
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
//��������
        freeHuman.add(Victor);
        freeHuman.add(Wyatt);
        freeHuman.add(Xavier);
        freeHuman.add(Yancey);
        freeHuman.add(Zeus);
        resolve();
        printResult(women);
        //Ů������
        freeHuman.add(Amy);
        freeHuman.add(Berths);
        freeHuman.add(Clare);
        freeHuman.add(Diane);
        freeHuman.add(Erika);
        resolve();
        printResult(men);


    }

    public void resolve() {
        Human w, m, m2;
        //������������˲��Ҹ���������û��������Ů����飬��ִ��ѭ��
        while (!freeHuman.isEmpty() && (m = freeHuman.iterator().next()) != null) {
            //��w��m�����ȱ���m��û���������������Ů��
            w = m.getNext();
            m2 = w.getCurrent();
            if (m2 == null) {
                w.setCurrent(m);
                //��ʱm��������
                freeHuman.remove(m);

            } else {
                //�������m
                if (w.getRank(m2)>w.getRank(m)) {
                    w.setCurrent(m);
                    freeHuman.remove(m);
                    freeHuman.add(m2);
                }

            }
        }
    }

    public void printResult(Human[] human) {
        System.out.println("===============");
        for(int i=0;i<human.length;i++){
//            System.out.println(human[i].getName()+" "+human[i].getCurrent().getName());
            System.out.println(human[i].getCurrent().getName()+" "+human[i].getName());
        }
        System.out.println("===============");
//        System.out.println(Amy.getName() + " " + Amy.getCurrent().getName());
//        System.out.println(Berths.getName() + " " + Berths.getCurrent().getName());
//        System.out.println(Clare.getName() + " " + Clare.getCurrent().getName());
//        System.out.println(Diane.getName() + " " + Diane.getCurrent().getName());
//        System.out.println(Erika.getName() + " " + Erika.getCurrent().getName());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new GSSolution4();
    }
}
