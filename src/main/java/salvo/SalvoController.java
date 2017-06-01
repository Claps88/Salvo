package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private PlayerRepository playerRepo;

    @RequestMapping("/games")
    public Map<String, Object> getInfo(Authentication auth) {

        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("currentPlayer", authInfo(auth));
        dto.put("games", gameRepo.findAll().stream()
                .map(game -> gameInfo(game))
                .collect(Collectors.toList()));
        dto.put("score", playerRepo.findAll().stream()
                .map(player -> scoreInfo(player))
                .collect(Collectors.toList()));
        return dto;
    }
    private Map<String, Object> authInfo(Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<>();
        if(authentication != null){
            List<Player> wtv = playerRepo.findByUserName(authentication.getName());
            Player blabla = wtv.get(0);
            dto.put("id", blabla.getId());
            dto.put("userName", blabla.getUserName());
            return dto;
        }
        else return null;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> scoreInfo(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("playerId", player.getId());
        dto.put("email", player.getUserName());
        dto.put("totalScore", player.getTotalScore());
        dto.put("wins", player.getWins());
        dto.put("draws", player.getDraws());
        dto.put("losses", player.getLosses());
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
        if(gamePlayer.getScore() != null) {
            dto.put("score", gamePlayer.getScore().getScore());
        }
        else{
            dto.put("score", null);
        }
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
        dto.put("salvoes", currentGame.getGamePlayers().stream()
                .map(gp -> salvoGPs(gp))
                .collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> findGP(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", findPL(gamePlayer.getPlayer()));
        return dto;
    }

    private Map<String, Object> findPL(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    private Map<String, Object> fleet(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getShipType());
        dto.put("locations", ship.getLocations());
        return dto;
    }

    private Map<String, Object> salvoInfo(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurnNr());
        dto.put("players", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getLocation());
        return dto;
    }

    private List<Map<String, Object>> salvoGPs(GamePlayer gamePlayer){

        Set<Salvo> salvos = gamePlayer.getSalvoes();
        List<Map<String, Object>> dto2 = new ArrayList<>();



        for (Salvo salvo : salvos) {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("turn", salvo.getTurnNr());
            dto.put("player", salvo.getGamePlayer().getPlayer().getId());
            dto.put("locations", salvo.getLocation());
            dto2.add(dto);
        }
        return dto2;
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<String> createPlayer(@RequestParam String userName,@RequestParam String password) {

        List <Player> player = playerRepo.findByUserName(userName);
        if (userName.isEmpty()) {
            return new ResponseEntity<>("No name given", HttpStatus.FORBIDDEN);
        }

        if (!player.isEmpty()) {
            return new ResponseEntity<>("Name already used", HttpStatus.CONFLICT);
        }

        playerRepo.save(new Player(userName, password));
        return new ResponseEntity<>( userName + "added", HttpStatus.CREATED);
    }
}




