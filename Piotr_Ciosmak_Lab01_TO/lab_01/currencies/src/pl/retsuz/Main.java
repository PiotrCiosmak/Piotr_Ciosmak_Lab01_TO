package pl.retsuz;

import org.xml.sax.SAXException;
import pl.retsuz.collections.CurrencyDataCollection;
import pl.retsuz.collections.IDataCollection;
import pl.retsuz.collections.providers.IStringCurrencyCollectionProvider;
import pl.retsuz.collections.providers.XMLCurrencyCollectionProvider;
import pl.retsuz.data.IRemoteDataProvider;
import pl.retsuz.data.RemoteDataProvider;
import pl.retsuz.exchange.Exchange;
import pl.retsuz.exchange.IExchange;
import pl.retsuz.view.ICurrencyView;
import pl.retsuz.view.StandardView;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * ZADANIE:ToString
 * 3) Utworzyć klasę StandardView w paczce view, implementującą interfejs ICurrencyView
 * <p>
 * Zaimplementować logikę powyższych klas
 */
public class Main
{

    static IRemoteDataProvider provider;
    static IDataCollection LastA;
    static IStringCurrencyCollectionProvider xmlProvider;
    static IExchange exchange;
    static ICurrencyView view;

    public static void main(String[] args)
    {
        provider = new RemoteDataProvider();
        xmlProvider = new XMLCurrencyCollectionProvider();

        LastA = new CurrencyDataCollection();

        exchange = Exchange.getInstance();

        view = new StandardView();
        try
        {
            String result = provider.acquireRemoteData("https://www.nbp.pl/kursy/xml/lasta.xml");

            xmlProvider.provide(result, LastA);
            System.out.println(LastA.ToString());
            view.setDataCollection(LastA);
            view.setExchange(exchange);
            view.menu();

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (SAXException e)
        {
            e.printStackTrace();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }
}
