package pl.retsuz.exchange;

import pl.retsuz.currency.ICurrency;

public class Exchange implements IExchange
{
    private static Exchange exchange;

    private Exchange()
    {
    }

    public static Exchange getInstance()
    {
        if (exchange == null)
        {
            exchange = new Exchange();
        }
        return exchange;
    }

    @Override
    public double exchange(ICurrency src, ICurrency tgt, double amt)
    {
        double finallAmount = amt / src.getFactor() * src.getRate() / tgt.getRate() * tgt.getFactor();
        finallAmount = Math.round(finallAmount * 100);
        finallAmount /= 100;
        return finallAmount;
    }
}