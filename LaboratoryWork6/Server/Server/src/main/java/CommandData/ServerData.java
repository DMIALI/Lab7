package CommandData;

import java.io.Serializable;

public record ServerData(Long counter, String message, PrintType printType) implements Serializable {
}
