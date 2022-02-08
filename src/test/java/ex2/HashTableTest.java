package ex2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void count() {
    }

    @org.junit.jupiter.api.Test
    void size() {
    }

    @org.junit.jupiter.api.Test
    void put() {
        HashTable hashTable = new HashTable();

        hashTable.put("clave","valorr");
        hashTable.put("2","pera");
        hashTable.put("3","manzana");
        hashTable.put("3","naranja");
        hashTable.put("5","platano");

        Assertions.assertEquals(hashTable.toString(), "\n" +
                " bucket[2] = [2, pera]\n" +
                " bucket[3] = [3, manzana] -> [3, naranja]\n" +
                " bucket[5] = [5, platano]\n" +
                " bucket[7] = [clave, valorr]");
    }

    void put2(){
        HashTable hashTable = new HashTable();

        hashTable.put("clave","valor");
        hashTable.put("2","pera");
        hashTable.put("3","manzana");
        hashTable.put("3","naranja");
        hashTable.put("5","platano");

        Assertions.assertEquals(hashTable.toString(), "\n" +
                " bucket[2] = [2, pera]\n" +
                " bucket[3] = [3, manzana] -> [3, naranja]\n" +
                " bucket[5] = [5, platano]\n" +
                " bucket[7] = [clave, valor]");
    }


    @org.junit.jupiter.api.Test
    void get() {
        HashTable hashTable = new HashTable();
        hashTable.put("clave","valorr");
        hashTable.get("clave");
        Assertions.assertEquals(hashTable.get("clave"),"valorr");
    }

    @org.junit.jupiter.api.Test
    void drop() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }
}