package collection.impl;

import collection.LinkedList;
import collection.List;
import java.util.Comparator;


/**
 * Класс MyLinkedList представляет собой реализацию LinkedList для хранения объектов в списке с стандартными методами:
 * Добавление, добавление объекта в начало-конец списка, удаление, получение объекта,
 * сортировка массива, отчистка массива, вывод содержимого в консоль
 * @author Dennis Korendyasev
 * @param <E> Тип элементов хранящихся в списке
 */
public class MyLinkedList<E> implements List<E>, LinkedList<E> {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int size;

    public MyLinkedList() {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addFirst(E element)
    {
       final Node<E> f = firstNode;
       final Node<E> newNode = new Node<>(element, null, f);
       firstNode = newNode;
        if (f == null)
            lastNode = newNode;
        else
            f.setPreviousNode(newNode);
        size++;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addLast(E element)
    {
        final Node<E> l = lastNode;
        final Node<E> newNode = new Node<>(element, l, null);
        lastNode = newNode;
        if (l == null)
            firstNode = newNode;
        else
            l.setNextNode(newNode);
        size++;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void add(E element) {
         addLast(element);
    }

    /**
     * @inheritDoc
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public void add(E element, int index)
    {
        if (checkingForNegativeValue(index) && checkingGoingOutsideArray(index))
        {
            Node<E> target = searchNodeInList(index);
            overwritingLinksToCreateNode(target, element);
            size++;
        }
    }

    /**
     * @inheritDoc
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public E get(int index)
    {
        if (checkingForNegativeValue(index) && checkingGoingOutsideArray(index)) {
            Node<E> target = searchNodeInList(index);
            return target.getElement();
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean remove(E element)
    {
        int index = getIndexObject(element);
        if (checkingForNegativeValue(index)) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * @inheritDoc
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public boolean remove(int index)
    {
        if (checkingForNegativeValue(index))
        {
            Node<E> target = searchNodeInList(index);
            overwritingLinksForDeletionNode(target);
            size--;
            return true;
        }
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeAll()
    {
        lastNode = new Node<E>(null, firstNode, null);
        firstNode = new Node<E>(null, null, lastNode);
        size = 0;
    }

    /**
     * Сортировка связанного списка с помощью сортировки по выбору
     * При сортировке выделения мы поддерживаем два указателя: текущий и индексный.
     * Изначально current указывает на головной узел, а index будет указывать на узел рядом с current.
     * Мы просматриваем список до тех пор, пока current не укажет значение null.
     * Для каждого текущего узла индекс переходит от следующего текущего узла к нулевому.
     * Значение текущего узла сравнивается с каждым значением из следующего узла до конца списка.
     * Если значение в index меньше значения в current, мы меняем значения местами.
     * Таким образом, к текущему индексу добавляется наименьшее значение.
     */
    @Override
    public void sort()
    {
        Node<E> current = firstNode;
        while (current != null) {
            Node<E> index = current.nextNode;
            Node<E> min = current;
            while (index != null) {
                if (comparator().compare(index.element, min.element) < 0)
                {
                    min = index;
                }
                index = index.nextNode;
            }
            if (current.element != null) {
                E e = current.element;
                current.element = min.element;
                min.element = e;
            }
            current = current.nextNode;
        }
    }

    /**
     * Возвращает ссылку в который храниться объект
     * @param index Индекс по которому нужно найти объект
     * @return ссылку на объект
     */
    private Node<E> searchNodeInList(int index)
    {
        Node<E> target = firstNode.getNextNode();
        for (int i = 0; i < index; i++) {
            target = target.getNextNode();
        }
        return target;
    }

    /**
     * Находит индекс переданного объекта
     * @param element Объект у которого нужно найти индекс
     * @return позицию объекта, или -1 если объект не найден
     */
    private int getIndexObject(E element)
    {
        if (element != null)
        {
            Node<E> target = firstNode.getNextNode();
            for (int i = 0; i < size; i++)
            {
                target = target.getNextNode();
                if (element.equals(target.element)) {
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * Проверка числа на отрицательное значение
     * @param value Число
     * @return true если число положительное
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    private boolean checkingForNegativeValue(int value)
    {
        if (value >= 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Нельзя передавать отрицательное значение");
        }
    }

    /**
     * Проверка числа на правильное введённое значение
     * @param value Число
     * @return true
     * @throws ArrayIndexOutOfBoundsException Если переданное число (индекс) вышел за пределы массива
     */
    private boolean checkingGoingOutsideArray(int value) {
        if (value > size) {
            throw new  ArrayIndexOutOfBoundsException("Выход за пределы массива");
        }
        return true;
    }

    /**
     * Перезаписывает ссылки в дереве и удаляет переданный объект
     * @param node ссылка по которой находиться объект
     */
    private void overwritingLinksForDeletionNode(Node<E> node)
    {
        node.getNextNode().setPreviousNode(node.getPreviousNode());
        node.getPreviousNode().setNextNode(node.getNextNode());
        node.setPreviousNode(null);
        node.setElement(null);
        node.setNextNode(null);    }

    /**
     * Перезаписывает ссылки в дереве и создаёт объект
     * @param changeableNode Изменяемое место в списке
     * @param element объект который нужно добавить в список
     */
    private void overwritingLinksToCreateNode(Node<E> changeableNode, E element)
    {
        Node<E> newNode = new Node<>(element, changeableNode.getPreviousNode(), changeableNode);
        changeableNode.getPreviousNode().setNextNode(newNode);
        changeableNode.setPreviousNode(newNode);
    }

    /**
     * Возвращает компаратор для сравнения объектов списка
     * @return компаратор для сравнения элементов
     * @throws ClassCastException если элементы списка не реализуют интерфейс Comparable
     */
    private Comparator<E> comparator()
    {
        return (o1, o2) -> {
            if (o1 == null && o2 == null) return 0;
            if (o1 == null) return 1;
            if (o2 == null) return -1;
            return ((Comparable<E>) o1).compareTo(o2);
        };
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";

        int j = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Node<E> i = firstNode; i != null; i = i.getNextNode()) {
            if (j == size - 1) {
                stringBuilder.append(i.getElement()).append("]");
                break;
            }
            stringBuilder.append(i.getElement()).append(", ");
            j++;
        }
        return stringBuilder.toString();
    }


    /**
     * Внутренний класс, представляющий узел списка.
     * @param <E> тип элемента в узле
     */
    private class Node<E>
    {
        private E element;
        private Node<E> nextNode;
        private Node<E> previousNode;


        public Node(E currentElement, Node<E> previousElement, Node<E> nextElement) {
            this.element = currentElement;
            this.previousNode = previousElement;
            this.nextNode = nextElement;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<E> nextNode) {
            this.nextNode = nextNode;
        }

        public Node<E> getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node<E> previousNode) {
            this.previousNode = previousNode;
        }
    }
}
