package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class GameListTest {
    public Set<BoardGame> games;

    @TempDir
    static Path tempDir;

    @BeforeEach
    void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @Test
    void getGameNames() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        List<String> expectedList = Arrays.asList("17 days", "Chess", "Go", "Go Fish", "golang", "GoRami", "Monopoly", "Tucano");
        assertEquals(expectedList, list1.getGameNames());
    }

    @Test
    void clear() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        assertEquals(8, list1.count());
        list1.clear();
        assertEquals(0, list1.count());
    }

    @Test
    void count() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        assertEquals(8, list1.count());
    }

    @Test
    void saveGame() throws IOException {
        IGameList list1 = new GameList();
        list1.addToList("Chess", games.stream());
        list1.addToList("17 days", games.stream());
        Path gameList = tempDir.resolve("games_list.txt");
        list1.saveGame(gameList.toString());
        assertTrue(Files.exists(gameList));
        List<String> fileContent = Files.readAllLines(gameList);
        List<String> expected = List.of("17 days", "Chess");
        assertEquals(expected, fileContent);
    }

    @Test
    void testAddSingleGameToListByIndex() {
        // String str, Stream<BoardGame> filtered
        IGameList list1 = new GameList();
        list1.addToList("1", games.stream());
        assertEquals(1, list1.count());
        System.out.println(list1.getGameNames());
    }

    @Test
    void testAddInvalidGameToListByIndex() {
        IGameList list1 = new GameList();
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list1.addToList("9", games.stream());
        });
        assertEquals("Index out of bounds", exception.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class, () -> {
            list1.addToList("0", games.stream());
        });
        assertEquals("Index out of bounds", exception2.getMessage());

    }

    @Test
    void testAddSingleGameToListByName() {
        IGameList list1 = new GameList();
        list1.addToList("17 days", games.stream());
        assertEquals(1, list1.count());
        System.out.println(list1.getGameNames());
    }

    @Test
    void testAddInvalidGameToListByName() {
        IGameList list1 = new GameList();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> list1.addToList("18 days", games.stream()));
        assertEquals("Game does not exist", exception.getMessage());
    }

    @Test
    void testAddValidRangeOfGamesToList() {
        IGameList list1 = new GameList();
        list1.addToList("1-1", games.stream());
        assertEquals(1, list1.count());

        list1.addToList("2-5", games.stream());
        assertEquals(5, list1.count());
    }

    @Test
    void testAddInvalidRangeOfGamesToList() {
        IGameList list1 = new GameList();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> list1.addToList("0-9", games.stream()));
        assertEquals("Invalid range of games", exception.getMessage());

        IGameList list2 = new GameList();
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> list2.addToList("5-1", games.stream()));
        assertEquals("Invalid range of games", exception2.getMessage());
    }

    @Test
    void testAddAllGamesToList() {
        IGameList list1 = new GameList();
        list1.addToList("ALL", games.stream());
        assertEquals(8, list1.count());
        System.out.println(list1.getGameNames());
    }

    @Test
    void testRemoveAllGamesFromList() {
        IGameList list1 = new GameList();
        list1.addToList("1-5", games.stream());
        assertEquals(5, list1.count());
        list1.removeFromList("all");
        assertEquals(0, list1.count());
    }

    @Test
    void removeFromListByIndex() {
        IGameList list1 = new GameList();
        list1.addToList("Chess", games.stream());
        list1.addToList("17 days", games.stream());
        List<String> expect = List.of("17 days", "Chess");
        assertEquals(expect, list1.getGameNames());
        list1.removeFromList("2");
        List<String> expect2 = List.of("17 days");
        assertEquals(expect2, list1.getGameNames());
    }

    @Test
    void removeFromListByInvalidIndex() {
        IGameList list1 = new GameList();
        list1.addToList("17 days", games.stream());
        list1.addToList("Chess", games.stream());
        List<String> expect = List.of("17 days", "Chess");
        assertEquals(expect, list1.getGameNames());
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                () -> list1.removeFromList("3"));
        assertEquals("Invalid index", exception.getMessage());
    }

    @Test
    void removeFromListByName() {
        IGameList list1 = new GameList();
        list1.addToList("17 days", games.stream());
        list1.addToList("Chess", games.stream());
        List<String> expect = List.of("17 days", "Chess");
        assertEquals(expect, list1.getGameNames());
        list1.removeFromList("17 days");
        List<String> expect2 = List.of("Chess");
        assertEquals(expect2, list1.getGameNames());
    }

    @Test
    void removeFromListByInvalidName() {
        IGameList list1 = new GameList();
        list1.addToList("17 days", games.stream());
        list1.addToList("Chess", games.stream());
        List<String> expect = List.of("17 days", "Chess");
        assertEquals(expect, list1.getGameNames());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> list1.removeFromList("golang"));
        assertEquals("Game does not exist", exception.getMessage());
    }

    @Test
    void removeFromListByRange() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        list1.removeFromList("2-7");
        List<String> expect = List.of("17 days", "Tucano");
        assertEquals(expect, list1.getGameNames());

        list1.removeFromList("1-1");
        List<String> expect2 = List.of("Tucano");
        assertEquals(expect2, list1.getGameNames());
    }

    @Test
    void removeFromListByInvalidRange() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> list1.removeFromList("7-2"));
        assertEquals("Invalid range of games", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> list1.removeFromList("0-9"));
        assertEquals("Invalid range of games", exception2.getMessage());
    }
}
