package org.ta4j.core.indicators.volume;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

/**
 * Volume Oscillator indicator.
 * <a href="https://www.tradingview.com/support/solutions/43000591350-volume-oscillator/">TradingView</a>
 */
public class VolumeOscillatorIndicator extends CachedIndicator<Num> {
	private final EMAIndicator fastMaIndicator;
	private final EMAIndicator slowMaIndicator;

	public VolumeOscillatorIndicator(BarSeries series) {
		this(series, 5, 10);
	}

	public VolumeOscillatorIndicator(VolumeIndicator volumeIndicator) {
		this(volumeIndicator, 5, 10);
	}

	public VolumeOscillatorIndicator(BarSeries series, int fastLength, int slowLength) {
		this(new VolumeIndicator(series), fastLength, slowLength);
	}

	public VolumeOscillatorIndicator(VolumeIndicator volumeIndicator, int fastLength, int slowLength) {
		super(volumeIndicator);

		this.fastMaIndicator = new EMAIndicator(volumeIndicator, fastLength);
		this.slowMaIndicator = new EMAIndicator(volumeIndicator, slowLength);
	}

	@Override
	protected Num calculate(int index) {
		Num fast = fastMaIndicator.getValue(index);
		Num slow = slowMaIndicator.getValue(index);
		return hundred().multipliedBy(fast.minus(slow)).dividedBy(slow);
	}

	@Override
	public int getUnstableBars() {
		return Math.max(fastMaIndicator.getUnstableBars(), slowMaIndicator.getUnstableBars());
	}
}
