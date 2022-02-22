package ex3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void put() {
        HashTable hashTable = new HashTable();

        // Inserir un element que no col·lisiona dins una taula vuida.
        hashTable.put("0","0");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 0]");
        Assertions.assertEquals( 1,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un element que no col·lisiona dins una taula no vuida.
        hashTable.put("1","1");
        hashTable.put("2","2");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 0]\n" +
                " bucket[1] = [1, 1]\n" +
                " bucket[2] = [2, 2]");
        Assertions.assertEquals( 3,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
        hashTable.put("12","12");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 0]\n" +
                " bucket[1] = [1, 1] -> [12, 12]\n" +
                " bucket[2] = [2, 2]");
        Assertions.assertEquals( 4,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
        hashTable.put("13","13");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 0]\n" +
                " bucket[1] = [1, 1] -> [12, 12]\n" +
                " bucket[2] = [2, 2] -> [13, 13]");
        Assertions.assertEquals( 5,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

    }

    @org.junit.jupiter.api.Test
    void putUpdate() {
        HashTable hashTable = new HashTable();

        // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula vuida.
        hashTable.put("0","0");
        hashTable.put("0","00");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 00]");
        Assertions.assertEquals( 1,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
        hashTable.put("1","1");
        hashTable.put("1","11");
        hashTable.put("2","2");
        hashTable.put("2","22");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 00]\n" +
                " bucket[1] = [1, 11]\n" +
                " bucket[2] = [2, 22]");
        Assertions.assertEquals( 3,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
        hashTable.put("12","12");
        hashTable.put("12","1212");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 00]\n" +
                " bucket[1] = [1, 11] -> [12, 1212]\n" +
                " bucket[2] = [2, 22]");
        Assertions.assertEquals( 4,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());

        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
        hashTable.put("13","13");
        hashTable.put("13","1313");
        Assertions.assertEquals(hashTable.toString(),  "\n" +
                " bucket[0] = [0, 00]\n" +
                " bucket[1] = [1, 11] -> [12, 1212]\n" +
                " bucket[2] = [2, 22] -> [13, 1313]");
        Assertions.assertEquals( 5,hashTable.count());
        Assertions.assertEquals( 16,hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void get() {
        HashTable hashTable = new HashTable();

        // Obtenir un element que no col·lisiona dins una taula vuida.
        assertNull(hashTable.get("0"));

        // Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.put("0", "0");
        Assertions.assertEquals("0", hashTable.get("0"));

        // Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.put("11", "11");
        Assertions.assertEquals("11", hashTable.get("11"));

        // Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.put("22", "22");
        Assertions.assertEquals("22", hashTable.get("22"));

        // Obtenir un elements que no existeix perquè la seva posició està buida.
        Assertions.assertNull(hashTable.get("101"));

        // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        Assertions.assertEquals(null, hashTable.get("12"));

        // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
        Assertions.assertEquals(null, hashTable.get("33"));
    }

    @org.junit.jupiter.api.Test
    void drop() {
        HashTable hashTable = new HashTable();

        // Esborrar un element que no col·lisiona dins una taula.
        hashTable.put("0","0");
        hashTable.drop("0");
        assertNull(hashTable.get("0"));
        assertEquals(0, hashTable.count());
        assertEquals(16, hashTable.size());

        // Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.put("0","0");
        hashTable.put("11","11");
        hashTable.put("22","22");
        hashTable.drop("0");
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [22, 22]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        // Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.put("33","33");
        hashTable.drop("22");
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [33, 33]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        // Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.put("44","44");
        hashTable.drop("44");
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [33, 33]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        // Eliminar un elements que no existeix perquè la seva posició està buida.
        hashTable.drop("101");
        assertNull(hashTable.get("101"));
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [33, 33]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        // Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        hashTable.put("1","1");
        hashTable.drop("12");
        assertNull(hashTable.get("12"));
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [33, 33]\n" +
                " bucket[1] = [1, 1]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        // Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
        hashTable.put("55","55");
        hashTable.drop("66");
        assertNull(hashTable.get("66"));
        assertEquals("\n" +
                " bucket[0] = [11, 11] -> [33, 33] -> [55, 55]\n" +
                " bucket[1] = [1, 1]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

    }
}