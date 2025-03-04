package student;

import java.util.Comparator;

public class Sorts {
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
