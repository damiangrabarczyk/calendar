package View;

import java.sql.SQLException;
import java.util.EventListener;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public interface btnListener extends EventListener {
	public void actionEventOccured(actionEvent event) throws SQLException;
}
