import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import student.Planner;
import student.IPlanner;
import student.GameData;


/**
 * JUnit test for the Planner class.
 * 
 * Just a sample test to get you started, also using
 * setup to help out. 
 */
public class TestPlanner {
    static Set<BoardGame> games;

    @BeforeAll
    public static void setup() {
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
    public void testFilterName() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
    }

    @Test
    public void testFilterNameContains() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name ~= go").toList();
        assertEquals(4, filtered.size());
    }

    @Test
    public void testFilterNameNotEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name != Go Fish").toList();
        assertEquals(7, filtered.size());
    }

    @Test
    public void testFilterNameEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name == Go Fish").toList();
        assertEquals(1, filtered.size());
    }

    @Test
    public void testFilterMaxPlayers() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("maxPlayers < 5").toList();
        assertEquals(1, filtered.size());
        assertEquals("Chess", filtered.get(0).getName());
    }

    @Test
    public void testFilterMinPlayers() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayers > 5").toList();
        assertEquals(3, filtered.size());
        assertEquals("GoRami", filtered.get(0).getName());
        assertEquals("Monopoly", filtered.get(1).getName());
        assertEquals("Tucano", filtered.get(2).getName());
    }

    @Test
    public void testFilterMinPlayTime() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayTime>=50").toList();
        assertEquals(3, filtered.size());
        assertEquals("golang", filtered.get(0).getName());
        assertEquals("Tucano", filtered.get(1).getName());
        assertEquals("17 days", filtered.get(2).getName());
    }

    @Test
    public void testFilterMaxPlayTime() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("maxPlayTime<=50").toList();
        assertEquals(3, filtered.size());
        assertEquals("GoRami", filtered.get(0).getName());
        assertEquals("Go", filtered.get(1).getName());
        assertEquals("Chess", filtered.get(2).getName());
    }

    @Test
    public void testMultiFilters() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("rank<=300,difficulty>=5").toList();
        assertEquals(2, filtered.size());
    }

    @Test
    public void testSortByMaxPlayers() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("rank<=300", GameData.MAX_PLAYERS).toList();
        assertEquals(3, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
        assertEquals("GoRami", filtered.get(1).getName());
        assertEquals("Go Fish", filtered.get(2).getName());
    }

    @Test
    public void testSortByMaxPlayersDesc() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("rank<=300", GameData.MAX_PLAYERS, false).toList();
        assertEquals(3, filtered.size());
        assertEquals("Go", filtered.get(2).getName());
        assertEquals("GoRami", filtered.get(1).getName());
        assertEquals("Go Fish", filtered.get(0).getName());
    }
}