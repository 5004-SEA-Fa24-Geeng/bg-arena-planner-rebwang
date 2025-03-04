package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class GameList implements IGameList {
    private Set<String> listOfGames;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        listOfGames = new LinkedHashSet<>();
    }

    @Override
    public List<String> getGameNames() {
        List<String> listVersionOfGames = new ArrayList<>(List.copyOf(listOfGames));
        listVersionOfGames.sort(String.CASE_INSENSITIVE_ORDER);
        return listVersionOfGames;
    }


    @Override
    public void clear() {
        listOfGames.clear();
    }

    @Override
    public int count() {
        return listOfGames.size();
    }

    @Override
    public void saveGame(String filename) {
        List<String> gameNames = getGameNames();

        try {
            Files.write(Path.of(filename), gameNames);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }

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
            throw new IllegalArgumentException("Invalid format for addToList");
        }
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
                    throw new IllegalArgumentException("Game does not exist");
                }
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
                throw new IllegalArgumentException("Invalid range of games");

            }
        }
    }

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
