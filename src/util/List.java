package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class list implements Iterable
 * @author Andrew Ho, Amit Deshpande
 * @param <E> - object to be held
 */
public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;

    /**
     * List constructor
     */
    @SuppressWarnings("unchecked")
    public List(){
        this.objects = (E[]) new Object[4];
        this.size = 0;
    }

    /**
     * Gets object in list
     * @return the object in the list
     */
    public E[] getObject(){
        return this.objects;
    }

    /**
     * Gets the size of list
     * @return the size of the list
     */
    public int size(){
        return this.size;
    }

    /**
     * Search and returns index of object in list
     * @param e - item to be searched for
     * @return returns index if found, -1 if not
     */
    private int find(E e){
        for (int i = 0; i < size; i++){
            if (objects[i].equals(e)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Grow method for the list
     */
    @SuppressWarnings({"ManualArrayCopy", "unchecked"})
    private void grow(){
        E[] newObjects = (E[]) new Object[size + 4];
        for (int i = 0; i < size; i++){
            newObjects[i] = this.objects[i];
        }
        this.objects = newObjects;
    }

    /**
     * Checks if an object is in the list
     * @param e - item to be checked for
     * @return true if found, false if not
     */
    public boolean contains(E e){
        if (this.isEmpty()){
            return false;
        }
        return (this.find(e) != -1);
    }

    /**
     * Adder for an item to the list
     * @param e - item to be added
     */
    public void add(E e){
        if (size >= this.objects.length){
            this.grow();
        }
        this.objects[this.size] = e;
        this.size++;
    }

    /**
     * Removes a given item from the list
     * @param e - item to be removed
     */
    public void remove(E e){
        int index = this.find(e);
        if (index != -1){
            for (int i = index; i < size - 2; i++){
                this.objects[i] = this.objects[i + 1];
            }
            size--;
            if (size < this.objects.length){this.objects[size] = null;}
        }
    }

    /**
     * Checks if list is empty
     * @return - true if empty, false if not
     */
    public boolean isEmpty(){
        return (size == 0);
    }

    /**
     * Getter for the item at index
     * @param index - idex of the item to get gotten
     * @return returns the item if found, null if not
     */
    public E get(int index){
        return index < size ? this.objects[index] : null;
    }

    /*

    if (size >= this.objects.length){
            this.grow();
        }

        // shift all elements down by one
        // E temp = e;
        E prev = this.objects[index];
        this.objects[index] = e;
         // C
        E curr;
        for (int i = index + 1; i < size; i++){
            curr = this.objects[i]; // D
            this.objects[i] = prev; // C
            prev = curr; // D
        }
        size++;
    * */

    /**
     * Sets the item in the given index
     * @param index - index to be placed in
     * @param e - item to be placed
     */
    public void set(int index, E e){
        if (index < size){
            this.objects[index] = e;
        }
    }

    /**
     * Prints the list as a string
     * @return - returns the list as a string
     */
    public String toString(){
        String ret = "";
        for (int i = 0; i < size; i++){
            ret += objects[i].toString() + "\n";
        }
        return ret;
    }

    /**
     * Returns the index of an object if it is in the list
     * @param e - item to be searched
     * @return index of the item if found
     */
    public int indexOf(E e){
        return find(e);
    }

    /**
     * Constructor method for Iterator
     * @return instance of ListIterator
     */
    public Iterator<E> iterator(){
        return new ListIterator<>();
    };

    /**
     * Private class for ListIterator
     * @param <E> - type of list
     */
    @SuppressWarnings({"unchecked"})
    private class ListIterator<E> implements Iterator<E>{
       /** current index */
        private int current = 0;

        /**
         * Checks if listIterator has next
         * @return true if has next, false if has none
         */
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Getter for the next in the listIterator
         * @return returns next, if not throw exception
         */
        public E next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return (E) objects[current++];
        }
    }
}