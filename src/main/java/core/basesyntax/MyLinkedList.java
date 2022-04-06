package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index when add node");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementOfList : list) {
            linkLast(elementOfList);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when get node");
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when set node");
        }
        Node<T> nodeToSetNewValue = getNodeByIndex(index);
        T oldValue = nodeToSetNewValue.value;
        nodeToSetNewValue.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when remove");
        }
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(index);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        while (node != null) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                unlink(index);
                size--;
                return true;
            }
            index++;
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> nodeByIndex;
        if (index < size >> 1) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            head.prev = newNode;
            head = newNode;
        }
    }

    private void linkLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(value, lastNode, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
    }

    private void linkBefore(T value, Node<T> indexNode) {
        Node<T> currentIndexNode = indexNode.prev;
        Node<T> currentNewNode = new Node<>(value, currentIndexNode, indexNode);
        indexNode.prev = currentNewNode;
        if (currentIndexNode == null) {
            head = currentNewNode;
        } else {
            currentIndexNode.next = currentNewNode;
        }
    }

    private void unlink(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;
        if (prev == null && next == null) {
            head = null;
            tail = null;
            return;
        }
        if (prev == null) {
            head = next;
            next.prev = null;
            return;
        }
        if (next == null) {
            tail = prev;
            prev.next = null;
            return;
        }
        prev.next = currentNode.next;
        next.prev = currentNode.prev;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
