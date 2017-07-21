import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		
		String str = new Download().go();
		
		LocalDateTime lv = LocalDateTime.now();
		System.out.println(lv.getSecond());
		Importacao importacao = new Importacao();
		importacao.importar(str);
		
	} // end of main

}
