package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    /**
     * The array buffer into which the resumes are stored.
     */
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    /**
     * The number of resumes this storage contains.
     */
    private int size;

    /**
     * Removes all of the resumes from this storage.
     */
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " is not exist");
        } else {
            storage[index] = r;
        }
    }

    /**
     * Appends the specified resume to the end of the storage.
     *
     * @param r resume to be appended to this storage
     */
    @Override
    public void save(Resume r) {
        if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            storage[size++] = r;
        }
    }

    /**
     * Returns the resume with the specified uuid
     *
     * @param uuid unique number of the resume
     * @return the resume which uuid equals specified one,
     * or <tt>null</tt> if there was no resume for <tt>uuid</tt>
     */
    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " is not exist");
        }
        return index != -1 ? storage[index] : null;
    }

    /**
     * Removes the resume <tt>uuid</tt> of which equals the specified one.
     *
     * @param uuid unique number of the resume
     */
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " is not exist");
        } else {
            storage[index] = storage[--size];
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the number of resumes this storage contains
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the index of the specified Resume <tt>uuid</tt>  of which equals the specified one.
     *
     * @param uuid unique number of the resume
     * @return index of the specified resume
     */
    private int getIndex(String uuid) {
        return IntStream.range(0, size)
                .filter(i -> storage[i].getUuid().equals(uuid))
                .findAny()
                .orElse(-1);
    }
}