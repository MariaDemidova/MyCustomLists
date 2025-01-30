import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Динамический массив ArrayList, который реализует MyList
 *
 * @param <T> Лист может содержать любые ссылочные типы данных, которые умеют сравниваться друг с другом
 */
public class MyArrayList<T extends Comparable<T>> implements MyList<T> {
    private T[] list; //принимает массив любого ссылочного типа
    private int size; //размер массива
    private static final int CAPACITY_SIZE = 10; //размер массива по умолчанию

    //Конструктор, где задается размер массива
    public MyArrayList(int capacity) {
        //Защита от отрицательного значения capacity
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        } else {
            //Если capacity ОК, то создаем лист
            list = (T[]) new Comparable[capacity]; //создаем лист объектов типа Т, которые можно будет сравнивать
        }
    }

    //Конструктор, где не задается размер листа, и берется дефолтный capacity
    public MyArrayList() {
        list = (T[]) new Comparable[CAPACITY_SIZE];
    }

    /**
     * Добавляем элемент в конец списка
     *
     * @param element добавляемый элемент
     */
    @Override
    public void add(T element) {
        checkAndEnlarge();
        list[size++] = element;
    }

    /**
     * Добавление элемента в указанную позицию в листе
     *
     * @param index   индекс, по которому дожен быть вставлен элемент
     * @param element сам элемент
     * @throws IndexOutOfBoundsException Если индекс находится вне диапазона, выбразывается исключение
     */
    @Override
    public void add(int index, T element) {
        checkAndEnlarge();
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1]; //сдвигаем элементы вправо
        }
        list[index] = element; //вставляем элемент
        size++; // увеличиваем размер на 1
    }

    /**
     * Получает элемент по индексу
     *
     * @param index индекс элемента
     * @return T возвращает элемент типа T
     * @throws IndexOutOfBoundsException если вышли за пределы листа
     */
    @Override
    public T get(int index) {
        //Проверка индекса, что не выходит за пределы
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return list[index];
    }

    /**
     * Удаление элемента по индексу
     *
     * @param index индекс элемента, который надо удалить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы массива
     */
    @Override
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size - 1) {
            list[--size] = null; // Если удаляем последний элемент, просто уменьшаем размер и обнуляем последний элемент
        } else {
            //если элемент в середине, находим по индексу элемент, остальные элементы сдвигаем влево
            for (int i = index; i < size; i++) {
                list[i] = list[i + 1];
            }
            list[--size] = null; //обнуляем последний элемент
        }
    }

    /**
     * Удаление элемента по ключу
     *
     * @param element элемент, который надо удалить
     */
    @Override
    public void delete(T element) {
        int pos = index(element);
        if (pos < 0) { // если index(T element) вернет -1, выбрасывается ошибка
            throw new NoSuchElementException("Index: " + pos + ", Size: " + size);
        }
        delete(pos);
    }

    /**
     * Поиск индекса элемента
     *
     * @param element элемент, индекс которого надо найти
     *                Возвращать будет индекс
     * @return int возвращает индекс
     */
    @Override
    public int index(T element) {
        //проверяем на нулл
        if (element == null) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (list[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Удаляет все элементы массива
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            list[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Быстрая сортировка массива
     */
    private <T extends Comparable<T>> void quickSort(T[] array, int lowIndex, int highIndex) {
        T pivot = array[lowIndex];
        int leftPointer = lowIndex;
        int rightPointer = highIndex;
        while (leftPointer < rightPointer) {
            while (array[leftPointer].compareTo(pivot) < 0) {
                leftPointer++;
            }
            while (array[rightPointer].compareTo(pivot) > 0) {
                rightPointer--;
            }
            if (leftPointer <= rightPointer) {
                T swap = array[leftPointer];
                array[leftPointer] = array[rightPointer];
                array[rightPointer] = swap;
                leftPointer++;
                rightPointer--;
            }
        }
        if (lowIndex < rightPointer) quickSort(array, lowIndex, rightPointer);
        if (highIndex > leftPointer) quickSort(array, leftPointer, highIndex);
    }

    @Override
    public void sort(Comparator<T> comparator) {
        quickSort(list, 0, size - 1);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(list, size));
    }

    /**
     * Если размер массива достиг предела, то пперезаписываем в новый массив, увеличив его на 100№
     */
    private void checkAndEnlarge() {
        if (size == list.length) {
            list = Arrays.copyOf(list, list.length * 2);
        }
    }
}
