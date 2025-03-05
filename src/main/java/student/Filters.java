package student;

public final class Filters {
    private Filters() { }

    /**
     * Filters a BoardGame based on the specified column, operation, and value.
     * @param game the object to be filtered.
     * @param column the column that determines which attribute of the game to filter.
     * @param op the specifying the comparison operation to be applied.
     * @param value the value to compare against the specified column.
     * @return true if the game satisfies the filter condition, false otherwise.
     */
    public static boolean filter(BoardGame game, GameData column, Operations op, String value) {
        switch (column) {
            case NAME:
                // filter the name
                return filterString(game.getName(), op, value);
            case MAX_PLAYERS:
                // filter based on max-players
                 return filterNum(game.getMaxPlayers(), op, value);
             case MIN_PLAYERS:
                 return filterNum(game.getMinPlayers(), op, value);
            case MIN_TIME:
                return filterNum(game.getMinPlayTime(), op, value);
            case MAX_TIME:
                return filterNum(game.getMaxPlayTime(), op, value);
            case RANK:
                return filterNum(game.getRank(), op, value);
            case YEAR:
                return filterNum(game.getYearPublished(), op, value);
            case DIFFICULTY:
                return filterNum(game.getDifficulty(), op, value);
            case RATING:
                return filterNum(game.getRating(), op, value);
            default:
                return true;
        }
    }

    /**
     * Filters a string value based on the specified comparison operation.
     * @param gameStrData The string data to be compared.
     * @param op the operations specifying the type of comparison.
     * @param value the string value to compare against gameData.
     * @return true if the comparison condition is met, false otherwise.
     */
    public static boolean filterString(String gameStrData, Operations op, String value) {
        switch (op) {
            case EQUALS:
                return gameStrData.equalsIgnoreCase(value);
            case LESS_THAN:
                return gameStrData.compareToIgnoreCase(value) < 0;
            case GREATER_THAN:
                return gameStrData.compareToIgnoreCase(value) > 0;
            case LESS_THAN_EQUALS:
                return gameStrData.compareToIgnoreCase(value) <= 0;
            case GREATER_THAN_EQUALS:
                return gameStrData.compareToIgnoreCase(value) >= 0;
            case NOT_EQUALS:
                return !gameStrData.equalsIgnoreCase(value);
            case CONTAINS:
                return gameStrData.toLowerCase().contains(value.toLowerCase());
            default:
                return true;
        }
    }

    /**
     * Filters a numeric value based on the specified comparison operation.
     * @param gameNumData the numeric data to be compared.
     * @param op the Operations specifying the type of comparison.
     * @param value the string representation of the numeric value to compare against gameNumData.
     * @return true if the comparison condition is met, false otherwise.
     */
    public static boolean filterNum(double gameNumData, Operations op, String value) {
        double val = Double.parseDouble(value);
        switch (op) {
            case EQUALS:
                return gameNumData == val;
            case GREATER_THAN:
                return gameNumData > val;
            case LESS_THAN:
                return gameNumData < val;
            case GREATER_THAN_EQUALS:
                return gameNumData >= val;
            case LESS_THAN_EQUALS:
                return gameNumData <= val;
            case NOT_EQUALS:
                return gameNumData != val;
            default:
                return true;
        }
    }
}
