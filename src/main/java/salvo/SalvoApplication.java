package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepo, GameRepository gameRepo, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepo, SalvoRepository salvoRepo, ScoreRepository scoreRepo) {
		return (args) -> {
			// save a couple of customers
			Player p1 = new Player("j.bauer@cto.gov", "24");
			Player p2 = new Player("c.obrian@ctu.gov", "42");
			Player p3 = new Player("kim_bauer@gmail.com", "kb");
			Player p4 = new Player("t.almeida@ctu.gov", "mole");
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

			List<String> slv1 = new ArrayList<>(Arrays.asList("A1", "H7", "J4"));
			List<String> slv2 = new ArrayList<>(Arrays.asList("A2", "B5", "D9"));
			List<String> slv3 = new ArrayList<>(Arrays.asList("A3", "I3", "G3"));
			List<String> slv4 = new ArrayList<>(Arrays.asList("B1", "F8", "C4"));
			List<String> slv5 = new ArrayList<>(Arrays.asList("B2", "D5", "H6"));
			List<String> slv6 = new ArrayList<>(Arrays.asList("B3", "F1", "I7"));
			List<String> slv7 = new ArrayList<>(Arrays.asList("C5", "C7", "C9"));
			List<String> slv8 = new ArrayList<>(Arrays.asList("C6", "G4", "H2"));
			List<String> slv9 = new ArrayList<>(Arrays.asList("C7", "A5", "B3"));
			List<String> slv10 = new ArrayList<>(Arrays.asList("D2", "H5", "I8"));
			List<String> slv11 = new ArrayList<>(Arrays.asList("D3", "E4", "F5"));
			List<String> slv12 = new ArrayList<>(Arrays.asList("D4", "J7", "E1"));
			List<String> slv13 = new ArrayList<>(Arrays.asList("E2", "E4", "E9"));

			Salvo shot1 = new Salvo(1, slv1);
			Salvo shot2 = new Salvo(1, slv2);
			Salvo shot11 = new Salvo(1, slv10);
			Salvo shot3 = new Salvo(2, slv3);
			Salvo shot4 = new Salvo(2, slv4);
			Salvo shot41 = new Salvo(2, slv11);
			Salvo shot5 = new Salvo(3, slv5);
			Salvo shot6 = new Salvo(3, slv6);
			Salvo shot61 = new Salvo(3, slv12);
			Salvo shot7 = new Salvo(4, slv7);
			Salvo shot8 = new Salvo(4, slv8);
			Salvo shot81 = new Salvo(4, slv13);
			Salvo shot9 = new Salvo(5, slv9);

			gp1.addSalvo(shot1);
			gp2.addSalvo(shot2);
			gp1.addSalvo(shot3);
			gp2.addSalvo(shot4);
			gp1.addSalvo(shot5);
			gp2.addSalvo(shot6);
			gp3.addSalvo(shot11);
			gp3.addSalvo(shot41);
			gp3.addSalvo(shot61);

			salvoRepo.save(shot1);
			salvoRepo.save(shot11);
			salvoRepo.save(shot2);
			salvoRepo.save(shot3);
			salvoRepo.save(shot4);
			salvoRepo.save(shot41);
			salvoRepo.save(shot5);
			salvoRepo.save(shot6);
			salvoRepo.save(shot61);
			salvoRepo.save(shot7);
			salvoRepo.save(shot8);
			salvoRepo.save(shot81);
			salvoRepo.save(shot9);

			Date dt1 = new Date();
			Date dt2 = new Date();
			Score scr1 = new Score(dt1,1,gp1);
			Score scr2 = new Score(dt1,0,gp2);
			Score scr3 = new Score(dt2,0.5,gp3);

			scoreRepo.save(scr1);
			scoreRepo.save(scr2);
			scoreRepo.save(scr3);

		};
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepo;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
                List<Player> players = playerRepo.findByUserName(name);
                if (!players.isEmpty()) {
                    Player player = players.get(0);
                    return new User(player.getUserName(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + name);
                }
            }
        };
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests()
                .antMatchers("/API/**").permitAll();*/
		http
				.formLogin()
					.usernameParameter("userName")
					.passwordParameter("password")
					.loginPage("/api/login")
					.and()
				.authorizeRequests()
					.antMatchers("/games.html").permitAll()
					.antMatchers("/games.js").permitAll()
					.antMatchers("/main.css").permitAll()
					.antMatchers("/api/games").permitAll()
					.antMatchers("/api/players").permitAll()
					.anyRequest().fullyAuthenticated();

        http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

	private void clearAuthenticationAttributes(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
