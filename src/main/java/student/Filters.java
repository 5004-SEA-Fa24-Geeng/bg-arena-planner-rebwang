package student;

public class Filters {
    private Filters() {}

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
     * gameData -> game name
     * should be case-insensitive!
     */
    public static boolean filterString(String gameData, Operations op, String value) {
        switch (op) {
            case EQUALS:
                return gameData.equalsIgnoreCase(value);
            case LESS_THAN:
                return gameData.compareToIgnoreCase(value) < 0;
            case GREATER_THAN:
                return gameData.compareToIgnoreCase(value) > 0;
            case LESS_THAN_EQUALS:
                return gameData.compareToIgnoreCase(value) <= 0;
            case GREATER_THAN_EQUALS:
                return gameData.compareToIgnoreCase(value) >= 0;
            case NOT_EQUALS:
                return !gameData.equalsIgnoreCase(value);
            case CONTAINS:
                return gameData.toLowerCase().contains(value.toLowerCase());
            default:
                return true;
        }
    }

    public static boolean filterNum(double gameData, Operations op, String value) {
        double val = Double.parseDouble(value);
        switch (op) {
            case EQUALS:
                return gameData == val;
            case GREATER_THAN:
                return gameData > val;
            case LESS_THAN:
                return gameData < val;
            case GREATER_THAN_EQUALS:
                return gameData >= val;
            case LESS_THAN_EQUALS:
                return gameData <= val;
            case NOT_EQUALS:
                return gameData != val;
            default:
                return true;
        }
    }
}
