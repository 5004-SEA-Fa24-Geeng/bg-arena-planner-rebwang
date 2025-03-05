package student;


import java.util.*;
import java.util.stream.Stream;

/**
 * The Planner class implements the IPlanner interface and
 * provides functionality for filtering and sorting a collection of board games.
 */
public class Planner implements IPlanner {
    private Set<BoardGame> games;
    private Set<BoardGame> filteredGames;

    /**
     * Constructs a Planner with the given set of board games. Usually a full list of all games.
     *
     * @param games A set of BoardGame objects to be managed by the planner.
     */
    public Planner(Set<BoardGame> games) {
        this.games = games;
        this.filteredGames = new HashSet<>();
    }

    /**
     * Filters board games when only one param - filter condition is given.
     *
     * @param filter A string representing the filter condition. e.g. "minPlayer>5"
     * @return A Stream<BoardGame> of filtered games.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * A helper function to filter games by single condition.
     * @param filter String contains column, operator, condition e.g. "name == Go".
     * @param filteredGames Stream<BoardGame> full game list
     * @return Stream<BoardGame> a stream collection of the filtered games.
     */
    private Stream<BoardGame> filterSingle (String filter, Stream<BoardGame> filteredGames) {
        //handle getting operation, game attribute
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }

        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }
        GameData column;
        try {
            column = GameData.fromString(parts[0].trim());
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        String value;
        try {
            value = parts[1].trim();
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        // Filters.filter(board game, game data, op, string value)
        // Stream<BoardGames> filteredGames
        List<BoardGame> filteredGameList = filteredGames.filter(game ->
                Filters.filter(game, column, operator, value)).toList();

        return filteredGameList.stream();
    }

    /**
     * Filters board games based on a given condition and sorts them based on a specified column.
     *
     * @param filter A string representing the filter condition.
     * @param sortOn The GameData attribute to sort the filtered results on.
     * @return A Stream<BoardGame> of filtered and sorted games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Filters board games based on multiple conditions and sorts them based on a specified column.
     *
     * @param filter A string containing multiple filter conditions separated by commas.
     * @param sortOn The GameData attribute to sort the filtered results on.
     * @param ascending true for ascending order, false for descending order.
     * @return A Stream<BoardGame> of filtered and sorted games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        String[] filters  = filter.split(",");
        Stream<BoardGame> filteredStream = games.stream();
        for (String singleFilter : filters) {
            filteredStream = filterSingle(singleFilter, filteredStream);
        }
        Comparator<BoardGame> comparator = Sorts.getSortType(sortOn, ascending);
        return filteredStream.sorted(comparator);
    }

    /**
     * Resets the filtered games set, clearing all applied filters.
     */
    @Override
    public void reset() {
        filteredGames = new HashSet<>();
    }

}
