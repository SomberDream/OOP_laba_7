package ru.somber.laba_7.list;

import java.util.Objects;

public class LinkedList<T> implements IList<T> {

    /** Узел-заглушка, выступает в качестве головы. */
    private final Node<T> head;
    /** Узел-заглушка, выступает в качестве хваста. */
    private final Node<T> tail;
    /** Кол-во элементов в списке. */
    private int size;


    public LinkedList() {
        head = new Node<>(null);
        tail = new Node<>(null);

        linkNode(head, tail);

        size = 0;
    }


    @Override
    public void addFirst(T element) {
        Objects.requireNonNull(element);

        Node<T> newNode = new Node<>(element);

        Node<T> next = head.getNext();
        Node<T> previous = head;

        linkNode(previous, newNode, next);

        size++;
    }

    @Override
    public void addLast(T element) {
        Objects.requireNonNull(element);

        Node<T> newNode = new Node<>(element);

        Node<T> next = tail;
        Node<T> previous = tail.getPrevious();

        linkNode(previous, newNode, next);

        size++;
    }

    @Override
    public void add(int index, T element) {
        Node<T> next = getNodeByIndex(index);
        Node<T> previous = next.getPrevious();
        Node<T> newNode = new Node<>(element);

        linkNode(previous, newNode, next);
    }

    @Override
    public T getFirst() {
        tryThrowEmptyListException();

        return head.getNext().getElement();
    }

    @Override
    public T getLast() {
        tryThrowEmptyListException();

        return tail.getPrevious().getElement();
    }

    @Override
    public void removeFirst() {
        tryThrowEmptyListException();

        Node<T> previous = head;
        Node<T> next = head.getNext().getNext();

        linkNode(previous, next);

        size--;
    }

    @Override
    public void removeLast() {
        tryThrowEmptyListException();

        Node<T> next = tail;
        Node<T> previous = tail.getPrevious().getPrevious();

        linkNode(previous, next);

        size--;
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);

        return firstIndexOf(element) != -1;
    }

    @Override
    public int firstIndexOf(T element) {
        Objects.requireNonNull(element);

        if (isEmpty()) {
            return -1;
        }

        Node<T> checkNode = head.getNext();
        int index = 0;

        while (checkNode != tail) {
            if (checkNode.getElement().equals(element)) {
                return index;
            }

            checkNode = checkNode.getNext();
            index++;
        }

        return -1;
    }

    @Override
    public T get(int index) {
        Node<T> checkNode = getNodeByIndex(index);

        return checkNode.getElement();
    }

    @Override
    public void set(int index, T element) {
        Node<T> checkNode = getNodeByIndex(index);

        Node<T> next = checkNode.getNext();
        Node<T> previous = checkNode.getPrevious();

        Node<T> newNode = new Node<>(element);
        linkNode(previous, newNode, next);
    }

    @Override
    public void remove(T element) {
        int index = firstIndexOf(element);

        if (index >= 0) {
            removeByIndex(index);
        }
    }

    @Override
    public void removeByIndex(int index) {
        Node<T> checkNode = getNodeByIndex(index);

        Node<T> next = checkNode.getNext();
        Node<T> previous = checkNode.getPrevious();

        linkNode(previous, next);

        size--;
    }

    @Override
    public void clear() {
        linkNode(head, tail);
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public IIterator<T> getIterator() {
        tryThrowEmptyListException();

        return new LinkedListIterator<>(this);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "LinkedList{" +
                    "size=" + size +
                    '}';
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LinkedList{\n");

        Node<T> node = head.getNext();
        sb.append(node.getElement().toString());
        node = node.getNext();

        while (node != tail) {
            sb.append(",\n").append(node.getElement());
            node = node.getNext();
        }

        sb.append("\n} size=").append(getSize()).append(";");

        return sb.toString();
    }


    /**
     * Возвращает Node по индексу.
     */
    private Node<T> getNodeByIndex(int index) {
        tryThrowEmptyListException();
        tryThrowIndexOutOfBoundException(index);

        Node<T> checkNode = head.getNext();

        for (int i = 0; i < index; i++) {
            checkNode = checkNode.getNext();
        }

        return checkNode;
    }

    /**
     * Образует связь между переданными узлами.
     * Узел next - правый узел.
     * Узел previous - левый узел.
     *
     * После вызова метода цепочка связей будет выглядеть:
     * ... previous <-> next ...
     */
    private void linkNode(Node<T> previous, Node<T> next) {
        next.setPrevious(previous);
        previous.setNext(next);
    }

    /**
     * Образует связь между переданными узлами.
     * Узел linkableNode - узел, который нужно вставить.
     * Узел next - узел, являющийся следующми для linkableNode.
     * Узел previous - узел, являющийся предыдущим для linkableNode.
     *
     * После вызова метода цепочка связей будет выглядеть:
     * ... previous <-> linkableNode <-> next ...
     */
    private void linkNode(Node<T> previous, Node<T> linkableNode, Node<T> next) {
        linkNode(previous, linkableNode);
        linkNode(linkableNode, next);
    }


    /**
     * Проверяет пустой ли список.
     * Если список пустой, то выбрасывает исключение времени выполнения.
     */
    private void tryThrowEmptyListException() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
    }

    /**
     * Проверяет выходит ли переданный индекс за границы списка.
     * Если индекс выходит за пределы списка, то выбрасывается исключение времени выполнения.
     */
    private void tryThrowIndexOutOfBoundException(int index) {
        if (index >= getSize() || index < 0) {
            throw new RuntimeException("Index out of bound");
        }
    }


    /**
     * Класс узла-ячейки. Нужен для представленя одной ячейки связного списка.
     * Хранит элемент связного списка и две ссылки на предыдущий и следующий узел.
     */
    private static class Node<T> {
        private final T element;
        private Node<T> next;
        private Node<T> previous;


        public Node(T element) {
            this(element, null, null);
        }

        public Node(T element, Node<T> next, Node<T> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }


        public T getElement() {
            return element;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }

    }


    private static class LinkedListIterator<T> implements IIterator<T> {
        /** Лист, за который отвечает итератор. */
        private final LinkedList<T> list;

        /** Текущий просматриваемый узел. */
        private Node<T> currentNode;
        /** Индекс текущего просматриваемого узла. */
        private int currentIndex;


        public LinkedListIterator(LinkedList<T> list) {
            this.list = list;

            currentNode = list.head.getNext();
            currentIndex = 0;
        }


        public T currentElement() {
            return currentNode.getElement();
        }

        public int currentIndex() {
            return currentIndex;
        }

        public boolean next() {
            if (currentNode.getNext() != list.tail) {
                currentIndex++;
                currentNode = currentNode.getNext();
                return true;
            } else {
                return false;
            }
        }

        public boolean previous() {
            if (currentNode.getPrevious() != list.head) {
                currentIndex--;
                currentNode = currentNode.getPrevious();
                return true;
            } else {
                return false;
            }
        }

        public void f() {

        }

    }


}
