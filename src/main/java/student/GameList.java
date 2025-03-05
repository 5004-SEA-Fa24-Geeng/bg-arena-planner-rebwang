package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

/**
 * The GameList class implements the IGameList interface and
 * manages a collection of BoardGame. It provides functionality to add,
 * remove, retrieve, and persist game names.
 */

public class GameList implements IGameList {
    /**
     * A set containing the names of the board games in the list.
     * This set ensures that the game names are unique and maintains insertion order.
     */
    private Set<String> listOfGames;

    /**
     * Constructs a new GameList with an empty set of game names.
     */
    public GameList() {
        listOfGames = new LinkedHashSet<>();
    }

    /**
     * Retrieves a sorted list of game names in case-insensitive order.
     *
     * @return A sorted List<String> of game names.
     */
    @Override
    public List<String> getGameNames() {
        List<String> listVersionOfGames = new ArrayList<>(List.copyOf(listOfGames));
        listVersionOfGames.sort(String.CASE_INSENSITIVE_ORDER);
        return listVersionOfGames;
    }

    /**
     * Clears all games from the list.
     */
    @Override
    public void clear() {
        listOfGames.clear();
    }

    /**
     * Returns the number of games in the list.
     *
     * @return The count of games.
     */
    @Override
    public int count() {
        return listOfGames.size();
    }

    /**
     * Saves the list of game names to a specified file.
     *
     * @param filename The name of the file to save the game names.
     */
    @Override
    public void saveGame(String filename) {
        try {
            List<String> gameNames = getGameNames();
            Files.write(Path.of(filename), gameNames, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename + ", Error message: " + e.getMessage());
        }
    }

    /**
     * Adds a game or a range of games to the list based on the provided string and filtered stream.
     *
     * @param str      The game name, index, or range in string format.
     * @param filtered A stream of BoardGame objects to filter games from.
     * @throws IllegalArgumentException If the format is invalid or the game does not exist.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        //listOfGames HashSet, str operation, Stream<BoardGame> filtered
        List<BoardGame> filteredList = filtered.toList();

        for (BoardGame game : filteredList) {
            if (game.getName().equals(str)) {
                listOfGames.add(str);
                return;
            }
        }

        String[] parts = str.split("-");
        if (parts.length < 1 || parts.length > 2) {
            throw new IllegalArgumentException("Invalid format for addToList"); }
        // "all" or an index
        else if (parts.length == 1) {
            // "all"
            if (parts[0].equalsIgnoreCase(ADD_ALL)) {
                listOfGames.addAll(filteredList.stream()
                        .map(BoardGame::getName)
                        .toList());
            } else {
                // check if the given index out of bounds
                try {
                    int index = Integer.parseInt(parts[0]) - 1;
                    if (index < 0 || index >= filteredList.size()) {
                        throw new IndexOutOfBoundsException("Index out of bounds");
                    }
                    listOfGames.add(filteredList.get(index).getName());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Game does not exist"); }
            }
        }
        // range format
        else {
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]);
            if (start >= 0 && end <= filteredList.size() && start <= end) {
                listOfGames.addAll(filteredList.subList(start, end).stream()
                        .map(BoardGame::getName)
                        .toList());
            }
            else {
                throw new IllegalArgumentException("Invalid range of games"); }
        }
    }

    /**
     * Removes a game or a range of games from the list based on the provided string.
     *
     * @param str The game name, index, or range in string format.
     * @throws IllegalArgumentException If the format is invalid or the game does not exist.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // remove all
        if (str.equalsIgnoreCase(ADD_ALL)) {
            clear();
            return;
        }

        String[] parts = str.split("-");
        if (parts.length == 1) {
            // remove by game name
            if (listOfGames.contains(parts[0])) {
                listOfGames.remove(parts[0]);
            } else {
                try {
                    // remove by index
                    int index = Integer.parseInt(parts[0]) - 1;
                    List<String> listVersionOfGames = getGameNames();
                    if (index < 0 || index >= listVersionOfGames.size()) {
                        throw new IndexOutOfBoundsException("Invalid index");
                    }
                    listOfGames.remove(listVersionOfGames.get(index));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Game does not exist");
                }
            }
        } else if (parts.length == 2) {
            // remove by range
            try {
                int start = Integer.parseInt(parts[0]) - 1;
                int end = Integer.parseInt(parts[1]);
                List<String> listVersionOfGames = getGameNames();
                if (start < 0 || end > listVersionOfGames.size() || start >= end) {
                    throw new IllegalArgumentException("Invalid range of games");
                }

                List<String> toKeep1 = new ArrayList<>(listVersionOfGames.subList(0, start));
                List<String> toKeep2 = new ArrayList<>(listVersionOfGames.subList(end, listVersionOfGames.size()));

                List<String> updatedList = new ArrayList<>();
                updatedList.addAll(toKeep1);
                updatedList.addAll(toKeep2);

                listOfGames = new LinkedHashSet<>(updatedList);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid range format");
            }
        } else {
            throw new IllegalArgumentException("Invalid format for removeFromList");
        }
    }
}
