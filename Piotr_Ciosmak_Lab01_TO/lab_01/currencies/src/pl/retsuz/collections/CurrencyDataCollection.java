package pl.retsuz.collections;

import pl.retsuz.currency.Currency;
import pl.retsuz.currency.ICurrency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDataCollection implements IDataCollection
{
    private List<ICurrency> list;

    public CurrencyDataCollection()
    {
        list = new ArrayList<>();
    }

    @Override
    public String ToString()
    {
        String tmp = "";
        for (ICurrency x : list)
        {
            tmp += "Code = " + x.getCode() + " | Factor = " + x.getFactor() + " | Rate = " + x.getRate() + "\n";
        }
        return tmp;
    }

    @Override
    public List<ICurrency> getCurrencyList()
    {
        return this.list;
    }

    @Override
    public ICurrency getCurrencyByCode(ICurrency currency)
    {
        for (ICurrency x : list)
        {
            if (currency.equals(x))
            {
                return x;
            }
        }
        return null;
    }
}
