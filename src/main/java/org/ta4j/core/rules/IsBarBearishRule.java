package org.ta4j.core.rules;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.AbstractIndicator;

/**
 * Rule to check if the bar for the given index is bearish.
 * Its purpose is to support not only BarSeries but also an indicator of type Bar,
 */
public class IsBarBearishRule extends AbstractRule {
	private final Indicator<Bar> indicator;

	public IsBarBearishRule(BarSeries series) {
		this(new AbstractIndicator<Bar>(series) {
			@Override
			public Bar getValue(int index) {
				return getBarSeries().getBar(index);
			}
		});
	}

	public IsBarBearishRule(Indicator<Bar> indicator) {
		this.indicator = indicator;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		boolean satisfied = indicator.getValue(index).isBearish();
		traceIsSatisfied(index, satisfied);
		return satisfied;
	}
}
