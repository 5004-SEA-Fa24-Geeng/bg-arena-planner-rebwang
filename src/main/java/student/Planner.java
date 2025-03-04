package student;


import java.util.*;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    Set<BoardGame> games;
    Set<BoardGame> filteredGames;

    public Planner(Set<BoardGame> games) {
        this.games = games;
        this.filteredGames = new HashSet<>();
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Helper function to filter games by single condition
     * @return Stream<BoardGame> a filtered list
     * @param filter String contains column, operator, condition e.g. "name == Go"
     * @param filteredGames Stream<BoardGame> full game list
     */
    private Stream<BoardGame> filterSingle (String filter, Stream<BoardGame> filteredGames) {
        //handle getting operation, game attribute
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }
        // remove spaces
        filter = filter.replaceAll(" ", "");

        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }
        GameData column;
        try {
            column = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        String value;
        try {
            value = parts[1].trim();
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

//        System.out.println("Operator is :" + operator);
//        System.out.println("Operator is :" + column);
//        System.out.println("Operator is :" + value);

        // Filters.filter(board game, game data, op, string value)
        // Stream<BoardGames> filteredGames
        List<BoardGame> filteredGameList = filteredGames.filter(game ->
                Filters.filter(game, column, operator, value)).toList();

        return filteredGameList.stream();
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

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

    @Override
    public void reset() {
        filteredGames = new HashSet<>();
    }

}
