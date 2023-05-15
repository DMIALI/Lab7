package ManagerOfCommands.Commands;

import ManagerOfCommands.CommandData.ClientData;
import ManagerOfCommands.DataTypes.*;
import Utils.Operation;
import Utils.PrimitiveTypeAsserter;
import Utils.Printer;
import Utils.RepeatInput;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Update extends Command{

    public ClientData processing(String title, ArrayList<String> args) {
        if (PrimitiveTypeAsserter.checkLong(args.get(0))){
            printer.outPrintln("Изменение данных музыкальной группы");
            Object id = (Long.valueOf(args.get(0)));
            Object name = receiveName(scanner, printer);
            Object coordinates = receiveCoordinates(scanner, printer);
            Object date = new Date();
            Object numberOfParticipants = receiveNumberOfParticipants(scanner, printer);
            Object albumsCount = receiveAlbumsCount(scanner, printer);
            Object musicGenre = receiveMusicGenre(scanner, printer);
            Object person = receivePerson(scanner, printer);
            ArrayList<Object> res = new ArrayList<Object>();
            res.add(id);
            res.add(name);
            res.add(coordinates);
            res.add(date);
            res.add(numberOfParticipants);
            res.add(albumsCount);
            res.add(musicGenre);
            res.add(person);
            ClientData clientData = new ClientData();
            clientData.setName("add");
            clientData.setArgs(res);
            return clientData;
        }
        else{
            printer.errPrintln("id элемента - число типа long");
            return null;
        }
    }

    /**
     * Receive front man's location: name from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return String
     */
    protected String receiveLocationName(Scanner scanner, Printer printer) {
        printer.outPrint("Название: ");
        String name = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return !input.equals("");
            }
        };
        if (!func.execute(name)) {
            RepeatInput.go(name,"Название: ", "Название не может быть пустым", func, scanner, printer);
        }
        return name;
    }

    /**
     * Receive front man's location: z from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Long
     */
    protected long receiveLocationZ(Scanner scanner, Printer printer) {
        printer.outPrint("Координата Z: ");
        String z = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return PrimitiveTypeAsserter.checkLong(input);
            }
        };
        if (!func.execute(z)) {
            z = RepeatInput.go(z,"Координата Z: ", "Введенное значение не является числом типа long", func, scanner, printer);
        }
        return Long.parseLong(z);
    }

    /**
     * Receive front man's location: y from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Float
     */
    protected Float receiveLocationY(Scanner scanner, Printer printer) {
        printer.outPrint("Координата Y: ");
        String y = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return PrimitiveTypeAsserter.checkFloat(input);
            }
        };
        if (!func.execute(y)) {
            y = RepeatInput.go(y,"Координата Y: ", "Введенное значение не является числом типа float", func, scanner, printer);
        }
        return Float.valueOf(y);
    }

    /**
     * Receive front man's location: x from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Integer
     */
    protected int receiveLocationX(Scanner scanner, Printer printer) {
        printer.outPrint("Координата X: ");
        String x = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return PrimitiveTypeAsserter.checkInteger(input);
            }
        };
        if (!func.execute(x)) {
            x = RepeatInput.go(x,"Координата X: ", "Введенное значение не является числом типа integer", func, scanner, printer);
        }
        return Integer.parseInt(x);
    }

    /**
     * Receive front man's location from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Location
     */
    protected Location receiveLocation(Scanner scanner, Printer printer) {
        printer.outPrintln("Местоположение");
        int x = receiveLocationX(scanner, printer);
        Float y = receiveLocationY(scanner, printer);
        long z = receiveLocationZ(scanner, printer);
        String name = receiveLocationName(scanner, printer);
        return new Location(x, y, z, name);
    }


    /**
     * Receive front man's country from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Country
     */
    protected Country receiveCountry(Scanner scanner, Printer printer) {
        printer.outPrint("Страна проживания (Russia, United_Kingdom, Germany, Vatican): ");
        String country = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                input = input.toUpperCase();
                return input.equals("RUSSIA")||input.equals("UNITED_KINGDOM")||input.equals("GERMANY")||input.equals("VATICAN");
            }
        };
        if (!func.execute(country)) {
            country = RepeatInput.go(country,"Страна проживания (Russia, United_Kingdom, Germany, Vatican): ", "Введенное значение не соответствует одному из допустимых (Russia, United_Kingdom, Germany, Vatican)", func, scanner, printer);
        }
        return Country.valueOf(country.toUpperCase());
    }

    /**
     * Receive front man's hair color id from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Color
     */
    protected Color receiveColor(Scanner scanner, Printer printer) {
        printer.outPrint("Цвет волос (green, yellow, orange, brown): ");
        String color = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                input = input.toUpperCase();
                return input.equals("GREEN")||input.equals("YELLOW")||input.equals("ORANGE")||input.equals("BROWN");
            }
        };
        if (!func.execute(color)) {
            color = RepeatInput.go(color,"Цвет волос (green, yellow, orange, brown): ", "Введенное значение не соответствует одному из допустимых (green, yellow, orange, brown)", func, scanner, printer);
        }
        return Color.valueOf(color.toUpperCase());
    }

    /**
     * Receive front man's passport id from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return String
     */
    protected String receivePassportID(Scanner scanner, Printer printer) {
        printer.outPrint("ID паспорта: ");
        String passportID = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return (input.length() > 6);
            }
        };
        if (!func.execute(passportID)) {
            RepeatInput.go(passportID,"ID паспорта: ", "ID паспорта не может быть короче 6 символов ", func, scanner, printer);
        }
        return passportID;
    }
    /**
     * Receive front man's name from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return String
     */
    protected String receivePersonName(Scanner scanner, Printer printer) {
        printer.outPrint("Имя: ");
        String name = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return !input.equals("");
            }
        };
        if (!func.execute(name)) {
            RepeatInput.go(name,"Имя: ", "Имя не может быть пустым", func, scanner, printer);
        }
        return name;
    }
    /**
     * Receive music band's front man from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Person
     */
    protected Person receivePerson(Scanner scanner, Printer printer) {
        printer.outPrintln("Ведущий вокалист");
        String name = receivePersonName(scanner, printer);
        String passportID = receivePassportID(scanner, printer);
        Color color = receiveColor(scanner, printer);
        Country country = receiveCountry(scanner, printer);
        Location location = receiveLocation(scanner, printer);
        return new Person(name, passportID, color, country, location);
    }
    /**
     * Receive music band's music genre from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return MusicGenre
     */
    protected MusicGenre receiveMusicGenre(Scanner scanner, Printer printer) {
        printer.outPrint("Музыкальный жанр (jazz, soul, pop): ");
        String musicGenre = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                input = input.toUpperCase();
                return input.equals("JAZZ")||input.equals("SOUL")||input.equals("POP");
            }
        };
        if (!func.execute(musicGenre)) {
            musicGenre = RepeatInput.go(musicGenre,"Музыкальный жанр (jazz, soul, pop): ", "Введенное значение не соответствует одному из допустимых (jazz, soul, pop)", func, scanner, printer);
        }
        return MusicGenre.valueOf(musicGenre.toUpperCase());
    }
    /**
     * Receive music band's album's count from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return long
     */
    protected long receiveAlbumsCount(Scanner scanner, Printer printer) {
        printer.outPrint("Количество альбомов: ");
        String albumsCount = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return (PrimitiveTypeAsserter.checkLong(input))&&((Long.parseLong(input)) > 0);
            }
        };
        if (!func.execute(albumsCount)) {
            albumsCount = RepeatInput.go(albumsCount,"Количество альбомов: ", "Введенное значение не является числом типа long или указано количество меньше 0", func, scanner, printer);
        }
        return Long.parseLong(albumsCount);
    }
    /**
     * Receive music band's number of participants from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return long
     */
    protected long receiveNumberOfParticipants(Scanner scanner, Printer printer) {
        printer.outPrint("Количество участников: ");
        String numberOfParticipants = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return (PrimitiveTypeAsserter.checkLong(input))&&((Long.parseLong(input)) > 0);
            }
        };
        if (!func.execute(numberOfParticipants)) {
            numberOfParticipants = RepeatInput.go(numberOfParticipants,"Количество участников: ", "Введенное значение не является числом типа long или указано количество меньше 0", func, scanner, printer);
        }
        return Long.parseLong(numberOfParticipants);
    }
    /**
     * Receive music band's coordinates from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Coordinates
     */
    protected Coordinates receiveCoordinates(Scanner scanner, Printer printer) {
        printer.outPrintln("Местоположение ");
        Long x = receiveX(scanner, printer);
        double y = receiveY(scanner, printer);
        return new Coordinates(x, y);
    }
    /**
     * Receive music band's coordinates:x from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return Long
     */

    protected Long receiveX(Scanner scanner, Printer printer) {
        printer.outPrint("Координата X: ");
        String x = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return (PrimitiveTypeAsserter.checkLong(input))&&((Long.parseLong(input)) > -645);
            }
        };
        if (!func.execute(x)) {
            x = RepeatInput.go(x,"Координата X: ", "Введенное значение не является числом типа long или указана координата меньше -645", func, scanner, printer);
        }
        return Long.valueOf(x);
    }

    /**
     * Receive music band's coordinates: y from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return double
     */
    protected double receiveY(Scanner scanner, Printer printer) {
        printer.outPrint("Координата Y: ");
        String y = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return (PrimitiveTypeAsserter.checkDouble(input));
            }
        };
        if (!func.execute(y)) {
            y = RepeatInput.go(y,"Координата Y: ", "Введенное значение не является числом типа double", func, scanner, printer);
        }
        return Double.parseDouble(y);
    }
    /**
     * Receive music band's name from user
     * @param scanner command's scanner
     * @param printer command's printer
     * @return String
     */

    protected String receiveName(Scanner scanner, Printer printer) {
        printer.outPrint("Название: ");
        String name = scanner.nextLine();
        Operation func = new Operation() {
            @Override
            public boolean execute(String input) {
                return !input.equals("");
            }
        };
        if (!func.execute(name)) {
            RepeatInput.go(name,"Название: ", "Название не может быть пустым", func, scanner, printer);
        }
        return name;
    }
}

