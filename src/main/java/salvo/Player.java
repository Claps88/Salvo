package salvo;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long Id;

    public long getId() {
        return Id;
    }

    public void setId(long playerId) {
        this.Id = playerId;
    }

    private String userName;

    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> score;

    public Player(){}

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return Id + " " + userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Score isPlaying(Game game) {
        Optional<Score> scoreActual = score.stream()
                .filter(sc -> sc.getGame().equals(game))
                .findFirst();
        if (scoreActual.isPresent()) {
            return scoreActual.get();
        } else {
            return null;
        }
    }

    public double getTotalScore() {
        if (score == null) {
            return 0;
        } else {
            return score.stream()
                    .map(score -> score.getScore())
                    .mapToDouble(d -> d.doubleValue())
                    .sum();
        }
    }

    public long getWins() {
        return score.stream()
                .map(sc -> sc.getScore())
                .filter(s -> s == 1)
                .count();
    }

    public long getDraws() {
        return score.stream()
                .map(sc -> sc.getScore())
                .filter(sc -> sc == 0.5)
                .count();
    }

    public long getLosses() {
        return score.stream()
                .map(sc -> sc.getScore())
                .filter(sc -> sc == 0)
                .count();
    }
}
