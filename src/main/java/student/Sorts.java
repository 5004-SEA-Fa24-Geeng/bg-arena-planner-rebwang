package student;

import java.util.Comparator;

/**
 * The Sorts class provides utility methods to obtain comparators for sorting board games.
 * This class should not be instantiated.
 */
public final class Sorts {

    // Private constructor to prevent instantiation
    private Sorts() { }

    /**
     * Returns a comparator for sorting BoardGame objects based on the specified attribute.
     *
     * @param sortOn The GameData(column) attribute to sort by.
     * @param asc true for ascending order, false for descending order.
     * @return A comparator for sorting BoardGame objects.
     */
    public static Comparator<BoardGame> getSortType(GameData sortOn, boolean asc) {
        Comparator<BoardGame> comparator = null;
        switch (sortOn) {
            case MAX_PLAYERS:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getMaxPlayers(), o2.getMaxPlayers());
                    return asc ? compare : -compare;
                };
            case MIN_PLAYERS:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getMinPlayers(), o2.getMinPlayers());
                    return asc ? compare : -compare;
                };
            case MAX_TIME:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getMaxPlayTime(), o2.getMaxPlayTime());
                    return asc ? compare : -compare;
                };
            case MIN_TIME:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getMinPlayTime(), o2.getMinPlayTime());
                    return asc ? compare : -compare;
                };
            case DIFFICULTY:
                return (o1, o2) -> {
                    int compare = Double.compare(o1.getDifficulty(), o2.getDifficulty());
                    return asc ? compare : -compare;
                };
            case RANK:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getRank(), o2.getRank());
                    return asc ? compare : -compare;
                };
            case RATING:
                return (o1, o2) -> {
                    int compare = Double.compare(o1.getRating(), o2.getRating());
                    return asc ? compare : -compare;
                };
            case YEAR:
                return (o1, o2) -> {
                    int compare = Integer.compare(o1.getYearPublished(), o2.getYearPublished());
                    return asc ? compare : -compare;
                };
            default:
                return (o1, o2) -> {
                    int compare = o1.getName().compareToIgnoreCase(o2.getName());
                    return asc ? compare : -compare;
                };
        }
    }
}
