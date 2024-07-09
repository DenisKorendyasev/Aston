package collection.impl;

import collection.List;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Класс MyArrayList представляет собой реализацию ArrayList для хранения объектов с стандартными методами:
 * Добавление, удаление, получение объекта, сортировка массива, отчистка массива, вывод содержимого в консоль
 * @author Dennis Korendyasev
 * @param <E> Тип элементов хранящихся в массиве
 */
public class MyArrayList<E> implements List<E> {

    /**
     * Дефолтное значение вместимости массива при его создании
     */
    private final static int DEFAULT_CAPACITY = 10;

    /**
     * Процент вместимости, при котором увеличивать размер массива
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.70f;

    private int capacity;
    private int size;
    private E[] list;

    /**
     * Конструктор по-умолчанию, создаёт массив с дефолтным размером
     */
    public MyArrayList()
    {
        capacity = DEFAULT_CAPACITY;
        list = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Перегруженный конструктор создаёт массив с указанным размером
     * @param capacity Размер массива
     */
    public MyArrayList(int capacity)
    {
        if (checkingForNegativeValue(capacity)) {
            list = (E[]) new Object[capacity];
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void add(E element)
    {
        checkingTheCapacity();
        list[size++] = element;
    }

    /**
     * @inheritDoc
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public void add(E element, int index)
    {
        checkingTheCapacity();
        if (checkingForNegativeValue(index) && checkingGoingOutsideArray(index))
        {
            for (int i = size; i > index; i--) {
                list[i] = list[i - 1];
            }
            list[index] = element;
            size++;
        }
    }

    /**
     * @inheritDoc
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public E get(int index) {
        if (checkingForNegativeValue(index) && checkingGoingOutsideArray(index)) {
            return list[index];
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
     * @throws IllegalArgumentException Если переданное число (индекс) отрицательное
     */
    @Override
    public boolean remove(int index)
    {
        if (checkingForNegativeValue(index)) {
            for (int i = index; i < size; i++)
            {
                list[i] = list[i + 1];
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean remove(E element) {
       return remove(getIndexObject(element));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeAll() {
       list = (E[]) new Object[DEFAULT_CAPACITY];
       size = 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sort() {
        Arrays.sort(list, comparator());
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
            for (int i = 0; i < size; i++) {
                if (element.equals(list[i])) {
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
        if (value >= 0 ) {
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
        if (value > size - 1) {
            throw new  ArrayIndexOutOfBoundsException("Выход за пределы массива");
        }
        return true;
    }

    /**
     * Увеличивает размер массива в 2 раза
     */
    private void increasingCapacity()
    {
        capacity *= 2;
        E[] copyList = list;
        list = (E[]) new Object[capacity];
        System.arraycopy(copyList, 0, list, 0, copyList.length);
    }

    /**
     * Проверяет размер массива, если превышает допустимое значение, вызывает метод
     * для его увелечения
     */
    private void checkingTheCapacity()
    {
        if (size >= (capacity * DEFAULT_LOAD_FACTOR)) {
            increasingCapacity();
        }
    }
    /**
     * Возвращает компаратор для сравнения объектов массива
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

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        if (size==0)
            return "[]";

        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (E element : list){
            if (element != null) {
                if (i == size - 1) {
                    stringBuilder.append(element).append("]");
                    break;
                }
                stringBuilder.append(element).append(", ");
            }
            i++;
        }
        return stringBuilder.toString();
    }
    }

