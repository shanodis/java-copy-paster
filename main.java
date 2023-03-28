//BANK
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
 
public class Bank {
    //Map containing key + value as name and account balance
    Map<String, Integer> accounts = new HashMap<>();
 
    //Withdrawal method checking if user has funds on their account
    //If yes it allows for money withdrawal. If not throws an exception
    void withdraw(String user) throws InsufficientFundsException {
        System.out.println("Wprowadź kwotę wypłaty:");
        Scanner scanner = new Scanner(System.in);
        int withdrawal = scanner.nextInt();
        if (accounts.get(user) >= withdrawal) {
            System.out.println("Wypłacono " + withdrawal);
            accounts.put(user, accounts.get(user) - withdrawal);
            balance(user);
        }
        else {
            throw new InsufficientFundsException();
        }
    }
 
    //Deposit method checking if sum is correct.
    //If yes it allows user to deposit their money
    //If not throws an exception
    void deposit(String user)
    {
        System.out.println("Wprowadź kwotę wpłaty:");
        Scanner scanner = new Scanner(System.in);
        int deposit = scanner.nextInt();
        if(deposit < 0) throw new IllegalArgumentException("Wprowadzoną błędną kwotę");
        System.out.println("Wpłacono " + deposit);
        accounts.put(user, accounts.get(user) + deposit);
        balance(user);
    }
 
    //Method handling transfer between two available users: Mikołaj and Dominik
    //Being logged as one of them lets user transfer their money to another
    //It also has error handling with an exception
    void transfer(String user) throws Exception {
        System.out.println("Wprowadź kwotę przelewu:");
        Scanner scanner = new Scanner(System.in);
        int transferAmount = scanner.nextInt();
        if(Objects.equals(user, "Mikołaj") && transferAmount <= accounts.get(user) && transferAmount > 0) {
            accounts.put("Dominik", accounts.get("Dominik") + transferAmount);
            accounts.put("Mikołaj", accounts.get("Mikołaj") - transferAmount);
        }
        else if (Objects.equals(user, "Dominik") && transferAmount <= accounts.get(user) && transferAmount > 0) {
            accounts.put("Mikołaj", accounts.get("Mikołaj") + transferAmount);
            accounts.put("Dominik", accounts.get("Dominik") - transferAmount);
        }
        else throw new Exception("Błąd transakcja anulowana");
    }
 
    //Simple method for checking user's account balance
    void balance(String user) {
        System.out.println("Twój stan konta to: " + accounts.get(user));
    }
 
    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();
        bank.accounts.put("Mikołaj", 1000);
        bank.accounts.put("Dominik", 2000);
        while(true) {
            System.out.println("Wybierz użytkownika: 'Mikołaj' lub 'Dominik'");
            Scanner pickUser = new Scanner(System.in);
            String user = pickUser.nextLine();
            if(Objects.equals(user, "Mikołaj") || Objects.equals(user, "Dominik")){}
            else {
                System.out.println("Nie ma takiego użytkownika");
                return;
            }
            System.out.println("Co chcesz zrobić?");
            System.out.println("1 - Wypłata");
            System.out.println("2 - Wpłata");
            System.out.println("3 - Przelew");
            System.out.println("4 - Stan konta");
            System.out.println("5 - Wyjście");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if(choice == 1){
                bank.withdraw(user);
            }else if(choice == 2){
                bank.deposit(user);
            }else if(choice == 3){
                bank.transfer(user);
            }else if(choice == 4){
                bank.balance(user);
            }else if(choice == 5){
                break;
            }else{
                System.out.println("Wrong input try again.");
            }
        }
    }
}
 
//BANK EXCEPTION
public class InsufficientFundsException extends Exception{
    public InsufficientFundsException() {
        super("Brak wystarczających środków na koncie");
    }
}
 
//DICE ROLL
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
 
public class DiceRoll {
    public static void main(String args[]) throws Exception {
        LinkedList<Integer> rolls = new LinkedList<>();
        System.out.print("Wpisz iloma kostkami chcesz rzucić: ");
        Scanner input = new Scanner(System.in);
        int numberOfDice = input.nextInt();
        if(numberOfDice <= 0 || numberOfDice > 100) {
            throw new Exception("Niepoprawna ilość kości");
        }
        Random ranNum = new Random();
 
        System.out.print("Wyniki: ");
        int total = 0;
        int rand;
 
        for (int i = 0; i < numberOfDice; i++) {
            rand = ranNum.nextInt(6) + 1;
            total = total + rand;
            System.out.print(rand);
            System.out.print(" ");
            rolls.add(rand);
        }
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateNow.format(dateFormatted);
        System.out.println("Suma: " + total);
        input.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("rolls.txt", true));
        writer.append("Rzut z: " + formattedDate);
        writer.append(System.lineSeparator());
        for(Integer num : rolls) {
            writer.append(num + System.lineSeparator());
        }
        writer.append("Suma: " + total);
        writer.append(System.lineSeparator());
        writer.close();
        File file = new File("rolls.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            System.out.println(data);
        }
    }
}
 
//FILES
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class Odczyt {
 
    public static void main(String[] args) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Integer> prime = new ArrayList<>();
        try {
            File myObj = new File("numbers.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                integerArrayList.add(Integer.valueOf(data));
                if(isPrimeNumber(Integer.valueOf(data))) {
                    prime.add(Integer.valueOf(data));
                };
            }
            myReader.close();
            System.out.println("Liczby całkowite: " + integerArrayList);
            System.out.println("Liczby pierwsze: " + prime);
        } catch (FileNotFoundException e) {
            System.out.println("Nie można znaleźć takiego pliku");
            e.printStackTrace();
        }
    }
 
    public static boolean isPrimeNumber(Integer number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
