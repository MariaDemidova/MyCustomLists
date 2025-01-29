public class Main {
    public static void main(String[] args) {
        MyArrayList <Integer> myArrayList = new MyArrayList();
        myArrayList.add(3);
        myArrayList.add(2);
        myArrayList.add(5);
        System.out.println(myArrayList);
        myArrayList.sort(Integer::compareTo);
        System.out.println(myArrayList);

        MyLinkedList <Integer> myLinkedList = new MyLinkedList();
        myLinkedList.add(3);
        myLinkedList.add(2);
        myLinkedList.add(5);

        myLinkedList.printList();
        myLinkedList.sort(Integer::compareTo);
        myLinkedList.printList();
    }
}