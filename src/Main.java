
import collection.impl.MyArrayList;
import collection.impl.MyLinkedList;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {


        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);
        System.out.println(list.get(10));

        MyLinkedList<Integer> list1 = new MyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }
        System.out.println(list1);
    }
}