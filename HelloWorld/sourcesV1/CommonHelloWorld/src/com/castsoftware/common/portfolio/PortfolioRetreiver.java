package com.castsoftware.common.portfolio;

import com.castsoftware.common.exceptions.InitializationException;

public abstract class PortfolioRetreiver {
	protected Portfolio portfolio;
	
	protected PortfolioRetreiver() throws InitializationException {
		portfolio = new Portfolio();
	}
	
}
