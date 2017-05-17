package api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by rohau.andrei on 16.05.2017.
 */
public class test {
    public static void main(String[] args) {
//        int[] arr = new int[1];
//        System.out.println(arr.length);
//        for(int x : arr){
//            System.out.println(x);
//        }

//        LinkedList<Integer> list = new LinkedList<>();
//        int f = 1;
//        int ef = 2;
//        int fe = 3;
//        int f3 = 4;
//
//        list.add(f);
//        list.add(ef);
//        list.add(fe);
//        list.add(f3);
//
//        for(int i : list) {
//            System.out.println(i);
//        }


//        SimpleDateFormat testEvent = new SimpleDateFormat();
//        System.out.println(testEvent);
//
//        Date h = new Date();
//        System.out.println(h);
//
//        System.out.println(h.getTime());
//        System.out.println();
//        //1494967089418 - 1494967188155
//        long hh = System.currentTimeMillis();
//        System.out.println(hh);

        LinkedList<Integer> first = new LinkedList<>();
//        first.add(1);
//        first.add(2);
//        first.add(3);
//        first.add(4);

        System.out.println("first " + first);

        LinkedList<Integer> sec = new LinkedList<>();
        sec.add(5);
        sec.add(6);
        sec.add(7);
        sec.add(8);

        System.out.println("sec " + sec);

        LinkedList<Integer> th = new LinkedList<>();
        th.add(1);
        th.add(2);
        th.add(3);
        th.add(4);
        th.add(5);
        th.add(6);
        th.add(7);
        th.add(8);
        th.add(9);
        th.add(10);

        System.out.println("th " + th);
        System.out.println();
        LinkedList<Integer> dif = new LinkedList<>();

        first.addAll(sec);

        System.out.println("first " + first);

        if(th == null || th.size() == 0) {
            //dif.addAll(first);
        }

        for(int i = 0; i < th.size(); i++){
            boolean found = false;
            int r = th.get(i);
            for(int it = 0; it < first.size(); it++){
                int k = first.get(it);
                if(r == k){
                    found = true;
                }
            }
            if(!found){
                dif.add(r);
            }
        }

        System.out.println("dif " + dif);

    }
}
