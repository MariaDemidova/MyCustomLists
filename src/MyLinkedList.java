import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс связанного списка, имплементирует MyList и переопределяет все его методы
 *
 * @param <T> параметры списка должны уметь сравниваться друг с другом
 */
public class MyLinkedList<T extends Comparable<T>> implements MyList<T> {
    private Node<T> head; //первый узел
    private Node<T> tail;// последний узел
    private int size;

    /**
     * Конструктор с пустым списком
     */
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    /**
     * Вставляем элемент в конец списка
     *
     * @param element добавляемый элемент
     */
    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++; // Увеличиваем размер списка
    }

    /**
     * Вставляем элемент в указанное место
     *
     * @param index   индекс, куда добавить элемент
     * @param element добавляемый элемент
     * @throws IndexOutOfBoundsException если зашли за пределы списка
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        if (index == size) {
            add(element);
            return;
        }
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    /**
     * Удаляет элемент по порядковому номеру
     *
     * @param index индекс элемента, который надо удалить
     */
    @Override
    public void delete(int index) {
        if (isEmpty()) {
            return;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        if (index == 0) {
            removeHead();
        } else if (index == size - 1) {
            removeTail();
        } else {
            removeNode(getNode(index));
        }
        size--;
    }

    /**
     * Удаление элемента по ключу
     *
     * @param element элемент, который надо удалить
     */
    @Override
    public void delete(T element) {
        if (isEmpty()) {
            return;
        }
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data, element)) {
                if (current.prev == null) {
                    removeHead();
                } else if (current.next == null) {
                    removeTail();
                } else {
                    removeNode(current);
                }
                size--;
                return;
            }
            current = current.next;
        }
    }

    /**
     * проверяем, не пуст ли список
     *
     * @return true or false
     */
    private boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаление первого элемента
     */
    private void removeHead() {
        if (head != null) {
            Node<T> nextNode = head.next;
            if (nextNode != null) {
                nextNode.prev = null;
            }
            head = nextNode;
            if (isEmpty()) {
                tail = null;
            }
        }
    }

    /**
     * Удаление последнего элемента
     */
    private void removeTail() {
        if (tail != null) {
            Node<T> prevNode = tail.prev;
            if (prevNode != null) {
                prevNode.next = null;
            }
            tail = prevNode;
        }
    }

    /**
     * Удаление ноды в середине
     */
    private void removeNode(Node<T> current) {
        if (current == null) {
            return;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        }

        current.prev = null;
        current.next = null;
    }

    /**
     * Поиск индекса элемента
     *
     * @param element элемент, индекс которого надо найти
     */
    @Override
    public int index(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (Objects.equals(current.data, element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1; // Если элемент не найден, возвращаем -1
    }

    /**
     * Получение элемента по номеру
     *
     * @param index порядковый номер элемента
     * @return элемент из ноды
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        return getNode(index).data;
    }

    /**
     * Находит элемент по ключу в списке.
     *
     * @param key Ключ для поиска элемента.
     * @return Optional, содержащий элемент с указанным ключом, или пустой Optional, если элемент не найден.
     */
    public Optional<T> getByKey(T key) {
        if (isEmpty()) {
            return Optional.empty();
        }
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (key == null && currentNode.data == null) {
                return Optional.ofNullable(currentNode.data);
            }
            if (key != null && Objects.equals(currentNode.data, key)) {
                return Optional.of(currentNode.data);
            }
            currentNode = currentNode.next;
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            current.prev = null; // Обнуляем ссылку на предыдущий узел
            current.next = null; // Обнуляем ссылку на следующий узел
            current.data = null; //Обнуляем data
            current = next;
        }
        tail = null;
        head = null;
        size = 0;
    }

    /**
     * @return возвращает размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * метод сортировки пузырьком
     *
     * @param comparator принимает такой тип объекта, который умеет сравниваться
     */
    @Override
    public void sort(Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return; // Список пуст или содержит один элемент
        }

        boolean swapped;
        Node<T> current;
        Node<T> last = null;
        do {
            swapped = false;
            current = head;
            while (current.next != last) {
                if (comparator.compare(current.data, current.next.data) > 0) {
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
            last = current;
        } while (swapped);
    }

    /**
     * Получаем ноду по индексу (порядковому номеру)
     *
     * @param index порядковый номер
     * @return возвращает определенную ноду
     * @throws IndexOutOfBoundsException если индекс выходит за пределы листа
     */
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        // проверяем, в какой половине листа находится искомый индекс
        Node<T> current;
        if (index < (size / 2)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]"; // Если список пуст, возвращаем пустые скобки
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Создаем основу связанного списка - внутренний параметризированный класс, из объектов которого состоит список
     *
     * @param <T> параметр класса
     */
    private static class Node<T> {
        private T data;
        private Node<T> next; //следующий узел
        private Node<T> prev; //предыдущий узел

        /**
         * Конструктор, который создает узел с ссылками на предыдущий элемент и последующий
         */
        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
}
