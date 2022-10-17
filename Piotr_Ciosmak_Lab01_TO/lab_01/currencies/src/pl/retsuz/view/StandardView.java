package pl.retsuz.view;

import pl.retsuz.collections.IDataCollection;
import pl.retsuz.currency.Currency;
import pl.retsuz.currency.ICurrency;
import pl.retsuz.exchange.Exchange;
import pl.retsuz.exchange.IExchange;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class StandardView implements ICurrencyView
{
    private IExchange ex;
    private IDataCollection iData;
    private Scanner sc = new Scanner(System.in);

    @Override
    public void setExchange(IExchange exchange)
    {
        this.ex = exchange;
    }

    @Override
    public void setDataCollection(IDataCollection collection)
    {
        this.iData = collection;
    }

    @Override
    public void ViewAll(IDataCollection coll)
    {
        System.out.println(coll.ToString());
    }

    @Override
    public ICurrency StringToCurrency(String code)
    {
        Currency obj = new Currency();
        obj.setCode(code);
        return iData.getCurrencyByCode(obj);
    }

    private String parseWithMessageString(String label)
    {
        System.out.println(label);
        String tmp;
        try
        {
            tmp = sc.next();
        } catch (Exception e)
        {
            System.err.print("Wprowadzono niepoprwne dane!");
            tmp = parseWithMessageString(label);
        }
        return tmp.toUpperCase();
    }

    private double parseWithMessageDouble(String label)
    {
        System.out.println(label);
        Double tmp;
        try
        {
            tmp = sc.nextDouble();
            if (tmp < 0)
            {
                throw new Exception();
            }
        } catch (InputMismatchException e)
        {
            System.err.print("Wprowadzono niepoprawne dane!\n");
            sc = new Scanner(System.in);
            tmp = parseWithMessageDouble(label);
        } catch (Exception e)
        {
            System.err.print("Ilość środka pienieżnego nie może byc ujemna!\n");
            tmp = parseWithMessageDouble(label);
        }
        return tmp;
    }

    @Override
    public ICurrency ChooseCurrency(String label)
    {
        return StringToCurrency(parseWithMessageString(label));
    }

    @Override
    public void exchange()
    {
        ICurrency src = ChooseCurrency("Wybierz walute poczatkowa: ");
        if (src == null)
        {
            System.err.print("Podany kod waluty nie istnieje!\n");
            exchange();
        }
        else
        {

            ICurrency tgt = ChooseCurrency("Wybierz walute koncowa: ");
            if (tgt == null)
            {
                System.err.print("Podany kod waluty nie istnieje!\n");
                exchange();
            }
            else
            {
                double amount = parseWithMessageDouble("Podaj ile masz kasy: ");
                System.out.println("Po wymianie masz: " + ex.exchange(src, tgt, amount) + " " + tgt.getCode());
            }
        }
    }

    @Override
    public void menu()
    {
        char option;
        while (true)
        {
            System.out.println("--------");
            System.out.println("--MENU--");
            System.out.println("--------");
            System.out.println("0. EXIT");
            System.out.println("1. Wymien walute");
            System.out.println("2. Wypisz wszystkie waluty");
            System.out.print("Wybierz opcje: ");
            option = sc.next().charAt(0);
            switch (option)
            {
                case '0':
                    System.out.println("Opuszczanie programu");
                    System.exit(0);
                    break;
                case '1':
                    exchange();
                    break;
                case '2':
                    ViewAll(iData);
                    break;
                default:
                    System.out.println("Bledna opcja");
                    System.out.println("Wproawdz ponownie!");
            }
        }
    }
}
