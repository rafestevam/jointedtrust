package jointedtrust;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptTest {

	public static void main(String[] args) {
		
//		boolean checked = BCrypt.checkpw("C0c0m0l3", "$2y$12$2QLbjBlOnWqm5Y7hRyFKVuebxx2XO0XF28WB/XeJbBKUiiEYybHy2");
//		System.out.println(checked);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode("C0c0m0l3");
		System.out.println(encoded);
		
	}
	
}
