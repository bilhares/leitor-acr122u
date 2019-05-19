package projeto;

import java.math.BigInteger;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class Teste {

	static String bin2hex(byte[] data) {
		return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
	}

	public static void main(String[] args) {

		try {

			// Display the list of terminals
			TerminalFactory factory = TerminalFactory.getDefault();
			List<CardTerminal> terminals = factory.terminals().list();
			System.out.println("Terminals: " + terminals);

			// Use the first terminal

			int size = terminals.size();
			CardTerminal terminal = terminals.get(0);

			if (!terminal.isCardPresent()) {

				while (!terminal.isCardPresent()) {
					try {

						Card card = terminal.connect("*");
						System.out.println("Card: " + card);
						CardChannel channel = card.getBasicChannel();

						// Send test command
						ResponseAPDU response = channel.transmit(new CommandAPDU(
								new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
						System.out.println("Response: " + response.toString());

						if (response.getSW1() == 0x63 && response.getSW2() == 0x00)
							System.out.println("Failed");

						System.out.println("UID: " + bin2hex(response.getData()));

						card.disconnect(false);
					} catch (Exception e) {
						System.out.println("insira o cartao");
					}
				}

			} else {
				Card card = terminal.connect("*");
				System.out.println("Card: " + card);
				CardChannel channel = card.getBasicChannel();

				// Send test command
				ResponseAPDU response = channel.transmit(new CommandAPDU(
						new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
				System.out.println("Response: " + response.toString());

				if (response.getSW1() == 0x63 && response.getSW2() == 0x00)
					System.out.println("Failed");

				System.out.println("UID: " + bin2hex(response.getData()));

				card.disconnect(false);
			}

//			while (size <= 1) {
//				try {
//
//					// Connect wit hthe card
//					Card card = terminal.connect("*");
//					System.out.println("Card: " + card);
//					CardChannel channel = card.getBasicChannel();
//
//					// Send test command
//					ResponseAPDU response = channel.transmit(new CommandAPDU(
//							new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
//					System.out.println("Response: " + response.toString());
//
//					if (response.getSW1() == 0x63 && response.getSW2() == 0x00)
//						System.out.println("Failed");
//
//					System.out.println("UID: " + bin2hex(response.getData()));
//
//					card.disconnect(false);
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("nenum cartao inserido ");
//				}
//			}
			// Disconnect the card

		} catch (Exception e) {

			System.out.println("Ouch: " + e.toString());

		}
	}
}
