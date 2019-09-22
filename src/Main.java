import com.google.gson.Gson;
import exceptions.PowerException;
import exceptions.UnitializedException;
import exceptions.FeelingException;
import laba_4.*;
import moves.Climb;
import moves.Fly;
import moves.Jump;

import java.io.*;
import java.util.*;

public class Main {

	// changed by Tehnopat

    interface Scenario {
        void Run();
    }

    private static final String separator = ";";
    private static Scanner scanner = new Scanner(System.in);
    private static final String CMD_EXIT = "exit";
    private static final String CMD_INFO = "info";
    private static final String CMD_SHOW = "show";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_ADD = "add";
    private static final String CMD_ADD_IF_MIN = "add_if_min";
    private static final String CMD_INSERT = "insert";
    private static final String CMD_SAVE = "save";
    private static final String CMD_HELP = "help";
    private static Date initDate = new Date();
    private static AreaComparator objAreaComparator = new AreaComparator();
    private static String collectionPath;
    private static List<House> list = Collections.emptyList();
    private static boolean isExit;

    private static House fromCSV(String line, String separator) {
        try {
            String[] parts = line.split(separator);
            House house = new House(parts[0], parts[1], parts[2]);
            house.area = Integer.parseInt(parts[3]);
            house.roof.color = Integer.parseInt(parts[4]);
            return house;
        } catch (Exception e) {
            return null;
        }
    }

