package api;

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

        LinkedList<Integer> list = new LinkedList<>();
        int f = 1;
        int ef = 2;
        int fe = 3;
        int f3 = 4;

        list.add(f);
        list.add(ef);
        list.add(fe);
        list.add(f3);

        for(int i : list) {
            System.out.println(i);
        }


    }
}
