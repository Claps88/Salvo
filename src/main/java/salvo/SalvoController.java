package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Claps on 04/05/2017.
 */
@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private GamePlayerRepository gpRepo;

    @RequestMapping("/games")
    public Map<String, Object> getIds() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("games", gameRepo.findAll().stream()
                .map(game -> gameInfo(game))
                .collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> gameInfo(Game game) {
        Map<String, Object> newdto = new LinkedHashMap<>();
        newdto.put("id", game.getId());
        newdto.put("created", game.getDate());
        newdto.put("gamePlayer", game.getGamePlayers().stream()
                .map(gp -> getGM(gp))
                .collect(Collectors.toList()));
        return newdto;
    }

    private Map<String, Object> getGM(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", getP(gamePlayer.getPlayer()));
        return dto;
    }

    private Map<String, Object> getP(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    @RequestMapping("/game_view/{nn}")
    public Map<String, Object> gameView(@PathVariable Long nn){
        GamePlayer currentGP = gpRepo.findOne(nn);
        Game currentGame = currentGP.getGame();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", currentGame.getId());
        dto.put("created", currentGame.getDate());
        dto.put("gamePlayers", currentGame.getGamePlayers().stream()
                .map(cg -> findGP(cg))
                .collect(Collectors.toList()));
        dto.put("ships", currentGP.getShips().stream()
                .map(ship -> fleet(ship))
                .collect(Collectors.toList()));
        return dto;
    }

    public Map<String, Object> findGP(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", findPL(gamePlayer.getPlayer()));
        return dto;
    }

    public Map<String, Object> findPL(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    public Map<String, Object> fleet(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getShipType());
        dto.put("locations", ship.getLocations());
        return dto;
    }
}




