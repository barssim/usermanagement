package ma.solide.usermanagement;

import org.springframework.boot.SpringApplication;

public class TestUsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(UsermanagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
