package Arquivos;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException, EOFException {

		try (ServerSocket s = new ServerSocket(2001)) {
			System.out.println("esperando para conectarse...");
			Socket conexao = s.accept();
			System.out.println("Conectou!\n");

			while (true) {
				DataInputStream entrada = new DataInputStream(conexao.getInputStream());
				DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());

				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

				String linha = entrada.readUTF();
				if (linha == null || linha.trim().equals("") || linha.trim().equalsIgnoreCase("sair")) {
					saida.writeUTF("sair");
					System.out.println("\nO \r\n conexión cerrada, cliente desconectado!");
					conexao.close();
					break;
				}
				System.out.println("Cliente: " + linha);
				System.out.print("> ");
				linha = teclado.readLine();
				saida.writeUTF("Servidor: " + linha);
			}
		} catch (Exception e) {
			System.out.println("¡No se pudo iniciar el servidor!");
		}
	}
}
