
import collection.impl.MyArrayList;
import collection.impl.MyLinkedList;



public class Main {
    public static void main(String[] args) {


        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);

        MyLinkedList<Integer> list1 = new MyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }
        System.out.println(list1);
    }
}