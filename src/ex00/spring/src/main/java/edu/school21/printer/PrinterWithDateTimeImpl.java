package edu.school21.printer;

import org.springframework.beans.factory.annotation.Autowired;
import edu.school21.renderer.Renderer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class PrinterWithDateTimeImpl implements Printer {

    private final Renderer renderer;
    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void output(String s) {
        renderer.print("[" + LocalDateTime.now().format(FORMATTER) + "] " + s);
    }
}
