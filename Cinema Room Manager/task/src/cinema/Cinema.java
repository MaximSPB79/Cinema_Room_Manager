package cinema;

import java.util.Scanner;

public class Cinema {

    private static final char EMPTY_PLACE = 'S';
    private static final char OCCUPIED_PLACE = 'B';
    private static final String HEADER_FIRST_SYMBOL = " ";
    private static final String SPACE_MAP = " ";
    private static final int TICKET_PRICE_VIP = 10;
    private static final int TICKET_PRICE = 8;
    private static final String DOLLARS = "$";
    private static int rows;
    private static int seats;
    private static int counter = 0;
    private static int countVip = 0;
    private static int count = 0;
    private static int rowBuyTickets;

    public static void main(String[] args) {

        cinema();

    }

    public static void cinema() {
        Scanner scanner = new Scanner(System.in);
        parameterInput(scanner);
        int[][] map = new int[rows][seats];
        initMap(map);
        menuActivation(scanner, map);
    }

    private static int getMenuItem(Scanner scanner) {
        int menuItem = scanner.nextInt();
        return menuItem;
    }

    private static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

    }

    private static void parameterInput(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        System.out.println();
    }

    private static void initMap(int[][] map) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                map[i][j] = EMPTY_PLACE;
            }
        }
    }

    private static void printHeaderMap(int[][] map) {
        System.out.println("Cinema:");
        System.out.print(HEADER_FIRST_SYMBOL + SPACE_MAP);
        for (int i = 0; i < seats; i++) {
            printNumberMap(i);
        }
        System.out.println();
    }

    private static void printNumberMap(int i) {

        System.out.print(i + 1 + SPACE_MAP);
    }

    private static void printBodyMap(int[][] map) {
        for (int i = 0; i < rows; i++) {
            printNumberMap(i);

            for (int j = 0; j < seats; j++) {
                System.out.print((char) map[i][j] + SPACE_MAP);
            }
            System.out.println();
        }
    }

    private static void printMap(int[][] map) {
        printHeaderMap(map);
        printBodyMap(map);
        System.out.println();
    }

    private static int income(int rows, int seats) {
        int places = rows * seats;
        if (places <= 60) {
            return places * TICKET_PRICE_VIP;
        } else {
            if (rows % 2 == 0) {
                places /= 2;
                return places * TICKET_PRICE_VIP + places * TICKET_PRICE;
            } else {
                return rows / 2 * seats * TICKET_PRICE_VIP + (rows / 2 + rows % 2) * seats * TICKET_PRICE;
            }
        }
    }

    private static void menuActivation(Scanner scanner, int[][] map) {
        while (true) {
            printMenu();
            switch (getMenuItem(scanner)) {
                case 1:
                    printMap(map);
                    break;
                case 2:
                    bayTicket(scanner, map);
                    break;
                case 3:
                    statisticsCalculation();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Enter a number from 0 to 3");
            }
        }
    }

    private static void bayTicket(Scanner scanner, int[][] map) {

        ticketPurchaseExceptionCheck(scanner, map);

        if (rows * seats < 60 || rowBuyTickets < rows / 2) {
            System.out.println("Ticket price: " + DOLLARS + TICKET_PRICE_VIP);
            countVip++;
        } else {
            System.out.println("Ticket price: " + DOLLARS + TICKET_PRICE);
            count++;
        }
        System.out.println();
        counter = countVip + count;
    }

    private static void ticketPurchaseExceptionCheck(Scanner scanner, int[][] map) {
        while (true) {
            System.out.println();
            System.out.println("Enter a row number:");
            rowBuyTickets = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            int seatsBuyTickets = scanner.nextInt() - 1;
            System.out.println();
            if (rowBuyTickets >= rows || seatsBuyTickets >= seats) {
                System.out.println("Wrong input!");
                System.out.println();
            } else if (map[rowBuyTickets][seatsBuyTickets] == OCCUPIED_PLACE) {
                System.out.println("That ticket has already been purchased!");
                System.out.println();
            } else {
                map[rowBuyTickets][seatsBuyTickets] = OCCUPIED_PLACE;
                break;
            }
        }
    }

    private static void statisticsCalculation() {

        System.out.println();
        System.out.println("Number of purchased tickets: " + counter);

        seatingCalculation();

        calculationOfCurrentIncome();

        System.out.println("Total income:$" + income(rows, seats));
        System.out.println();
    }

    private static void seatingCalculation() {
        double percentage = (double) counter / (rows * seats) * 100;
        System.out.printf("Percentage: %.2f", percentage);
        System.out.println("%");
    }

    private static void calculationOfCurrentIncome() {
        int sum = count * TICKET_PRICE + countVip * TICKET_PRICE_VIP;
        System.out.println("Current income: $" + sum);
    }
}