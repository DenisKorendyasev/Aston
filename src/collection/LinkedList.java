package collection;

/**
 * Интерфейс для удобной работы с списком
 * Предоставляет методы для добавления в начало-конец списка
 * @param <E> Тип объекта
 */
public interface LinkedList<E>
{
    /**
     * Добавить объект в начало списка
     * @param element объект
     */
    void addFirst(E element);

    /**
     * Добавить объект в конец списка
     * @param element объект
     */
    void addLast(E element);

}
