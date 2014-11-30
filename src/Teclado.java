	import java.io.*;
	
	public class Teclado{
		private InputStreamReader filtro;
		private BufferedReader teclado;
		public Teclado () {
			filtro = new InputStreamReader(System.in);
			teclado = new BufferedReader (filtro);
		}
		public String leerString () throws IOException {
			return(teclado.readLine());
		}
		public String leerString (String cadena) throws IOException {
			System.out.print(cadena);
			return(teclado.readLine());
		}
		public byte leerByte () throws IOException {
			return (Byte.parseByte(teclado.readLine ( )  )   );
		}
		public short leerShort () throws IOException {
			return (Short.parseShort(teclado.readLine() ) );
		}
		public char leerChar () throws IOException {
			return ( (char) teclado.read()  );
		}
		public int leerInt () throws IOException {
			return (Integer.parseInt (teclado.readLine() )  );
		}
		public long leerLong () throws IOException {
			return (Long.parseLong (teclado.readLine() )  );
		}
		public float leerFloat () throws IOException {
			return (Float.parseFloat(teclado.readLine() )  );
		}
		public double leerDouble () throws IOException {
			return (Double.parseDouble (teclado.readLine() )  );
		}
		
	}