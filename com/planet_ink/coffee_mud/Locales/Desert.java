package com.planet_ink.coffee_mud.Locales;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import java.util.*;

public class Desert extends StdRoom
{
	public Desert()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="the desert";
		recoverEnvStats();
		domainType=Room.DOMAIN_OUTDOORS_DESERT;
		domainCondition=Room.CONDITION_HOT;
		baseThirst=4;
	}
	public Environmental newInstance()
	{
		return new Desert();
	}
}