    private static List<House> readFile(String collectionPath) {

        if (collectionPath == null)
            return Collections.emptyList();

        List<House> result = new ArrayList<>();
        File file = new File(collectionPath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                House house = fromCSV(line, separator);
                if (house != null)
                    result.add(house);
                else
                    System.err.println("не удалось десериализовать строку");
            }
        } catch (FileNotFoundException e) {
            System.err.println("файл не найден или отсутствуют права доступа к файлу");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("не удалось открыть файл");
            System.exit(0);
        }
        return result;
    }

    private static boolean saveFile(String collectionPath, List<House> list) {

        if (collectionPath == null)
            return false;

        StringBuilder stringBuilder = new StringBuilder();
        for (House house : list) {
            stringBuilder
                    .append(house.getName()).append(separator)
                    .append(house.roof.getName()).append(separator)
                    .append(house.pipe.getName()).append(separator)
                    .append(house.area).append(separator)
                    .append(house.roof.color).append("\n");
        }
        File file = new File(collectionPath);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(stringBuilder.toString());
            writer.close();
            return true;
        } catch (Exception e) {
            System.err.println("ошибка сохранения файла");
            return false;
        }
    }

    public static void main(String[] args) {

        collectionPath = System.getenv("COLLECTION_PATH");
        if (collectionPath == null || collectionPath.isEmpty()) {
            System.err.println("путь должен передаваться через переменную окружения COLLECTION_PATH!");
            System.exit(0);
        }

        list = readFile(collectionPath);
        list.sort(objAreaComparator);

        System.out.println("--- START ---");
        System.out.println("введите команду: ");
        try {
            while (!isExit) {
                System.out.print("> ");
                String command = scanner.next();
                switch (command.toLowerCase()) {
                    case CMD_EXIT: {
                        boolean success = saveFile(collectionPath, list);
                        if (success)
                            System.out.println("файл сохранён успешно");
                        else
                            System.out.println("не удалось сохранить файл");
                        System.out.println("завершение программы");
                        isExit = true;
                        break;
                    }
                    case CMD_INFO: {
                        System.out.println("тип " + list.getClass().getSimpleName());
                        System.out.println("количество элементов " + list.size());
                        System.out.println("дата инициализации " + initDate.toString());
                        break;
                    }
                    case CMD_SHOW: {
                        for (int i = 0; i < list.size(); i++) {
                            House house = list.get(i);
                            System.out.println(i + ": " + house.toString());
                        }
                        break;
                    }
                    case CMD_REMOVE: {
                        if (!scanner.hasNext()) {
                            System.out.println("ошибка: неправильный формат команды");
                            continue;
                        }
                        try {
                            int index = scanner.nextInt();
                            if (index >= 0 && index < list.size()) {
                                list.remove(index);
                                list.sort(objAreaComparator);
                                System.out.println("объект удалён");
                            } else {
                                System.out.println("ошибка: недопустимый индекс массива");
                            }
                        } catch (Exception e1) {
                            try {
                                String param = scanner.next();
                                House house = new Gson().fromJson(param, House.class);
                                boolean removed = list.remove(house);
                                if (removed) {
                                    list.sort(objAreaComparator);
                                    System.out.println("объект удалён");
                                } else
                                    System.out.println("такой объект не найден");
                            } catch (Exception e2) {
                                System.out.println("ошибка: не получилось распознать объект из данных");
                            }
                        }
                        scanner.nextLine();
                        break;
                    }

                    case CMD_ADD: {
                        try {
                            String input = readMultiline();
                            House house = new Gson().fromJson(input, House.class);
                            if (house != null) {
                                if (validateHouse(house)) {
                                    list.add(house);
                                    list.sort(objAreaComparator);
                                    System.out.println("объект добавлен");
                                } else {
                                    System.err.println("инвалидно введено");
                                }
                            } else {
                                System.err.println("ошибка: не получилось распознать объект из данных");
                            }
                        } catch (Exception e2) {
                            System.err.println("ошибка при вводе данных");
                        }
                        break;
                    }

                    case CMD_ADD_IF_MIN: {
                        try {
                            String input = readMultiline();
                            House house = new Gson().fromJson(input, House.class);
                            if (house != null) {
                                if (validateHouse(house) && house.area < list.get(0).area) {
                                    list.add(house);
                                    list.sort(objAreaComparator);
                                    System.out.println("объект добавлен");
                                } else {
                                    System.err.println("инвалидно введено или площадь больше максимальной");
                                }
                            } else {
                                System.err.println("ошибка: не получилось распознать объект из данных");
                            }
                        } catch (Exception e2) {
                            System.err.println("ошибка при вводе данных");
                        }
                        break;
                    }

                    case CMD_INSERT: {
                        try {
                            int index = scanner.nextInt();
                            String input = readMultiline();
                            House house = new Gson().fromJson(input, House.class);
                            list.add(index, house);
                            list.sort(objAreaComparator);
                            System.out.println("объект добавлен");
                        } catch (Exception e2) {
                            System.out.println("ошибка: не получилось распознать объект из данных");
                        }
                        break;
                    }

                    case CMD_SAVE: {
                        boolean success = saveFile(collectionPath, list);
                        if (success)
                            System.out.println("файл сохранён успешно");
                        else
                            System.out.println("не удалось сохранить файл");
                        break;
                    }

                    case CMD_HELP: {
                        System.out.println("Информация о доступных командах:" +
                                "\ninfo: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" +
                                "\nshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                                "\nremove {int index}: удалить элемент, находящийся в заданной позиции коллекции" +
                                "\nadd {element}: добавить новый элемент в коллекцию" +
                                "\ninsert {int index} {element}: добавить новый элемент в заданную позицию" +
                                "\nremove {element}: удалить элемент из коллекции по его значению" +
                                "\nsave: сохранить коллекцию в файл");
                        break;
                    }

                    default: {
                        System.out.println("неизвестная команда, попробуйте еще");
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Файл сохранен!");
            saveFile(collectionPath, list);
        }

        Scenario scenario = new Scenario() {
            @Override
            public void Run() {
                try {
                    House house = new House("Дом");
                    House arbor = new House("Беседка");
                    UnknownPlace unknownPlace = new UnknownPlace("Неизвестное место");
                    Wind wind = new Wind("Ветер", 5);
                    Shorty znayka = new Shorty("Знайка", 5);
                    Shorty vintik = new Shorty("Винтик", 5);
                    Shorty unknownShorty = new Shorty("Неизвестный коротышка", 4);
                    unknownShorty.setPlace(house);
                    Rope rope = new Rope();

                    ArrayList<Object> houses = new ArrayList<Object>();
                    houses.add(house);

                    znayka.move(new Jump(), arbor);
                    znayka.setFeeling(AHuman.Feeling.NICE);
                    // arbor.existHuman(vintik);
                    znayka.take(rope);
                    unknownShorty.pool(rope);
                    znayka.move(new Climb(), house.pipe, house.roof);
                    wind.move(new Fly(), house.roof);
                    wind.carry(znayka, unknownPlace);
                    System.out.println(znayka.getFeeling());
                } catch (UnitializedException e) {
                    System.err.println(e.getMessage());
                } catch (FeelingException e) {
                    e.printStackTrace();
                } catch (PowerException e) {
                    e.printStackTrace();
                }
            }
        };
        //  scenario.Run();
    }

    private static boolean validateHouse(House house) {
        return house != null && house.area != 0 && !house.getName().isEmpty();
    }

    private static String readMultiline() {
        StringBuilder builder = new StringBuilder();
        String line = scanner.next();
        builder.append(line);
        line = scanner.nextLine();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty())
                break;
            else
                builder.append(line);
        }
        return builder.toString();
    }


}

