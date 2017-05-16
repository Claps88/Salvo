package salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepo, GameRepository gameRepo, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepo) {
		return (args) -> {
			// save a couple of customers
			Player p1 = new Player("j.bauer@cto.gov");
			Player p2 = new Player("c.obrian@ctu.gov");
			Player p3 = new Player("kim_bauer@gmail.com");
			Player p4 = new Player("t.almeida@ctu.gov");
			playerRepo.save(p1);
			playerRepo.save(p2);
			playerRepo.save(p3);
			playerRepo.save(p4);

			Game g1 = new Game();
			Game g2 = new Game();
			Game g3 = new Game();
			gameRepo.save(g1);
			gameRepo.save(g2);
			gameRepo.save(g3);


			GamePlayer gp1 = new GamePlayer(g1,p1);
			GamePlayer gp2 = new GamePlayer(g1,p2);
			GamePlayer gp3 = new GamePlayer(g2,p3);
			gamePlayerRepo.save(gp1);
			gamePlayerRepo.save(gp2);
			gamePlayerRepo.save(gp3);


			List<String> loc1 = new ArrayList<>(Arrays.asList("H1", "H2", "H3"));
			List<String> loc2 = new ArrayList<>(Arrays.asList("C1", "D1", "E1", "F1"));
			List<String> loc3 = new ArrayList<>(Arrays.asList("G7", "H7"));
			List<String> loc4 = new ArrayList<>(Arrays.asList("B5", "B6", "B7"));
			List<String> loc5 = new ArrayList<>(Arrays.asList("C3", "D3", "E3", "F3", "G3"));
			List<String> loc6 = new ArrayList<>(Arrays.asList("A6", "B6", "C6"));
			List<String> loc7 = new ArrayList<>(Arrays.asList("B8", "C8", "D8", "E8", "F8"));
			List<String> loc8 = new ArrayList<>(Arrays.asList("G6", "F6"));
			List<String> loc9 = new ArrayList<>(Arrays.asList("B1", "C1", "D1"));
			List<String> loc10 = new ArrayList<>(Arrays.asList("H3", "H4", "H5", "H6"));

			Ship sh1 = new Ship("carrier", loc5);
			Ship sh2 = new Ship("patrol boat", loc3);
			Ship sh3 = new Ship("battleship", loc2);
			Ship sh4 = new Ship("submarine", loc1);
			Ship sh5 = new Ship("destroyer", loc4);
			Ship sh6 = new Ship("carrier", loc7);
			Ship sh7 = new Ship("patrol boat", loc8);
			Ship sh8 = new Ship("battleship", loc10);
			Ship sh9 = new Ship("submarine", loc9);
			Ship sh10 = new Ship("destroyer", loc6);

			gp1.addShip(sh1);
			gp1.addShip(sh2);
			gp1.addShip(sh3);
			gp1.addShip(sh4);
			gp1.addShip(sh5);
			gp2.addShip(sh6);
			gp2.addShip(sh7);
			gp2.addShip(sh8);
			gp2.addShip(sh9);
			gp2.addShip(sh10);

			shipRepo.save(sh1);
			shipRepo.save(sh2);
			shipRepo.save(sh3);
			shipRepo.save(sh4);
			shipRepo.save(sh5);
			shipRepo.save(sh6);
			shipRepo.save(sh7);
			shipRepo.save(sh8);
			shipRepo.save(sh9);
			shipRepo.save(sh10);




		};
	}
}
