package hr.fer.zemris.optjava.dz11.rng;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class RNG {
	private static IRNGProvider rngProvider;
	static {
	// Stvorite primjerak razreda Properties;
	// Nad Classloaderom razreda RNG tražite InputStream prema resursu rng-config.properties
	// recite stvorenom objektu razreda Properties da se uèita podatcima iz tog streama.
	// Dohvatite ime razreda pridruženo kljuèu "rng-provider"; zatražite Classloader razreda
	// RNG da uèita razred takvog imena i nad dobivenim razredom pozovite metodu newInstance()
	// kako biste dobili jedan primjerak tog razreda; castajte ga u IRNGProvider i zapamtite.
		Properties properties = new Properties();
		ClassLoader loader = RNG.class.getClassLoader();
		InputStream podaci = loader.getResourceAsStream("rng-config.properties");
		try {
			properties.load(podaci);
			rngProvider = (IRNGProvider) loader.loadClass(properties.getProperty("rng-provider")).newInstance();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}
