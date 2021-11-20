package ru.arina;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String args[]) {
        inv();
    }

    public static void inv() {
     /* BigInteger - это класс, хранящийся в пакете java.math.
    Он представляет целые числа произвольной длины. */
        BigInteger a = BigInteger.ZERO; // a = 0
        BigInteger p = BigInteger.ZERO; // p = 0
        BigInteger b = BigInteger.ZERO; // b = 0
        BigInteger r = BigInteger.ZERO; // r = 0

        // Считывание с консоли числа p
        System.out.print("Введите число p: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            p = new BigInteger(scanner.next());
            System.out.println("Введенное p = " + p);
        }

        // Считывание с консоли числа a
        System.out.print("Введите значение a: ");
        if (scanner.hasNext()) {
            a = new BigInteger(scanner.next());
            System.out.println("Введенное a = " + a);
        }

        // Считывание с консоли числа b
        System.out.print("Введите значение b: ");
        if (scanner.hasNext()) {
            b = new BigInteger(scanner.next());
            System.out.println("Введенное b = " + b);
        }

        // Считывание с консоли числа r
        System.out.print("Введите значение r: ");
        if (scanner.hasNext()) {
            r = new BigInteger(scanner.next());
            System.out.println("Введенное r = " + r);
        }

        if ((p.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 1) &&
                p.compareTo(BigInteger.ZERO) == 1 && a.compareTo(BigInteger.ZERO) == 1 &&
                b.compareTo(p) == -1 && b.compareTo(BigInteger.ONE) == 1) {
            //roPollard(p, a, b, r);
           shanks(p, a, b, r);
        }

        inv();

        return;
    }

    /* p - метод Полларда*/
    public static void roPollard(BigInteger p, BigInteger a, BigInteger b, BigInteger r) {

        System.out.println("\n" + "p - метод Полларда: ");
        BigInteger c, d, u2, v2, x;
        BigInteger u1 = BigInteger.ZERO;
        BigInteger v1 = BigInteger.ZERO;
        int i = 0;

        // Считывание с консоли числа u1
        System.out.print("Введите число u1: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            u1 = new BigInteger(scanner.next());
            System.out.println("Введенное u1 = " + u1);
        }

        // Считывание с консоли числа v1
        System.out.print("Введите значение v1: ");
        if (scanner.hasNext()) {
            v1 = new BigInteger(scanner.next());
            System.out.println("Введенное v1 = " + v1);
        }

        if (u1.compareTo(BigInteger.ZERO) != 1 || v1.compareTo(BigInteger.ZERO) != 1) {
            System.out.print("Ошибка!");
            return;
        }

        c = ((a.modPow(u1, p)).multiply(b.modPow(v1, p))).mod(p);
        d = c;
        u2 = u1;
        v2 = v1;
        System.out.println("Шаг 0.\tc = " + c + "\tlog(c) = " + u1 + " + x * " + v1 + "\td = " + d + "\tlog(d) = " + u2 + " + x * " + v2);

        do {
            i++;
            if (less(c, p)) u1 = u1.add(BigInteger.ONE);
            else v1 = v1.add(BigInteger.ONE);
            c = f(c, p, a, b);

            if (less(d, p)) u2 = u2.add(BigInteger.ONE);
            else v2 = v2.add(BigInteger.ONE);
            d = f(d, p, a, b);
            if (less(d, p)) u2 = u2.add(BigInteger.ONE);
            else v2 = v2.add(BigInteger.ONE);
            d = f(d, p, a, b);
            System.out.println("Шаг " + i + ".\tc = " + c + "\tlog(c) = " + u1 + " + x * " + v1 + "\td = " + d + "\tlog(d) = " + u2 + " + x * " + v2);
        }
        while (c.compareTo(d.mod(p)) != 0);

        x = solution(v1.subtract(v2), u2.subtract(u1), r);
        System.out.println("x = " + x);

    }

    public static boolean less(BigInteger c, BigInteger p) {
        if (c.compareTo(p.divide(BigInteger.valueOf(2))) == -1) return (true);
        else return (false);
    }

    public static BigInteger f(BigInteger c, BigInteger p, BigInteger a, BigInteger b) {
        if (less(c, p)) return ((c.multiply(a)).mod(p));
        else return ((c.multiply(b)).mod(p));
    }

    public static BigInteger solution(BigInteger a, BigInteger b, BigInteger m) {
        long n = euler(m.longValue()) - 1;
        System.out.println(a + " * x = " + b + "(mod " + m + ");");
        a = (b.multiply(a.modPow(BigInteger.valueOf(n), m))).mod(m);
        return a;
    }

    public static long euler(long n) {
        long result = n;
        for (long i = 2; i * i <= n; ++i)
            if (n % i == 0) {
                while (n % i == 0)
                    n /= i;
                result -= result / i;
            }
        if (n > 1)
            result -= result / n;
        return result;
    }


    public static void shanks(BigInteger p, BigInteger a, BigInteger b, BigInteger r) {

        System.out.println("\n" + "Метод Гельфонда: ");
        BigInteger s, d, x, u, res;
        BigInteger deg = BigInteger.ZERO;
        BigInteger pos = BigInteger.ZERO;

        s = BigDecimal.valueOf(Math.sqrt(r.doubleValue())).toBigInteger().add(BigInteger.ONE);
        System.out.println("s = " + s);
        d = a.modPow(r.subtract(s), p);
        System.out.println("d = " + d);

       for (int j = 0; j < 5; j++)
        {
            u = (a.pow(j)).mod(p);
            System.out.println("Шаг " + j + ".\ta = " + u);
        }

        Database sequence[] = new Database[192050057];

        for (int j = 0; j < 192050057; j++) {
            sequence[j] = new Database((b.multiply(d.pow(j)).mod(p)), BigInteger.valueOf(j));
            System.out.println(j + ".  " +  sequence[j].getValue() + "\n");
        }

        List<Database> data = new ArrayList<Database>(Arrays.asList(sequence));
        Collections.sort(data);

        for (Database n : data) {
            System.out.println(n.getValue() + " " + n.getNumber() + "\n");
        }

        int i = 0;

        for (int j = 0; j < s.intValue(); j++) {
            u = (a.pow(j)).mod(p);
            System.out.println("u = " + u);

            for (Database n : data) {
                if (n.getValue().compareTo(u) == 0) {
                    deg = BigInteger.valueOf(j);
                    j = s.intValue();
                    pos = n.getNumber();
                } else i++;
            }
        }

        if (i != (s.pow(2)).intValue()) {
            System.out.println("b * a^(" + pos + " * (-" + s + ")) = a^(" + deg + ")");
            res = ((pos.multiply(s)).add(deg)).mod(r);
            System.out.println("res = " + pos + " * (-" + s + ") + " + deg + " = " + res + " (mod " + r + ");");
        } else System.out.println("Решение не найдено");
    }
}

class Database implements Comparable<Database> {
    private BigInteger value;
    private BigInteger number;

    public Database(BigInteger firstName, BigInteger lastName) {
        this.value = firstName;
        this.number = lastName;
    }

    public BigInteger getValue() {
        return value;
    }

    public BigInteger getNumber() {
        return number;
    }

    public int compareTo(Database otherName) {
        if (this.value.compareTo(otherName.value) == 0) {
            return this.number.compareTo(otherName.number);
        } else {
            return this.value.compareTo(otherName.value);
        }
    }
}

