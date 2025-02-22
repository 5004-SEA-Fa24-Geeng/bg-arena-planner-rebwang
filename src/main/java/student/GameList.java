package student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameList implements IGameList {
    Set<String> listOfGames;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        //throw new UnsupportedOperationException("Unimplemented constructor 'GameList'");
        listOfGames = new HashSet<>();
    }

    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getGameNames'");
        List<String> listVersionOfGames = List.copyOf(listOfGames);
        return listVersionOfGames;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'count'");
        return listOfGames.size();
    }

    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
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

        if (parts.length == 1) {
            if (parts[0].equalsIgnoreCase(ADD_ALL)) {
                listOfGames.addAll(filteredList.stream()
                        .map(BoardGame::getName)
                        .collect(Collectors.toList()));
            } else {
                try {
                    BoardGame toAdd = filteredList.get(Integer.parseInt(parts[0]));
                    listOfGames.add(toAdd.getName());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Game does not exist");
                }
            }
        } else {
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]);
            if (start >= 0 && end <= filteredList.size() && start <= end) {
                listOfGames.addAll(filteredList.subList(start, end).stream()
                        .map(BoardGame::getName)
                        .collect(Collectors.toList()));
            }
            else {
                throw new IllegalArgumentException("Invalid range of games");
            }
        }
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromList'");
    }

}
