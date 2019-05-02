package com.agg.common.template;

import java.util.LinkedList;

/**
 * 利用LinkedList链表实现队列Queue
 * @param <E>
 */
public class Queue<E> {

    private LinkedList<E> linkedList;

    public Queue() {
        linkedList = new LinkedList<>();
    }

    public void add(E e) {
        linkedList.add(e);
    }

    public E get() {
        return linkedList.removeFirst();
    }

    public boolean isNull() {
        return linkedList.isEmpty();
    }

}
